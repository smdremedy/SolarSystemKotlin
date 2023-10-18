package pl.szkoleniaandroid.solarsystem.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.R
import pl.szkoleniaandroid.solarsystem.domain.SolarObject
import pl.szkoleniaandroid.solarsystem.databinding.SolarObjectsFragmentBinding
import pl.szkoleniaandroid.solarsystem.api.model.SolarObjectJson
import pl.szkoleniaandroid.solarsystem.screens.details.ObjectDetailsFragmentArgs

abstract class SolarObjectsFragment : Fragment() {

    private var solarObjectsAdapter: SolarObjectsAdapter? = null
    private lateinit var binding: SolarObjectsFragmentBinding
    abstract val viewModel: SolarObjectsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SolarObjectsFragmentBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        solarObjectsAdapter = SolarObjectsAdapter { clickedObject ->
            viewModel.showObject(clickedObject)
        }
        binding.objectsRecyclerView.adapter = solarObjectsAdapter
        binding.objectsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    render(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.flowWithLifecycle(lifecycle).collect {
                handleEffect(it)
            }
        }

        viewModel.fetchObjects()
    }

    private fun handleEffect(effect: SolarObjectsViewModel.Effect) {
        when (effect) {
            is SolarObjectsViewModel.Effect.ShowObject -> {
                val args = ObjectDetailsFragmentArgs.Builder(effect.objectToShow).build()
                findNavController().navigate(R.id.nav_details, args.toBundle())
            }
        }
    }

    private fun render(state: SolarObjectsViewModel.UiState) {
        when (state) {
            SolarObjectsViewModel.UiState.Loading -> {
                binding.loadingProgress.isInvisible = false
            }

            is SolarObjectsViewModel.UiState.Success -> {
                binding.loadingProgress.isInvisible = true
                solarObjectsAdapter?.setObjects(state.objects)
            }

            is SolarObjectsViewModel.UiState.Error -> {
                binding.loadingProgress.isInvisible = true
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        solarObjectsAdapter = null
    }
}

fun SolarObjectJson.toSolarObject() =
    SolarObject(
        id,
        name,
        imageUrl = imageUrl,
        video = videoId,
    )
