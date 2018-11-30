package id.hasaneljabir.footballzone.entity.repository

import id.hasaneljabir.footballzone.rest.FootballRest

class PlayersRepositoryImpl(private val footballRest: FootballRest) : PlayersRepository {
    override fun getAllPlayers(teamId: String?) = footballRest.getAllPlayers(teamId)

    override fun getPlayerDetail(playerId: String?) = footballRest.getPlayerDetail(playerId)
}