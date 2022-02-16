package ru.kirea.filmswithpagination.api.entities

import com.google.gson.annotations.SerializedName

data class MovieFined (
        @SerializedName("page")
        val page: Int,

        @SerializedName("total_pages")
        val totalPages: Int,

        @SerializedName("total_results")
        val totalResults: Int,

        @SerializedName("results")
        val movies: List<Movie>
)