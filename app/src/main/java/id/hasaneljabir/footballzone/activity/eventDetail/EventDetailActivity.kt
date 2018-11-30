package id.hasaneljabir.footballzone.activity.eventDetail

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Calendars
import android.provider.CalendarContract.Events
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.entity.db.FavoriteEvent
import id.hasaneljabir.footballzone.entity.event.Event
import id.hasaneljabir.footballzone.entity.repository.local.LocalRepositoryImplementation
import id.hasaneljabir.footballzone.entity.repository.team.TeamRepositoryImplementation
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.api.ApiClient
import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.utils.CalendarHelper
import id.hasaneljabir.footballzone.utils.DateHelper
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat

class EventDetailActivity : AppCompatActivity(),
    EventDetailContract.View {
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    lateinit var event: Event

    override fun displayTeamBadgeAway(team: Team) {
        Glide.with(applicationContext)
            .load(team.strTeamBadge)
            .apply(RequestOptions().placeholder(R.drawable.ic_hourglass_empty_black_24dp))
            .into(awayImg)
    }

    override fun displayTeamBadgeHome(team: Team) {
        Glide.with(applicationContext)
            .load(team.strTeamBadge)
            .apply(RequestOptions().placeholder(R.drawable.ic_hourglass_empty_black_24dp))
            .into(homeImg)
    }

    lateinit var presenterEventDetail: EventDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val service = ApiClient.getClient().create(ApiService::class.java)
        val request = TeamRepositoryImplementation(service)
        val localRepo =
            LocalRepositoryImplementation(applicationContext)
        presenterEventDetail =
                EventDetailPresenter(this, request, localRepo)

        event = intent.getParcelableExtra("match")
        presenterEventDetail.getTeamsBadgeAway(event.idAwayTeam)
        presenterEventDetail.getTeamsBadgeHome(event.idHomeTeam)
        presenterEventDetail.checkMatch(event.idEvent)
        initData(event)
        supportActionBar?.title = event.strEvent
    }

    fun initData(event: Event) {
        if (event.intHomeScore == null) {
            dateScheduleTv.setTextColor(applicationContext.getColor(R.color.colorDateNextMatch))
        }

        dateScheduleTv.text = event.dateEvent?.let { DateHelper.formatDateToMatch(it) }
        homeNameTv.text = event.strHomeTeam
        homeScoreTv.text = event.intHomeScore
        awayNameTv.text = event.strAwayTeam
        awayScoreTv.text = event.intAwayScore

        homeScorerTv.text = event.strHomeGoalDetails
        awayScorerTv.text = event.strAwayGoalDetails

        gkHomeTv.text = event.strHomeLineupGoalkeeper
        gkAwayTv.text = event.strAwayLineupGoalkeeper

        defHomeTv.text = event.strHomeLineupDefense
        defAwayTv.text = event.strAwayLineupDefense

        midHomeTv.text = event.strHomeLineupMidfield
        midAwayTv.text = event.strAwayLineupMidfield

        forHomeTv.text = event.strHomeLineupForward
        forAwayTv.text = event.strAwayLineupForward

        subHomeTv.text = event.strHomeLineupSubstitutes
        subAwayTv.text = event.strAwayLineupSubstitutes

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
                    toast("Event added to favorite")
                    isFavorite = !isFavorite
                } else {
                    presenterEventDetail.deleteMatch(event.idEvent)
                    toast("Event removed favorite")
                    isFavorite = !isFavorite
                }
                setFavorite()
                true
            }
            R.id.notify -> {
                if (CalendarHelper.haveCalendarReadWritePermissions(this@EventDetailActivity)) {
                    addEventToGoogleCalendar();

                } else {
                    CalendarHelper.requestCalendarReadWritePermission(this@EventDetailActivity);
                }
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

    private fun getGoogleCalendarId(): Long {

        val projection = arrayOf(Calendars._ID, Calendars.NAME, Calendars.ACCOUNT_NAME, Calendars.ACCOUNT_TYPE)

        if (CalendarHelper.haveCalendarReadWritePermissions(this@EventDetailActivity)) {
            val calCursor = this.contentResolver
                .query(
                    CalendarContract.Calendars.CONTENT_URI, projection,
                    Calendars.VISIBLE + " = 1", null, Calendars._ID + " ASC"
                )

            if (calCursor!!.moveToFirst()) {
                do {
                    val id = calCursor.getLong(0)
                    return id

                } while (calCursor.moveToNext())
            }
        }
        return -1

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == CalendarHelper.CALENDARHELPER_PERMISSION_REQUEST_CODE) {
            if (CalendarHelper.haveCalendarReadWritePermissions(this)) {
                Toast.makeText(
                    this, "Have Calendar Read/Write Permission.",
                    Toast.LENGTH_LONG
                ).show()

            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun addEventToGoogleCalendar() {

        if (event.intHomeScore != null) {
            toast("This event has passed, please choose the upcoming one!")
        } else {
            val calId = getGoogleCalendarId()
            if (calId == -1L) {
                Toast.makeText(
                    this, "Somethings went wrong, try again!",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val title = event.strEvent
            val clock = event.strTime.split("+")[0]
            val dt = event.dateEvent
            val dateWithClock = "$dt $clock"
            val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val date = simpleDate.parse(dateWithClock)

            val timeInMillis = date.time

            //add end time to 90 minutes
            val end = timeInMillis + 5400000
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra(Events.TITLE, title)
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeInMillis)
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
            intent.putExtra(Events.EVENT_LOCATION, "Television")
            startActivity(intent)
        }
    }
}