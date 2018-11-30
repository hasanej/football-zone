package id.hasaneljabir.footballzone.fragment.match

import id.hasaneljabir.footballzone.entity.event.Event

interface MatchContract {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun displayFootballMatch(matchList: List<Event>)
    }

    interface Presenter {
        fun getFootballMatchData(leagueName: String = "4328")
        fun onDestroyPresenter()
    }
}