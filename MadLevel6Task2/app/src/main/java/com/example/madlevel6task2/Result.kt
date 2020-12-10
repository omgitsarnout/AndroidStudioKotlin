package com.example.madlevel6task2

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    @SerializedName("poster_path") var posterImageUrl: String,
    @SerializedName("backdrop_path") var backdropImageUrl: String,
    @SerializedName("original_title") var title: String,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("vote_average") var rating: String,
    @SerializedName("overview") var overview: String,
    var number: Number
) : Parcelable {

}