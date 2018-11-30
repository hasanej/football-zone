package id.hasaneljabir.footballzone.activity.main

import id.hasaneljabir.footballzone.entity.repository.match.MatchRepositoryImplementation
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(val mView: MainContract.View, val matchRepositoryImplementation: MatchRepositoryImplementation) :
    MainContract.Presenter {

    val compositeDisposable = CompositeDisposable()
}