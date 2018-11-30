package id.hasaneljabir.footballzone.fragment.player

import id.hasaneljabir.footballzone.entity.player.Player

interface PlayerContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun displayPlayers(playerList: List<Player>)
    }

    interface Presenter {
        fun getAllPlayer(teamId: String?)
        fun onDestroy()
    }
}