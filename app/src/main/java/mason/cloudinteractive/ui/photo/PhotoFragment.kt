package mason.cloudinteractive.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mason.cloudinteractive.databinding.PhotoFragmentBinding

class PhotoFragment : Fragment() {

    private var _binding: PhotoFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: PhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhotoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.also {
            it.id.text = args.photo.id.toString()
            it.title.text = args.photo.title
            it.image.setImageDrawable(args.photo.drawable)
            it.root.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }


}