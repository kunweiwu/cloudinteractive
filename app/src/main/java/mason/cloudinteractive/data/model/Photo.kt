package mason.cloudinteractive.data.model

import android.graphics.drawable.Drawable
import java.io.Serializable

data class Photo(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)


data class OnePhoto(
    val id: Int,
    val title: String,
    val drawable: Drawable
): Serializable