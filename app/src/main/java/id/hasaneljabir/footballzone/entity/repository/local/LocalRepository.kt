package id.hasaneljabir.footballzone.entity.repository.local

import id.hasaneljabir.footballzone.entity.db.FavoriteEvent
import id.hasaneljabir.footballzone.entity.db.FavoriteTeam

interface LocalRepository {
    fun getMatchFromDb(): List<FavoriteEvent>

    fun insertData(eventId: String, homeId: String, awayId: String)

    fun deleteData(eventId: String)

    fun checkFavorite(eventId: String): List<FavoriteEvent>

    fun getTeamFromDb(): List<FavoriteTeam>

    fun insertTeamData(teamId: String, imgUrl: String)

    fun deleteTeamData(teamId: String)

    fun checkFavTeam(teamId: String): List<FavoriteTeam>
}