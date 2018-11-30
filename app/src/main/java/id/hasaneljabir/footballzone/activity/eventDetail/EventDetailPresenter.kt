package id.hasaneljabir.footballzone.activity.eventDetail

import id.hasaneljabir.footballzone.entity.repository.LocalRepositoryImpl
import id.hasaneljabir.footballzone.entity.repository.TeamRepositoryImpl
import id.hasaneljabir.footballzone.entity.team.TeamResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class EventDetailPresenter(
    val view: EventDetailContract.View, val teamRepositoryImpl: TeamRepositoryImpl,
    val localRepositoryImpl: LocalRepositoryImpl
) : EventDetailContract.Presenter {
    override fun deleteMatch(id: String) {
        localRepositoryImpl.deleteData(id)
    }

    override fun checkMatch(id: String) {
        view.setFavoriteState(localRepositoryImpl.checkFavorite(id))
    }

    override fun insertMatch(eventId: String, homeId: String, awayId: String) {
        localRepositoryImpl.insertData(eventId, homeId, awayId)
    }

    override fun getTeamsBadgeHome(id: String) {
        compositeDisposable.add(
            teamRepositoryImpl.getTeamsDetail(id)
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
            teamRepositoryImpl.getTeamsDetail(id)
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