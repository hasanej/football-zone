package id.hasaneljabir.footballzone.fragment.team

import id.hasaneljabir.footballzone.entity.team.Team

interface TeamContract {
    interface View {
        fun displayTeams(teamList: List<Team>)
        fun hideLoading()
        fun showLoading()

    }

    interface Presenter {
        fun getTeamData(leagueName: String)
        fun searchTeam(teamName: String)
        fun onDestroy()
    }
}