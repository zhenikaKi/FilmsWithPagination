package ru.kirea.filmswithpagination.api.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppMovie (
        val filmId: Long,
        val title: String,
        val description: String,
        val imageUrl: String? = null,
        val popularity: Double? = null,
        val releaseDate: String? = null,
        val adult: Boolean = false): Parcelable {

    constructor(movie: Movie): this (
            movie.id,
            movie.title,
            movie.overview,
            movie.posterPath,
            movie.popularity,
            movie.releaseDate,
            movie.adult
    )
}