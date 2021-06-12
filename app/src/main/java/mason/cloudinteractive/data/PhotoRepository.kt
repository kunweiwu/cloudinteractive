package mason.cloudinteractive.data

import mason.cloudinteractive.data.model.Photo

interface PhotoRepository {
    suspend fun getPhotos(): List<Photo>
}

class DefaultPhotoRepository(
    private val apiService: ApiService
) : PhotoRepository {

    override suspend fun getPhotos(): List<Photo> {
        return apiService.getPhotos()
    }
}