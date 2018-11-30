package id.hasaneljabir.footballzone.fragment.favorite.favTeam

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.TeamAdapter
import id.hasaneljabir.footballzone.api.ApiClient
import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.entity.repository.local.LocalRepositoryImplementation
import id.hasaneljabir.footballzone.entity.repository.team.TeamRepositoryImplementation
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.extensions.hide
import id.hasaneljabir.footballzone.extensions.show
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_favorite_team.*

class FavTeamFragment : Fragment(), FavTeamContract.View {

    private var teamLists: MutableList<Team> = mutableListOf()
    lateinit var presenter: FavTeamPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = ApiClient.getClient().create(ApiService::class.java)
        val localRepositoryImpl = LocalRepositoryImplementation(context!!)
        val teamRepositoryImpl = TeamRepositoryImplementation(service)
        val scheduler = AppSchedulerProvider()
        presenter = FavTeamPresenter(this, localRepositoryImpl, teamRepositoryImpl, scheduler)
        presenter.getTeamData()

        swiperefresh.setOnRefreshListener {
            presenter.getTeamData()
        }
    }


    override fun displayTeams(teamList: List<Team>) {
        teamLists.clear()
        teamLists.addAll(teamList)
        val layoutManager = GridLayoutManager(context, 3)
        rvTeam.layoutManager = layoutManager
        rvTeam.adapter = TeamAdapter(teamLists, context)
    }

    override fun hideLoading() {
        mainProgressBar.hide()
        rvTeam.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgressBar.show()
        rvTeam.visibility = View.GONE
    }

    override fun hideSwipeRefresh() {
        swiperefresh.isRefreshing = false
        mainProgressBar.hide()
        rvTeam.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}