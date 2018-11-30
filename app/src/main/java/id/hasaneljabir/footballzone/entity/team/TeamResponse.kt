package id.hasaneljabir.footballzone.entity.team

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams")
    var teams: List<Team>
)