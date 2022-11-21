package com.example.finalproject.api

import android.text.SpannableString
import com.example.finalproject.ui.Directions
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Type

interface PlacesApi {

    @GET("/maps/api/place/textsearch/json?")
    suspend fun getRestaurant(@Query("query")city : String, @Query("key")apikey: String) : PlacesReponse

    class PlacesReponse(
        val html_atrributions : List<String?>,
        val next_page_token : String?,
        val results : List<RestaurantData>,
        val status : String
    )

    @GET("/maps/api/directions/json?")
    suspend fun getDirections(@Query("origin") origin: String, @Query("destination") destination: String, @Query("key")apikey: String) : DirectionsResponse

    @GET("/maps/api/place/details/json?")
    suspend fun getRestaurantDetails(@Query("place_id") id: String, @Query("key") apikey: String) : DetailsResponse

    class DirectionsResponse(
        val routes: List<Route>
    )

    class DetailsResponse(
        val html_atrributions: List<String?>,
        val result: RestaurantDetailsData
    )

    class SpannableDeserializer : JsonDeserializer<SpannableString> {
        // @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): SpannableString {
            return SpannableString(json.asString)
        }
    }

    companion object {
        // Tell Gson to use our SpannableString deserializer
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val gsonBuilder = GsonBuilder().registerTypeAdapter(
                SpannableString::class.java, SpannableDeserializer()
            )
            return GsonConverterFactory.create(gsonBuilder.create())
        }
        // Keep the base URL simple
        //private const val BASE_URL = "https://www.reddit.com/"
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("maps.googleapis.com")
            .build()
        fun create(): PlacesApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): PlacesApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    // Enable basic HTTP logging to help with debugging.
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(buildGsonConverterFactory())
                .build()
                .create(PlacesApi::class.java)
        }
    }

}