package id.hasaneljabir.footballzone.feature.searchmatches

import id.hasaneljabir.footballzone.entity.event.Event

interface SearchMatchContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun displayMatch(matchList: List<Event>)
    }

    interface Presenter {
        fun searchMatch(query: String?)
        fun onDestroy()
    }
}