package com.example.madlevel6task2

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("results") var results: List<Result>
) {

}