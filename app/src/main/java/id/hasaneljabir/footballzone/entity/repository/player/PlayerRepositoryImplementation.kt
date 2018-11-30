package id.hasaneljabir.footballzone.entity.repository.player

import id.hasaneljabir.footballzone.api.ApiService

class PlayerRepositoryImplementation(private val apiService: ApiService) :
    PlayerRepository {
    override fun getAllPlayers(teamId: String?) = apiService.getAllPlayers(teamId)

    override fun getPlayerDetail(playerId: String?) = apiService.getPlayerDetail(playerId)
}