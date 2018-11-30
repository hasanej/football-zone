package id.hasaneljabir.footballzone.feature.favteam

import id.hasaneljabir.footballzone.entity.team.Team

interface FavoriteTeamContract {

    interface View {
        fun displayTeams(teamList: List<Team>)
        fun hideLoading()
        fun showLoading()
        fun hideSwipeRefresh()
    }

    interface Presenter {
        fun getTeamData()
        fun onDestroy()
    }
}