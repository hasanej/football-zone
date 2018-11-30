package id.hasaneljabir.footballzone.feature.playerdetail

import id.hasaneljabir.footballzone.entity.player.PlayerDetailResponse
import id.hasaneljabir.footballzone.entity.repository.PlayersRepositoryImpl
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber

class PlayerDetailPresenter(
    val mView: PayerDetailContract.View,
    val playersRepositoryImpl: PlayersRepositoryImpl,
    val schedulerProvider: SchedulerProvider
) : PayerDetailContract.Presenter {


    val compositeDisposable = CompositeDisposable()

    override fun getPlayerData(idPlayer: String) {
        compositeDisposable.add(
            playersRepositoryImpl.getPlayerDetail(idPlayer)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : ResourceSubscriber<PlayerDetailResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: PlayerDetailResponse) {
                        mView.displayPlayerDetail(t.player[0])
                    }

                    override fun onError(t: Throwable?) {

                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}