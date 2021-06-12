package mason.cloudinteractive.data

import mason.cloudinteractive.data.model.Photo
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("/photos")
    suspend fun getPhotos(): List<Photo>

    @GET
    suspend fun fetchCaptcha(@Url url: String): ResponseBody
}