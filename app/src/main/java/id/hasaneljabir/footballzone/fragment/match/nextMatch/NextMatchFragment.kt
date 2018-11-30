package id.hasaneljabir.footballzone.fragment.match.nextMatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.MatchAdapter
import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.repository.MatchRepositoryImpl
import id.hasaneljabir.footballzone.extensions.hide
import id.hasaneljabir.footballzone.extensions.show
import id.hasaneljabir.footballzone.fragment.match.MatchContract
import id.hasaneljabir.footballzone.rest.FootballApiService
import id.hasaneljabir.footballzone.rest.FootballRest
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_upcoming_match.*

class NextMatchFragment : Fragment(), MatchContract.View {
    lateinit var presenter: NextMatchPresenter
    lateinit var leagueName: String
    private var matchLists: MutableList<Event> = mutableListOf()

    override fun hideLoading() {
        mainProgressBar.hide()
        rvFootball.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgressBar.show()
        rvFootball.visibility = View.INVISIBLE
    }

    override fun displayFootballMatch(matchList: List<Event>) {
        matchLists.clear()
        matchLists.addAll(matchList)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvFootball.layoutManager = layoutManager
        rvFootball.adapter = MatchAdapter(matchList, context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_upcoming_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = FootballApiService.getClient().create(FootballRest::class.java)
        val request = MatchRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        presenter = NextMatchPresenter(this, request, scheduler)
        presenter.getFootballMatchData()
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerMatch.adapter = spinnerAdapter

        spinnerMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinnerMatch.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> presenter.getFootballMatchData("4328")
                    "German Bundesliga" -> presenter.getFootballMatchData("4331")
                    "Italian Serie A" -> presenter.getFootballMatchData("4332")
                    "French Ligue 1" -> presenter.getFootballMatchData("4334")
                    "Spanish La Liga" -> presenter.getFootballMatchData("4335")
                    "Netherlands Eredivisie" -> presenter.getFootballMatchData("4337")
                    else -> presenter.getFootballMatchData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyPresenter()
    }
}