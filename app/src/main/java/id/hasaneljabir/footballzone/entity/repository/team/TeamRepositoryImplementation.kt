package id.hasaneljabir.footballzone.entity.repository.team

import id.hasaneljabir.footballzone.entity.team.TeamResponse
import id.hasaneljabir.footballzone.api.ApiService
import io.reactivex.Flowable

class TeamRepositoryImplementation(val apiService: ApiService) :
    TeamRepository {
    override fun getAllTeam(id: String) = apiService.getAllTeam(id)

    override fun getTeamBySearch(query: String) = apiService.getTeamBySearch(query)

    override fun getTeams(id: String): Flowable<TeamResponse> = apiService.getAllTeam(id)

    override fun getTeamsDetail(id: String): Flowable<TeamResponse> = apiService.getTeam(id)
}