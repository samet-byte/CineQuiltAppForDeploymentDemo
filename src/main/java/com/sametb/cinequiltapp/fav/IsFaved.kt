package com.sametb.cinequiltapp.fav

import com.fasterxml.jackson.annotation.JsonProperty


data class IsFaved(
    @JsonProperty("faved")
    val isFaved: Boolean
)