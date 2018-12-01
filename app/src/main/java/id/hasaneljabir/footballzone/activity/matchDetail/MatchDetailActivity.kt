package id.hasaneljabir.footballzone.activity.matchDetail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.api.ApiClient
import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.entity.db.FavoriteEvent
import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.repository.local.LocalRepositoryImplementation
import id.hasaneljabir.footballzone.entity.repository.team.TeamRepositoryImplementation
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.utils.DateHelper
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast

class MatchDetailActivity : AppCompatActivity(),
    MatchDetailContract.View {
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    lateinit var event: Event

    override fun displayTeamBadgeAway(team: Team) {
        Glide.with(applicationContext)
            .load(team.strTeamBadge)
            .apply(RequestOptions().placeholder(R.drawable.ic_no_image))
            .into(imgAway)
    }

    override fun displayTeamBadgeHome(team: Team) {
        Glide.with(applicationContext)
            .load(team.strTeamBadge)
            .apply(RequestOptions().placeholder(R.drawable.ic_no_image))
            .into(imgHome)
    }

    lateinit var presenterEventDetail: MatchDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val service = ApiClient.getClient().create(ApiService::class.java)
        val request = TeamRepositoryImplementation(service)
        val localRepo =
            LocalRepositoryImplementation(applicationContext)
        presenterEventDetail =
                MatchDetailPresenter(this, request, localRepo)

        event = intent.getParcelableExtra("match")
        presenterEventDetail.getTeamsBadgeAway(event.idAwayTeam)
        presenterEventDetail.getTeamsBadgeHome(event.idHomeTeam)
        presenterEventDetail.checkMatch(event.idEvent)
        initData(event)
        supportActionBar?.title = event.strEvent
    }

    fun initData(event: Event) {
        if (event.intHomeScore == null) {
            tvDate.setTextColor(applicationContext.getColor(R.color.colorDateNextMatch))
            tvTime.setTextColor(applicationContext.getColor(R.color.colorDateNextMatch))
        }

        tvDate.text = event.dateEvent?.let { DateHelper.formatDateToMatch(it) }
        tvTime.text = event.strTime.take(5)
        tvHomeName.text = event.strHomeTeam
        tvHomeScore.text = event.intHomeScore
        tvAwayName.text = event.strAwayTeam
        tvAwayScore.text = event.intAwayScore

        tvHomeScorer.text = event.strHomeGoalDetails
        tvAwayScorer.text = event.strAwayGoalDetails

        tvHomeGk.text = event.strHomeLineupGoalkeeper
        tvAwayGk.text = event.strAwayLineupGoalkeeper

        tvHomeDef.text = event.strHomeLineupDefense
        tvAwayDef.text = event.strAwayLineupDefense

        tvHomeMid.text = event.strHomeLineupMidfield
        tvAwayMid.text = event.strAwayLineupMidfield

        tvHomeForward.text = event.strHomeLineupForward
        tvAwayForward.text = event.strAwayLineupForward

        tvHomeSub.text = event.strHomeLineupSubstitutes
        tvAwaySub.text = event.strAwayLineupSubstitutes
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite -> {
                if (!isFavorite) {
                    presenterEventDetail.insertMatch(
                        event.idEvent, event.idHomeTeam, event.idAwayTeam
                    )
                    toast("Match added to favorite")
                    isFavorite = !isFavorite
                } else {
                    presenterEventDetail.deleteMatch(event.idEvent)
                    toast("Match removed from favorite")
                    isFavorite = !isFavorite
                }
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_fav_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_fav_24dp)
    }

    override fun setFavoriteState(favList: List<FavoriteEvent>) {
        if (!favList.isEmpty()) isFavorite = true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterEventDetail.onDestroyPresenter()
    }
}