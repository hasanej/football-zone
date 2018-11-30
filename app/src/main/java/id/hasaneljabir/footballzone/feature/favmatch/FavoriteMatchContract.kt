package id.hasaneljabir.footballzone.feature.favmatch

import id.hasaneljabir.footballzone.entity.event.Event

interface FavoriteMatchContract {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun displayFootballMatch(matchList: List<Event>)
        fun hideSwipeRefresh()
    }

    interface Presenter {
        fun getFootballMatchData()
        fun onDestroyPresenter()
    }
}