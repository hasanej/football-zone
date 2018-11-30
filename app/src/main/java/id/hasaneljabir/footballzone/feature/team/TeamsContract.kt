package id.hasaneljabir.footballzone.feature.team

import id.hasaneljabir.footballzone.entity.team.Team

interface TeamsContract {
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