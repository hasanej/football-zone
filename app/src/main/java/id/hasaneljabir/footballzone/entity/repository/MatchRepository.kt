package id.hasaneljabir.footballzone.entity.repository

import id.hasaneljabir.footballzone.entity.event.EventResponse
import id.hasaneljabir.footballzone.entity.SearchedEvent
import io.reactivex.Flowable

interface MatchRepository {
    fun getFootballMatch(id: String): Flowable<EventResponse>

    fun getUpcomingMatch(id: String): Flowable<EventResponse>

    fun getEventById(id: String): Flowable<EventResponse>

    fun searchMatches(query: String?): Flowable<SearchedEvent>
}