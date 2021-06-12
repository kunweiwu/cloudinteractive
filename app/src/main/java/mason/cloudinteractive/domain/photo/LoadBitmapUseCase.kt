package mason.cloudinteractive.domain.photo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import mason.cloudinteractive.data.ApiService
import mason.cloudinteractive.domain.UseCase

open class LoadBitmapUseCase(
    private val apiService: ApiService,
    ioDispatcher: CoroutineDispatcher
) : UseCase<String, Bitmap>(ioDispatcher) {

    override suspend fun execute(parameters: String): Bitmap {
        val stream = apiService.fetchCaptcha(parameters).byteStream()
        return BitmapFactory.decodeStream(stream)
    }

}