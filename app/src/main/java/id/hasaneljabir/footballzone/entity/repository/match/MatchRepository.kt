package id.hasaneljabir.footballzone.entity.repository.match

import id.hasaneljabir.footballzone.entity.SearchMatch
import id.hasaneljabir.footballzone.entity.event.EventResponse
import io.reactivex.Flowable

interface MatchRepository {
    fun getLastMatch(id: String): Flowable<EventResponse>

    fun getNextMatch(id: String): Flowable<EventResponse>

    fun getEventById(id: String): Flowable<EventResponse>

    fun searchMatches(query: String?): Flowable<SearchMatch>
}