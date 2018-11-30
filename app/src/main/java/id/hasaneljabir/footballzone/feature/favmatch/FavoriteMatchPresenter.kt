package id.hasaneljabir.footballzone.feature.favmatch

import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.event.EventResponse
import id.hasaneljabir.footballzone.entity.repository.LocalRepositoryImpl
import id.hasaneljabir.footballzone.entity.repository.MatchRepositoryImpl
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class FavoriteMatchPresenter(
    val mView: FavoriteMatchContract.View,
    val matchRepositoryImpl: MatchRepositoryImpl,
    val localRepositoryImpl: LocalRepositoryImpl,
    val scheduler: SchedulerProvider
) : FavoriteMatchContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getFootballMatchData() {
        mView.showLoading()
        val favList = localRepositoryImpl.getMatchFromDb()
        var eventList: MutableList<Event> = mutableListOf()
        for (fav in favList) {
            compositeDisposable.add(
                matchRepositoryImpl.getEventById(fav.idEvent)
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribeWith(object : ResourceSubscriber<EventResponse>() {
                        override fun onComplete() {
                            mView.hideLoading()
                            mView.hideSwipeRefresh()
                        }

                        override fun onNext(t: EventResponse) {
                            eventList.add(t.event[0])
                            mView.displayFootballMatch(eventList)
                        }

                        override fun onError(t: Throwable?) {
                            mView.displayFootballMatch(Collections.emptyList())
                            mView.hideLoading()
                            mView.hideSwipeRefresh()
                        }

                    })
            )
        }

        if (favList.isEmpty()) {
            mView.hideLoading()
            mView.hideSwipeRefresh()
            mView.displayFootballMatch(eventList)
        }
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}