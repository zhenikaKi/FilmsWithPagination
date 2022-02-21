package ru.kirea.filmswithpagination.api.entities

import com.google.gson.annotations.SerializedName

data class Images(
        @SerializedName("base_url")
        val baseUrl: String,

        @SerializedName("secure_base_url")
        val secureBaseUrl: String,

        @SerializedName("poster_sizes")
        val posterSizes: List<String>
)