package id.hasaneljabir.footballzone.fragment.favorite.favMatch

import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.event.EventResponse
import id.hasaneljabir.footballzone.entity.repository.local.LocalRepositoryImplementation
import id.hasaneljabir.footballzone.entity.repository.match.MatchRepositoryImplementation
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class FavMatchPresenter(
    val mView: FavMatchContract.View,
    val matchRepositoryImplementation: MatchRepositoryImplementation,
    val localRepositoryImplementation: LocalRepositoryImplementation,
    val scheduler: SchedulerProvider
) : FavMatchContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getFootballMatchData() {
        mView.showLoading()
        val favList = localRepositoryImplementation.getMatchFromDb()
        var eventList: MutableList<Event> = mutableListOf()
        for (fav in favList) {
            compositeDisposable.add(
                matchRepositoryImplementation.getEventById(fav.idEvent)
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