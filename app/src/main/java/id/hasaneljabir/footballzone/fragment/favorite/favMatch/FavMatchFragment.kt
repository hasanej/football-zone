package id.hasaneljabir.footballzone.fragment.favorite.favMatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.MatchAdapter
import id.hasaneljabir.footballzone.api.ApiClient
import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.repository.local.LocalRepositoryImplementation
import id.hasaneljabir.footballzone.entity.repository.match.MatchRepositoryImplementation
import id.hasaneljabir.footballzone.extensions.hide
import id.hasaneljabir.footballzone.extensions.show
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_favorite_match.*

class FavMatchFragment : Fragment(), FavMatchContract.View {
    private var matchLists: MutableList<Event> = mutableListOf()
    lateinit var presenter: FavMatchPresenter

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = ApiClient.getClient().create(ApiService::class.java)
        val request = MatchRepositoryImplementation(service)
        val localRepositoryImpl =
            LocalRepositoryImplementation(context!!)
        val scheduler = AppSchedulerProvider()
        presenter = FavMatchPresenter(
            this,
            request,
            localRepositoryImpl,
            scheduler
        )
        presenter.getFootballMatchData()

        swiperefreshFav.setOnRefreshListener {
            presenter.getFootballMatchData()
        }

    }

    override fun hideSwipeRefresh() {
        swiperefreshFav.isRefreshing = false
        mainProgressBar.hide()
        rvFootball.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyPresenter()
    }
}