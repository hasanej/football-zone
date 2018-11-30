package id.hasaneljabir.footballzone.feature.playerdetail

import id.hasaneljabir.footballzone.entity.player.Player

interface PayerDetailContract {

    interface View {
        fun displayPlayerDetail(player: Player)
    }

    interface Presenter {
        fun getPlayerData(idPlayer: String)
        fun onDestroy()
    }
}