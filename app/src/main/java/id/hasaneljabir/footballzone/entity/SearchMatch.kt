package id.hasaneljabir.footballzone.entity

import com.google.gson.annotations.SerializedName
import id.hasaneljabir.footballzone.entity.event.Event

data class SearchMatch(
    @SerializedName("event") var event: List<Event>
)