package ru.kirea.filmswithpagination.api.entities

import com.google.gson.annotations.SerializedName

data class Configuration(
        @SerializedName("images")
        val images: Images
)