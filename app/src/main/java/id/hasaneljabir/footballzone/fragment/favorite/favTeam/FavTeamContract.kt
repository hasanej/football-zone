package id.hasaneljabir.footballzone.fragment.favorite.favTeam

import id.hasaneljabir.footballzone.entity.team.Team

interface FavTeamContract {

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