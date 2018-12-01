package id.hasaneljabir.footballzone

import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.event.EventResponse
import id.hasaneljabir.footballzone.entity.repository.match.MatchRepositoryImplementation
import id.hasaneljabir.footballzone.fragment.match.MatchContract
import id.hasaneljabir.footballzone.fragment.match.lastMatch.LastMatchPresenter
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import id.hasaneljabir.footballzone.utils.TestSchedulerProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest {

    @Mock
    lateinit var view: MatchContract.View

    @Mock
    lateinit var matchRepositoryImplementation: MatchRepositoryImplementation

    lateinit var scheduler: SchedulerProvider

    lateinit var presenter: LastMatchPresenter

    lateinit var match: EventResponse

    lateinit var footballMatch: Flowable<EventResponse>

    private val event = mutableListOf<Event>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = TestSchedulerProvider()
        match = EventResponse(event)
        footballMatch = Flowable.just(match)
        presenter = LastMatchPresenter(view, matchRepositoryImplementation, scheduler)
        `when`(matchRepositoryImplementation.getLastMatch("4328")).thenReturn(footballMatch)
    }

    @Test
    fun getFootballMatchData() {
        presenter.getFootballMatchData()
        verify(view).showLoading()
        verify(view).displayFootballMatch(event)
        verify(view).hideLoading()
    }
}