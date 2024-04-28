package com.example.wonderland

import com.example.wonderland.Models.AgeModel
import com.example.wonderland.Models.AttractionDetailResponse
import com.example.wonderland.Models.AttractionModel
import com.example.wonderland.Models.AttractionTicketRequestModel
import com.example.wonderland.Models.EventDetailResponseModel
import com.example.wonderland.Models.TicketModel
import com.example.wonderland.Models.EventResponseModel
import com.example.wonderland.Models.EventTicketRequestModel
import com.example.wonderland.Models.RestaurantDetailResponse
import com.example.wonderland.Models.RestaurantResponseModel
import com.example.wonderland.Models.SignupRequestModel
import com.example.wonderland.Models.TagModel
import com.example.wonderland.Models.TokenResponseModel
import com.example.wonderland.Models.UserModel
import com.example.wonderland.Models.UserRequestModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .baseUrl("https://49df-92-253-236-112.ngrok-free.app/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val attractionsService = retrofit.create(AttractionService::class.java)
val eventsService = retrofit.create(EventService::class.java)
val restaurantsService = retrofit.create(RestaurantService::class.java)
val authService = retrofit.create(AuthService::class.java)
val attractionTicketService = retrofit.create(AttractionTicketsService::class.java)
val eventTicketService = retrofit.create(EventTicketsService::class.java)
val tagService = retrofit.create(TagService::class.java)
val ageService = retrofit.create(AgeService::class.java)


interface AttractionService {
    @GET("attractions")
    suspend fun getAllAttractions(
        @Query("tag_names") tagNames: String? = null,
        @Query("age_names") ageNames: String? = null
    ): List<AttractionModel>

    @GET("attractions/{id}")
    suspend fun getAttractionById(
        @Path("id") id: Int
    ): AttractionDetailResponse
}

interface TagService {
    @GET("tags")
    suspend fun getAllTags(): List<TagModel>
}

interface AgeService {
    @GET("ages")
    suspend fun getAllAges(): List<AgeModel>
}

interface EventService {
    @GET("events")
    suspend fun getAllEvents(): List<EventResponseModel>

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): EventDetailResponseModel
}

interface RestaurantService {
    @GET("restaurants")
    suspend fun getAllRestaurants(): List<RestaurantResponseModel>

    @GET("restaurants/{id}")
    suspend fun getRestaurantById(
        @Path("id") id: Int
    ): RestaurantDetailResponse
}

interface AuthService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): TokenResponseModel

    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequestModel
    ): Response<TokenResponseModel>

    @GET("auth/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): UserModel

    @DELETE("auth/me")
    suspend fun deleteMe(
        @Header("Authorization") token: String?
    )

    @PUT("auth/me")
    suspend fun updateMe(
        @Header("Authorization") token: String?,
        @Body request: UserRequestModel
    )
}

interface AttractionTicketsService {
    @GET("attraction-tickets")
    suspend fun getAllAttractionTickets(
        @Header("Authorization") token: String?,
        @Query("sort") sort: Int = -1
    ): List<TicketModel>

    @POST("attraction-tickets")
    suspend fun addAttractionTicket(
        @Header("Authorization") token: String?,
        @Body request: AttractionTicketRequestModel
    )
}

interface EventTicketsService {
    @GET("event-tickets")
    suspend fun getAllEventTickets(
        @Header("Authorization") token: String?,
        @Query("sort") sort: Int = -1
    ): List<TicketModel>

    @POST("event-tickets")
    suspend fun addEventTicket(
        @Header("Authorization") token: String?,
        @Body request: EventTicketRequestModel
    )
}