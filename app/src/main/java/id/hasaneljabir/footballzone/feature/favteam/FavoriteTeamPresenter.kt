package id.hasaneljabir.footballzone.feature.favteam

import id.hasaneljabir.footballzone.entity.repository.LocalRepositoryImpl
import id.hasaneljabir.footballzone.entity.repository.TeamRepositoryImpl
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.entity.team.TeamResponse
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class FavoriteTeamPresenter(
    val mView: FavoriteTeamContract.View,
    val localRepositoryImpl: LocalRepositoryImpl,
    val teamRepositoryImpl: TeamRepositoryImpl,
    val scheduler: SchedulerProvider
) : FavoriteTeamContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getTeamData() {
        mView.showLoading()
        val teamList = localRepositoryImpl.getTeamFromDb()
        var teamLists: MutableList<Team> = mutableListOf()
        for (fav in teamList) {
            compositeDisposable.add(
                teamRepositoryImpl.getTeamsDetail(fav.idTeam)
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                        override fun onComplete() {
                            mView.hideLoading()
                            mView.hideSwipeRefresh()
                        }

                        override fun onNext(t: TeamResponse) {
                            teamLists.add(t.teams[0])
                            mView.displayTeams(teamLists)
                        }

                        override fun onError(t: Throwable?) {
                            mView.hideLoading()
                            mView.hideSwipeRefresh()
                            mView.displayTeams(Collections.emptyList())
                        }

                    })
            )
        }

        if (teamList.isEmpty()) {
            mView.hideLoading()
            mView.hideSwipeRefresh()
            mView.displayTeams(teamLists)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}