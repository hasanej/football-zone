package id.hasaneljabir.footballzone.entity.repository

import id.hasaneljabir.footballzone.entity.event.EventResponse
import id.hasaneljabir.footballzone.rest.FootballRest
import io.reactivex.Flowable

class MatchRepositoryImpl(private val footballRest: FootballRest) : MatchRepository {
    override fun searchMatches(query: String?) = footballRest.searchMatches(query)

    override fun getEventById(id: String): Flowable<EventResponse> = footballRest.getEventById(id)

    override fun getUpcomingMatch(id: String): Flowable<EventResponse> = footballRest.getUpcomingMatch(id)

    override fun getFootballMatch(id: String): Flowable<EventResponse> = footballRest.getLastmatch(id)
}