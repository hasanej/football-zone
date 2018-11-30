package id.hasaneljabir.footballzone.activity.searchMatch

import id.hasaneljabir.footballzone.entity.SearchedEvent
import id.hasaneljabir.footballzone.entity.repository.match.MatchRepositoryImplementation
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class SearchMatchPresenter(
    val mView: SearchMatchContract.View,
    val matchRepositoryImplementation: MatchRepositoryImplementation,
    val schedulerProvider: SchedulerProvider
) : SearchMatchContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun searchMatch(query: String?) {
        mView.showLoading()
        compositeDisposable.add(
            matchRepositoryImplementation.searchMatches(query)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : ResourceSubscriber<SearchedEvent>() {
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: SearchedEvent) {
                        mView.displayMatch(t.event ?: Collections.emptyList())
                    }

                    override fun onError(t: Throwable?) {
                        mView.displayMatch(Collections.emptyList())
                        mView.hideLoading()
                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}