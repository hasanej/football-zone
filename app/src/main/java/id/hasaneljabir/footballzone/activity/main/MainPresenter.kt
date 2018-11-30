package id.hasaneljabir.footballzone.activity.main

import id.hasaneljabir.footballzone.entity.repository.MatchRepositoryImpl
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(val mView: MainContract.View, val matchRepositoryImpl: MatchRepositoryImpl) :
    MainContract.Presenter {

    val compositeDisposable = CompositeDisposable()
}