

package com.kaklis.android.isearchmovies.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class TrailerResponse(

        @Expose
        @SerializedName("id")
        var id: Integer,
        @Expose
        @SerializedName("results")
        var results: List<Trailer>)

