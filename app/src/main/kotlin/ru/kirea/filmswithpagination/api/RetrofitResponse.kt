package ru.kirea.filmswithpagination.api
import retrofit2.Response

class RetrofitResponse<T>(private val response: Response<T>) {
    //обработать ответ, полученный через Retrofit
    fun getResult(): T? {
        if (response.isSuccessful) {
            return response.body()
        } else {
            //получаем ошибку от сервера
            val error = response.errorBody()?.string()
            error ?. let {
                throw RuntimeException(java.lang.String.format("Ошибка запроса: %s", error))
            } ?: throw  RuntimeException("Не получилось обработать ответ сервера")
        }
    }
}