package id.hasaneljabir.footballzone.fragment.player

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hasaneljabir.footballzone.R
import id.hasaneljabir.footballzone.adapter.PlayerAdapter
import id.hasaneljabir.footballzone.api.ApiClient
import id.hasaneljabir.footballzone.api.ApiService
import id.hasaneljabir.footballzone.entity.player.Player
import id.hasaneljabir.footballzone.entity.repository.player.PlayerRepositoryImplementation
import id.hasaneljabir.footballzone.entity.team.Team
import id.hasaneljabir.footballzone.extensions.hide
import id.hasaneljabir.footballzone.extensions.show
import id.hasaneljabir.footballzone.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : Fragment(), PlayerContract.View {

    private var listPlayer: MutableList<Player> = mutableListOf()
    lateinit var presenter: PlayerContract.Presenter

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
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val team: Team? = arguments?.getParcelable("teams")
        val service = ApiClient.getClient().create(ApiService::class.java)
        val request = PlayerRepositoryImplementation(service)
        val scheduler = AppSchedulerProvider()
        presenter = PlayerPresenter(this, request, scheduler)
        presenter.getAllPlayer(team?.idTeam)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}