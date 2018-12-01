package id.hasaneljabir.footballzone.fragment.player

import id.hasaneljabir.footballzone.entity.player.PlayerResponse
import id.hasaneljabir.footballzone.entity.repository.player.PlayerRepositoryImplementation
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class PlayerPresenter(
    val mView: PlayerContract.View,
    val playersRepositoryImplementation: PlayerRepositoryImplementation,
    val schedulerProvider: SchedulerProvider
) : PlayerContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getAllPlayer(teamId: String?) {
        mView.showLoading()
        compositeDisposable.add(
            playersRepositoryImplementation.getAllPlayers(teamId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : ResourceSubscriber<PlayerResponse>() {
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: PlayerResponse) {
                        mView.displayPlayers(t.player)
                    }

                    override fun onError(t: Throwable?) {
                        mView.displayPlayers(Collections.emptyList())
                        mView.hideLoading()
                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}