package mason.cloudinteractive.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mason.cloudinteractive.databinding.MainFragmentBinding
import mason.cloudinteractive.result.EventObserver

class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener(this)
        viewModel.navigateToPhotosAction.observe(viewLifecycleOwner, EventObserver {
            val action = MainFragmentDirections.actionMainFragmentToPhotosFragment()
            findNavController().navigate(action)
        })
    }

    override fun onClick(v: View) {
        viewModel.onRequestApiClicked()
    }

}