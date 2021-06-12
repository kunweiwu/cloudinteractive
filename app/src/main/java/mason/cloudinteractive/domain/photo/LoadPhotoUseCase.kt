package mason.cloudinteractive.domain.photo

import kotlinx.coroutines.CoroutineDispatcher
import mason.cloudinteractive.domain.UseCase
import mason.cloudinteractive.data.PhotoRepository
import mason.cloudinteractive.data.model.Photo

open class LoadPhotoUseCase(
    private val repository: PhotoRepository,
    ioDispatcher: CoroutineDispatcher
) : UseCase<Unit, List<Photo>>(ioDispatcher) {

    override suspend fun execute(parameters: Unit): List<Photo> = repository.getPhotos()
}