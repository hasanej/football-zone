package id.hasaneljabir.footballzone.entity.repository.local

import android.content.Context
import id.hasaneljabir.footballzone.entity.db.FavoriteEvent
import id.hasaneljabir.footballzone.entity.db.FavoriteTeam
import id.hasaneljabir.footballzone.entity.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class LocalRepositoryImplementation(private val context: Context) :
    LocalRepository {
    override fun getTeamFromDb(): List<FavoriteTeam> {
        lateinit var favoriteList: List<FavoriteTeam>
        context.database.use {
            val result = select(FavoriteTeam.TEAM_TABLE)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favoriteList = favorite
        }
        return favoriteList
    }

    override fun insertTeamData(teamId: String, imgUrl: String) {
        context.database.use {
            insert(
                FavoriteTeam.TEAM_TABLE,
                FavoriteTeam.TEAM_ID to teamId,
                FavoriteTeam.TEAM_IMAGE to imgUrl
            )

        }

    }

    override fun deleteTeamData(teamId: String) {
        context.database.use {
            delete(
                FavoriteTeam.TEAM_TABLE, "(TEAM_ID = {id})",
                "id" to teamId
            )
        }
    }

    override fun checkFavTeam(teamId: String): List<FavoriteTeam> {
        return context.database.use {
            val result = select(FavoriteTeam.TEAM_TABLE)
                .whereArgs(
                    "(TEAM_ID = {id})",
                    "id" to teamId
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorite
        }
    }

    override fun checkFavorite(eventId: String): List<FavoriteEvent> {
        return context.database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
                .whereArgs(
                    "(EVENT_ID = {id})",
                    "id" to eventId
                )
            val favorite = result.parseList(classParser<FavoriteEvent>())
            favorite
        }
    }


    override fun deleteData(eventId: String) {
        context.database.use {
            delete(
                FavoriteEvent.TABLE_FAVORITE, "(EVENT_ID = {id})",
                "id" to eventId
            )
        }
    }

    override fun insertData(eventId: String, homeId: String, awayId: String) {
        context.database.use {
            insert(
                FavoriteEvent.TABLE_FAVORITE,
                FavoriteEvent.EVENT_ID to eventId,
                FavoriteEvent.HOME_TEAM_ID to homeId,
                FavoriteEvent.AWAY_TEAM_ID to awayId
            )

        }
    }

    override fun getMatchFromDb(): List<FavoriteEvent> {
        lateinit var favoriteList: List<FavoriteEvent>
        context.database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            favoriteList = favorite
        }
        return favoriteList
    }
}