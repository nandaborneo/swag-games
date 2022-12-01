package app.web.sleepcoder.core.data.source.remote

import android.util.Log
import app.web.sleepcoder.core.data.source.remote.network.ApiResponse
import app.web.sleepcoder.core.data.source.remote.network.ApiServices
import app.web.sleepcoder.core.data.source.remote.response.GameDetailResponse
import app.web.sleepcoder.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getPopularGame(page: Int): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiServices.getListPopularGame(page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchGame(search: String, page: Int): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiServices.getListSearchGame(search, page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailGame(slug: String): Flow<ApiResponse<GameDetailResponse>> {
        return flow {
            try {
                val response = apiServices.getDetailGame(slug)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}