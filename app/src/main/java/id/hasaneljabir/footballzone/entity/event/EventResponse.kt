package id.hasaneljabir.footballzone.entity.event

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("events") var event: List<Event>
)