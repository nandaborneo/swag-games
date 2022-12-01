package app.web.sleepcoder.core.data.source.remote.network

import app.web.sleepcoder.core.data.source.remote.response.GameDetailResponse
import app.web.sleepcoder.core.data.source.remote.response.ListGameResponse
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("games/{slug}")
    suspend fun getDetailGame(
        @Path("slug") slug: String
    ): GameDetailResponse

    @GET("games?parent_platforms=1,2,3,4,8")
    suspend fun getListPopularGame(
        @Query("page") page: Int
    ): ListGameResponse

    @GET("games?parent_platforms=1,2,3,4,8")
    suspend fun getListSearchGame(
        @Query("search") search: String,
        @Query("page") page: Int
    ): ListGameResponse
}
