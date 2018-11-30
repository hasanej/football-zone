package id.hasaneljabir.footballzone.feature.favteam

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.TeamAdapter
import id.hasaneljabir.footballzone.entity.repository.LocalRepositoryImpl
import id.hasaneljabir.footballzone.entity.repository.TeamRepositoryImpl
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.extensions.hide
import id.hasaneljabir.footballzone.extensions.show
import id.hasaneljabir.footballzone.rest.FootballApiService
import id.hasaneljabir.footballzone.rest.FootballRest
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_favorite_team.*

class FavoriteTeamFragment : Fragment(), FavoriteTeamContract.View {

    private var teamLists: MutableList<Team> = mutableListOf()
    lateinit var mPresenter: FavoriteTeamPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sercive = FootballApiService.getClient().create(FootballRest::class.java)
        val localRepositoryImpl = LocalRepositoryImpl(context!!)
        val teamRepositoryImpl = TeamRepositoryImpl(sercive)
        val scheduler = AppSchedulerProvider()
        mPresenter = FavoriteTeamPresenter(this, localRepositoryImpl, teamRepositoryImpl, scheduler)
        mPresenter.getTeamData()

        swiperefresh.setOnRefreshListener {
            mPresenter.getTeamData()
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
        mPresenter.onDestroy()
    }
}