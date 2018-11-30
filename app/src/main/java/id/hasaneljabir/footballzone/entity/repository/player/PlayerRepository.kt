package id.hasaneljabir.footballzone.entity.repository.player

import id.hasaneljabir.footballzone.entity.player.PlayerResponse
import id.hasaneljabir.footballzone.entity.player.PlayerDetailResponse
import io.reactivex.Flowable

interface PlayerRepository {
    fun getAllPlayers(teamId: String?): Flowable<PlayerResponse>
    fun getPlayerDetail(playerId: String?): Flowable<PlayerDetailResponse>
}