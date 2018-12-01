package id.hasaneljabir.footballzone.fragment.team

import id.hasaneljabir.footballzone.entity.repository.team.TeamRepositoryImplementation
import id.hasaneljabir.footballzone.entity.team.TeamResponse
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class TeamPresenter(
    val view: TeamContract.View, val teamRepositoryImplementation: TeamRepositoryImplementation,
    val scheduler: SchedulerProvider
) : TeamContract.Presenter {
    val compositeDisposable = CompositeDisposable()

    override fun searchTeam(teamName: String) {
        compositeDisposable.add(
            teamRepositoryImplementation.getTeamBySearch(teamName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: TeamResponse) {
                        view.displayTeams(t.teams)
                    }

                    override fun onError(t: Throwable?) {
                        view.displayTeams(Collections.emptyList())
                        view.hideLoading()
                    }

                })
        )
    }

    override fun getTeamData(leagueName: String) {
        view.showLoading()
        compositeDisposable.add(
            teamRepositoryImplementation.getAllTeam(leagueName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: TeamResponse) {
                        view.displayTeams(t.teams)
                    }

                    override fun onError(t: Throwable?) {
                        view.displayTeams(Collections.emptyList())
                        view.hideLoading()
                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}