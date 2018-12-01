package id.hasaneljabir.footballzone.activity.searchMatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.MatchAdapter
import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.repository.match.MatchRepositoryImplementation
import id.hasaneljabir.footballzone.extensions.hide
import id.hasaneljabir.footballzone.extensions.show
import id.hasaneljabir.footballzone.api.ApiClient
import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_search_match.*

class SearchMatchActivity : AppCompatActivity(), SearchMatchContract.View {

    private var matchLists: MutableList<Event> = mutableListOf()
    lateinit var presenter: SearchMatchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)

        val query = intent.getStringExtra("query")
        Log.v("test", query)
        val service = ApiClient.getClient().create(ApiService::class.java)
        val request = MatchRepositoryImplementation(service)
        val scheduler = AppSchedulerProvider()
        presenter = SearchMatchPresenter(this, request, scheduler)
        presenter.searchMatch(query)

    }

    override fun showLoading() {
        mainProgressBar.show()
        rvFootball.hide()
    }

    override fun hideLoading() {
        mainProgressBar.hide()
        rvFootball.show()
    }

    override fun displayMatch(matchList: List<Event>) {
        matchLists.clear()
        matchLists.addAll(matchList)
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvFootball.layoutManager = layoutManager
        rvFootball.adapter = MatchAdapter(matchList, applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?
        searchView?.queryHint = "Search Matches"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.searchMatch(newText)
                return false
            }
        })

        return true
    }
}