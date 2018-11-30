package id.hasaneljabir.footballzone.fragment.favorite.favMatch

import id.hasaneljabir.footballzone.entity.event.Event

interface FavMatchContract {
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