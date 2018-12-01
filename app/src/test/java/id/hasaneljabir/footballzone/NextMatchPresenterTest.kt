package id.hasaneljabir.footballzone

import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.event.EventResponse
import id.hasaneljabir.footballzone.entity.repository.match.MatchRepositoryImplementation
import id.hasaneljabir.footballzone.fragment.match.MatchContract
import id.hasaneljabir.footballzone.fragment.match.nextMatch.NextMatchPresenter
import id.hasaneljabir.footballzone.utils.SchedulerProvider
import id.hasaneljabir.footballzone.utils.TestSchedulerProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {

    @Mock
    lateinit var view: MatchContract.View

    @Mock
    lateinit var matchRepositoryImplementation: MatchRepositoryImplementation

    lateinit var scheduler: SchedulerProvider

    lateinit var presenter: NextMatchPresenter

    lateinit var match: EventResponse

    lateinit var footballMatch: Flowable<EventResponse>

    private val event = mutableListOf<Event>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = TestSchedulerProvider()
        match = EventResponse(event)
        footballMatch = Flowable.just(match)
        presenter = NextMatchPresenter(view, matchRepositoryImplementation, scheduler)
        Mockito.`when`(matchRepositoryImplementation.getNextMatch("4328")).thenReturn(footballMatch)
    }

    @Test
    fun getFootballMatchData() {
        presenter.getFootballMatchData()
        Mockito.verify(view).showLoading()
        Mockito.verify(view).displayFootballMatch(event)
        Mockito.verify(view).hideLoading()
    }
}