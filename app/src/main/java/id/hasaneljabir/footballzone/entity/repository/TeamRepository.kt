package id.hasaneljabir.footballzone.entity.repository

import id.hasaneljabir.footballzone.entity.team.TeamResponse
import io.reactivex.Flowable

interface TeamRepository {
    fun getTeams(id: String = "0"): Flowable<TeamResponse>

    fun getTeamsDetail(id: String = "0"): Flowable<TeamResponse>

    fun getTeamBySearch(query: String): Flowable<TeamResponse>

    fun getAllTeam(id: String): Flowable<TeamResponse>
}