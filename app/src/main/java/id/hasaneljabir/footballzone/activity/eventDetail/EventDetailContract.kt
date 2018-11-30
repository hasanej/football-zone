package id.hasaneljabir.footballzone.activity.eventDetail

import id.hasaneljabir.footballzone.entity.db.FavoriteEvent
import id.hasaneljabir.footballzone.entity.team.Team

interface EventDetailContract {

    interface View {
        fun displayTeamBadgeHome(team: Team)
        fun displayTeamBadgeAway(team: Team)
        fun setFavoriteState(favList: List<FavoriteEvent>)
    }

    interface Presenter {
        fun getTeamsBadgeAway(id: String)
        fun getTeamsBadgeHome(id: String)
        fun deleteMatch(id: String)
        fun checkMatch(id: String)
        fun insertMatch(eventId: String, homeId: String, awayId: String)
        fun onDestroyPresenter()
    }
}