package id.hasaneljabir.footballzone.activity.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.feature.FavoriteFragment
import id.hasaneljabir.footballzone.fragment.match.MatchFragment
import id.hasaneljabir.footballzone.feature.team.TeamsFragment
import kotlinx.android.synthetic.main.bottom_nav_view.*
import kotlinx.android.synthetic.main.home_activity.*

class MainActivity : AppCompatActivity(), MainContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setSupportActionBar(toolbar_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.lastMatch -> {
                    loadLastMatch(savedInstanceState)
                }
                R.id.nextMatch -> {
                    loadNextMatch(savedInstanceState)
                }
                R.id.favMatch -> {
                    loadFavoritesMatch(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.lastMatch
    }

    private fun loadLastMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    MatchFragment(), MatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadNextMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, TeamsFragment(), TeamsFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavoriteFragment(), FavoriteFragment::class.java.simpleName)
                .commit()
        }
    }
}