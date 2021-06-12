package mason.cloudinteractive.ui.photo

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mason.cloudinteractive.data.ApiProvider
import mason.cloudinteractive.data.model.OnePhoto
import mason.cloudinteractive.data.model.Photo
import mason.cloudinteractive.databinding.ItemPhotoBinding
import mason.cloudinteractive.databinding.PhotosFragmentBinding
import mason.cloudinteractive.domain.photo.LoadBitmapUseCase
import mason.cloudinteractive.result.data

class PhotosFragment : Fragment(), ItemClickCallback<OnePhoto> {

    private var _binding: PhotosFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhotosViewModel by viewModels()

    private val myAdapter = MyAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhotosFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photoList.adapter = myAdapter

        viewModel.photos.observe(viewLifecycleOwner, {
            myAdapter.submitList(it)
        })
    }

    override fun onClick(item: OnePhoto) {
        val action = PhotosFragmentDirections.actionPhotosFragmentToPhotoFragment(item)
        findNavController().navigate(action)
    }

}

object DiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

}

class PhotoViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        val LOADED = "imageLoaded"
    }

    private val loadBitmapUseCase = LoadBitmapUseCase(ApiProvider.apiService, Dispatchers.IO)

    fun bind(photo: Photo, clickCallback: ItemClickCallback<OnePhoto>) {
        binding.let {
            it.id.text = photo.id.toString()
            it.title.text = photo.title
            it.image.setImageBitmap(null)
            it.image.tag = null
            CoroutineScope(Dispatchers.IO).launch {
                val bitmap = loadBitmapUseCase(photo.thumbnailUrl).data ?: return@launch
                withContext(Dispatchers.Main) {
                    it.image.setImageBitmap(bitmap)
                    it.image.tag = LOADED
                }
            }
            it.root.setOnClickListener { _ ->
                if (it.image.tag != LOADED) return@setOnClickListener
                val drawable: Drawable = it.image.drawable ?: return@setOnClickListener
                clickCallback.onClick(OnePhoto(photo.id, photo.title, drawable))
            }
        }
    }
}

class MyAdapter(private val clickCallback: ItemClickCallback<OnePhoto>) :
    ListAdapter<Photo, PhotoViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), clickCallback)
    }

}

interface ItemClickCallback<T> {
    fun onClick(item: T)
}