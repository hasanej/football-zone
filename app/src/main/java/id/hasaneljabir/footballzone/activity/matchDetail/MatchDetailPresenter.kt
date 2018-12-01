package id.hasaneljabir.footballzone.activity.matchDetail

import id.hasaneljabir.footballzone.entity.repository.local.LocalRepositoryImplementation
import id.hasaneljabir.footballzone.entity.repository.team.TeamRepositoryImplementation
import id.hasaneljabir.footballzone.entity.team.TeamResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class MatchDetailPresenter(
    val view: MatchDetailContract.View, val teamRepositoryImplementation: TeamRepositoryImplementation,
    val localRepositoryImplementation: LocalRepositoryImplementation
) : MatchDetailContract.Presenter {
    override fun deleteMatch(id: String) {
        localRepositoryImplementation.deleteData(id)
    }

    override fun checkMatch(id: String) {
        view.setFavoriteState(localRepositoryImplementation.checkFavorite(id))
    }

    override fun insertMatch(eventId: String, homeId: String, awayId: String) {
        localRepositoryImplementation.insertData(eventId, homeId, awayId)
    }

    override fun getTeamsBadgeHome(id: String) {
        compositeDisposable.add(
            teamRepositoryImplementation.getTeamsDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: TeamResponse) {
                        view.displayTeamBadgeHome(t.teams[0])
                    }

                    override fun onError(t: Throwable?) {

                    }
                })
        )
    }

    val compositeDisposable = CompositeDisposable()

    override fun getTeamsBadgeAway(id: String) {
        compositeDisposable.add(
            teamRepositoryImplementation.getTeamsDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: TeamResponse) {
                        view.displayTeamBadgeHome(t.teams[0])
                    }

                    override fun onError(t: Throwable?) {

                    }
                })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}