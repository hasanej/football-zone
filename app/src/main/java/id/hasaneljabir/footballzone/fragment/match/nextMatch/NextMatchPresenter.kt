package id.hasaneljabir.footballzone.fragment.match.nextMatch

import id.hasaneljabir.footballzone.entity.event.EventResponse
import id.hasaneljabir.footballzone.entity.repository.match.MatchRepositoryImplementation
import id.hasaneljabir.footballzone.fragment.match.MatchContract
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class NextMatchPresenter(
    val view: MatchContract.View,
    val matchRepositoryImplementation: MatchRepositoryImplementation,
    val scheduler: SchedulerProvider
) : MatchContract.Presenter {

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }

    val compositeDisposable = CompositeDisposable()

    override fun getFootballMatchData(leagueName: String) {
        view.showLoading()
        compositeDisposable.add(
            matchRepositoryImplementation.getNextMatch(leagueName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<EventResponse>() {
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: EventResponse) {
                        view.displayFootballMatch(t.event)
                    }

                    override fun onError(t: Throwable?) {
                        view.hideLoading()
                        view.displayFootballMatch(Collections.emptyList())
                    }

                })
        )
    }
}