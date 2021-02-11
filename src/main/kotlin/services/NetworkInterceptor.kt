package services

import okhttp3.Interceptor
import okhttp3.Response

object NetworkInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.isSuccessful)
            return response

        if (response.code == 400)
            throw BadInput(response.body)
        if (response.code == 404)
            throw NotFoundError(response.body)

        if (response.code == 500)
            throw  EmptySearch(response.body)
        throw  AppError(response.body)
    }
}