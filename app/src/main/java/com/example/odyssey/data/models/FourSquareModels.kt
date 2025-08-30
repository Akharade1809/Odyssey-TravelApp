package com.example.odyssey.data.models

import com.google.gson.annotations.SerializedName

// Main response wrapper
data class FoursquareResponse(
    @SerializedName("results")
    val results: List<FoursquarePlace>
)

// Individual place data
data class FoursquarePlace(
    @SerializedName("fsq_id")
    val fsqId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("categories")
    val categories: List<FoursquareCategory>,

    @SerializedName("location")
    val location: FoursquareLocation,

    @SerializedName("geocodes")
    val geocodes: FoursquareGeocodes,

    @SerializedName("distance")
    val distance: Int? = null,

    @SerializedName("rating")
    val rating: Double? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("photos")
    val photos: List<FoursquarePhoto>? = null,

    @SerializedName("hours")
    val hours: FoursquareHours? = null,

    @SerializedName("tips")
    val tips: List<FoursquareTip>? = null
)

data class FoursquareCategory(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("icon")
    val icon: FoursquareIcon? = null
)

data class FoursquareLocation(
    @SerializedName("address")
    val address: String? = null,

    @SerializedName("locality")
    val locality: String? = null,

    @SerializedName("region")
    val region: String? = null,

    @SerializedName("country")
    val country: String? = null,

    @SerializedName("formatted_address")
    val formattedAddress: String? = null
)

data class FoursquareGeocodes(
    @SerializedName("main")
    val main: FoursquareLatLng,

    @SerializedName("roof")
    val roof: FoursquareLatLng? = null
)

data class FoursquareLatLng(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)

data class FoursquarePhoto(
    @SerializedName("id")
    val id: String,

    @SerializedName("prefix")
    val prefix: String,

    @SerializedName("suffix")
    val suffix: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int
) {
    fun getPhotoUrl(size: String = "300x300"): String {
        return "${prefix}${size}${suffix}"
    }
}

data class FoursquareIcon(
    @SerializedName("prefix")
    val prefix: String,

    @SerializedName("suffix")
    val suffix: String
) {
    fun getIconUrl(size: String = "64"): String {
        return "${prefix}${size}${suffix}"
    }
}

data class FoursquareHours(
    @SerializedName("open_now")
    val openNow: Boolean? = null,

    @SerializedName("display")
    val display: String? = null
)

data class FoursquareTip(
    @SerializedName("id")
    val id: String,

    @SerializedName("text")
    val text: String,

    @SerializedName("created_at")
    val createdAt: String
)
