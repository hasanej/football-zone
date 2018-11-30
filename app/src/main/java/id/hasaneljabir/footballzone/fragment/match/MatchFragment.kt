package id.hasaneljabir.footballzone.fragment.match

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.ViewPagerAdapter
import id.hasaneljabir.footballzone.feature.searchmatches.SearchMatchActivity
import id.hasaneljabir.footballzone.fragment.match.lastMatch.LastMatchFragment
import id.hasaneljabir.footballzone.fragment.match.nextMatch.NextMatchFragment;
import org.jetbrains.anko.startActivity

class MatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vPager = view.findViewById<ViewPager>(R.id.viewpager)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val adapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.populateFragment(LastMatchFragment(), "Last Match")
        adapter.populateFragment(NextMatchFragment(), "Next Match")
        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?

        searchView?.queryHint = "Search Match"

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                context?.startActivity<SearchMatchActivity>("query" to query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}