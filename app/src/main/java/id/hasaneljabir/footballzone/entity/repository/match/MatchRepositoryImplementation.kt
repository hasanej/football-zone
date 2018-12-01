package id.hasaneljabir.footballzone.entity.repository.match

import id.hasaneljabir.footballzone.entity.event.EventResponse
import id.hasaneljabir.footballzone.api.ApiService
import io.reactivex.Flowable

class MatchRepositoryImplementation(private val apiService: ApiService) :
    MatchRepository {
    override fun searchMatches(query: String?) = apiService.searchMatches(query)

    override fun getEventById(id: String): Flowable<EventResponse> = apiService.getEventById(id)

    override fun getUpcomingMatch(id: String): Flowable<EventResponse> = apiService.getUpcomingMatch(id)

    override fun getFootballMatch(id: String): Flowable<EventResponse> = apiService.getLastmatch(id)
}