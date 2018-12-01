package id.hasaneljabir.footballzone.entity.repository.match

import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.entity.event.EventResponse
import io.reactivex.Flowable

class MatchRepositoryImplementation(private val apiService: ApiService) :
    MatchRepository {
    override fun searchMatches(query: String?) = apiService.searchMatches(query)

    override fun getEventById(id: String): Flowable<EventResponse> = apiService.getEventById(id)

    override fun getNextMatch(id: String): Flowable<EventResponse> = apiService.getNextMatch(id)

    override fun getLastMatch(id: String): Flowable<EventResponse> = apiService.getLastmatch(id)
}