package id.hasaneljabir.footballzone.fragment.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.TeamAdapter
import id.hasaneljabir.footballzone.api.ApiClient
import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.entity.repository.team.TeamRepositoryImplementation
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.extensions.hide
import id.hasaneljabir.footballzone.extensions.show
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_team.*

class TeamFragment : Fragment(), TeamContract.View {

    lateinit var leagueName: String
    lateinit var mPresenter: TeamContract.Presenter

    private var teamLists: MutableList<Team> = mutableListOf()

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
        rvTeam.visibility = View.INVISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = ApiClient.getClient().create(ApiService::class.java)
        val request = TeamRepositoryImplementation(service)
        val scheduler = AppSchedulerProvider()
        setHasOptionsMenu(true)
        mPresenter = TeamPresenter(this, request, scheduler)
        mPresenter.getTeamData("4328")
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerTeam.adapter = spinnerAdapter
        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinnerTeam.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> mPresenter.getTeamData("4328")
                    "German Bundesliga" -> mPresenter.getTeamData("4331")
                    "Italian Serie A" -> mPresenter.getTeamData("4332")
                    "French Ligue 1" -> mPresenter.getTeamData("4334")
                    "Spanish La Liga" -> mPresenter.getTeamData("4335")
                    "Netherlands Eredivisie" -> mPresenter.getTeamData("4337")
                    else -> mPresenter.getTeamData("4328")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?
        searchView?.queryHint = "Search team"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mPresenter.searchTeam(newText)
                return false
            }
        })

        searchView?.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                mPresenter.getTeamData("4328")
                return true
            }
        })
    }
}
