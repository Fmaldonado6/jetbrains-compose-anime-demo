package services

import models.AnimeSearchResponse
import models.CharacterResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanService {

    @GET("search/anime?q=")
    fun searchAnime(@Query("q") animeName: String): Call<AnimeSearchResponse>

    @GET("anime/{id}/characters_staff")
    fun getCharacters(@Path("id") animeId: String): Call<CharacterResponse>

    companion object {
        operator fun invoke(): JikanService {

            val client = OkHttpClient.Builder().addInterceptor(NetworkInterceptor).build()

            return Retrofit
                .Builder()
                .baseUrl("https://api.jikan.moe/v3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JikanService::class.java)
        }
    }
}