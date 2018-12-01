package id.hasaneljabir.footballzone.fragment.favorite.favTeam

import id.hasaneljabir.footballzone.entity.repository.local.LocalRepositoryImplementation
import id.hasaneljabir.footballzone.entity.repository.team.TeamRepositoryImplementation
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.entity.team.TeamResponse
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class FavTeamPresenter(
    val view: FavTeamContract.View,
    val localRepositoryImplementation: LocalRepositoryImplementation,
    val teamRepositoryImplementation: TeamRepositoryImplementation,
    val scheduler: SchedulerProvider
) : FavTeamContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getTeamData() {
        view.showLoading()
        val teamList = localRepositoryImplementation.getTeamFromDb()
        var teamLists: MutableList<Team> = mutableListOf()
        for (fav in teamList) {
            compositeDisposable.add(
                teamRepositoryImplementation.getTeamsDetail(fav.idTeam)
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                        override fun onComplete() {
                            view.hideLoading()
                            view.hideSwipeRefresh()
                        }

                        override fun onNext(t: TeamResponse) {
                            teamLists.add(t.teams[0])
                            view.displayTeams(teamLists)
                        }

                        override fun onError(t: Throwable?) {
                            view.hideLoading()
                            view.hideSwipeRefresh()
                            view.displayTeams(Collections.emptyList())
                        }

                    })
            )
        }

        if (teamList.isEmpty()) {
            view.hideLoading()
            view.hideSwipeRefresh()
            view.displayTeams(teamLists)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}