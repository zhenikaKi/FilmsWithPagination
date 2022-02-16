package ru.kirea.filmswithpagination.api

object ApiSetting {
    //размеры постеров, один из которых будет использоваться
    val priorPosterSizes = arrayListOf("w500", "w342", "original")

    var secureBaseUrl: String? = null
    var posterSize: String? = null

    //получить ссылку на изображение
    fun getUrlToImage(imageName: String) = "$secureBaseUrl$posterSize$imageName"
}