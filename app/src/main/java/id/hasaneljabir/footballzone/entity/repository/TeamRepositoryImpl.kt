package id.hasaneljabir.footballzone.entity.repository

import id.hasaneljabir.footballzone.entity.team.TeamResponse
import id.hasaneljabir.footballzone.rest.FootballRest
import io.reactivex.Flowable

class TeamRepositoryImpl(val footballRest: FootballRest) : TeamRepository {
    override fun getAllTeam(id: String) = footballRest.getAllTeam(id)

    override fun getTeamBySearch(query: String) = footballRest.getTeamBySearch(query)

    override fun getTeams(id: String): Flowable<TeamResponse> = footballRest.getAllTeam(id)

    override fun getTeamsDetail(id: String): Flowable<TeamResponse> = footballRest.getTeam(id)
}