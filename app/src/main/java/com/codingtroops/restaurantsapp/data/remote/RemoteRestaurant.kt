package com.codingtroops.restaurantsapp.data.remote

import com.google.gson.annotations.SerializedName
import java.io.FileDescriptor

data class RemoteRestaurant(
    @SerializedName("r_id")
    val id: Int,
    @SerializedName("r_title")
    val title: String,
    @SerializedName("r_description")
    val descripton: String
)