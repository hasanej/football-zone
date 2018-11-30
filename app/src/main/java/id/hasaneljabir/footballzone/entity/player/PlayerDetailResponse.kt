package id.hasaneljabir.footballzone.entity.player

import com.google.gson.annotations.SerializedName

data class PlayerDetailResponse(
    @SerializedName("players")
    var player: List<Player>
)