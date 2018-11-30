package id.hasaneljabir.footballzone.entity.player

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("player")
    var player: List<Player>
)