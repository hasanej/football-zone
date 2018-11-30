package id.hasaneljabir.footballzone.fragment.player

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.PlayerAdapter
import id.hasaneljabir.footballzone.entity.player.Player
import id.hasaneljabir.footballzone.entity.repository.PlayersRepositoryImpl
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.extensions.hide
import id.hasaneljabir.footballzone.extensions.show
import id.hasaneljabir.footballzone.rest.FootballApiService
import id.hasaneljabir.footballzone.rest.FootballRest
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_players.*

class PlayerFragment : Fragment(), PlayerContract.View {

    private var listPlayer: MutableList<Player> = mutableListOf()
    lateinit var mPresenter: PlayerContract.Presenter

    override fun showLoading() {
        playerProgressbar.show()
        rvPlayer.visibility = View.GONE
    }

    override fun hideLoading() {
        playerProgressbar.hide()
        rvPlayer.visibility = View.VISIBLE
    }

    override fun displayPlayers(playerList: List<Player>) {
        listPlayer.clear()
        listPlayer.addAll(playerList)
        val layoutManager = GridLayoutManager(context, 3)
        rvPlayer.layoutManager = layoutManager
        rvPlayer.adapter = PlayerAdapter(listPlayer, context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_players, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val team: Team? = arguments?.getParcelable("teams")
        val service = FootballApiService.getClient().create(FootballRest::class.java)
        val request = PlayersRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = PlayerPresenter(this, request, scheduler)
        mPresenter.getAllPlayer(team?.idTeam)

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}