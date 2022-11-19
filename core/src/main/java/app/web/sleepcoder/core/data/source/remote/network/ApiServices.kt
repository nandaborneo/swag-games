package app.web.sleepcoder.core.data.source.remote.network

import app.web.sleepcoder.core.data.source.remote.response.GameDetailResponse
import app.web.sleepcoder.core.data.source.remote.response.ListGameResponse
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("games/{id}")
    suspend fun getDetailGame(
        @Path("id") id: String
    ): GameDetailResponse

    @GET("games")
    suspend fun getListGame(
        @FieldMap search: Map<String, String>
    ): ListGameResponse
}
