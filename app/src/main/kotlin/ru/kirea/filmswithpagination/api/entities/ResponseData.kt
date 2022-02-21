package ru.kirea.filmswithpagination.api.entities

interface ResponseData<T> {
    fun success(result: T?)
    fun error(result: String?)
}