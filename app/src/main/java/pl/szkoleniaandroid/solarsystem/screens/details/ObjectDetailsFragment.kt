package pl.szkoleniaandroid.solarsystem.screens.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.load
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent
import pl.szkoleniaandroid.solarsystem.MainDirections
import pl.szkoleniaandroid.solarsystem.R
import pl.szkoleniaandroid.solarsystem.domain.SolarObject
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsAdapter
import pl.szkoleniaandroid.solarsystem.databinding.ObjectDetailsFragmentBinding

class ObjectDetailsFragment : Fragment() {

    private val imageLoader: ImageLoader by KoinJavaComponent.inject(ImageLoader::class.java)
    private val args by navArgs<ObjectDetailsFragmentArgs>()
    private lateinit var binding: ObjectDetailsFragmentBinding

    private val viewModel: ObjectDetailsViewModel by viewModel {
        parametersOf(args.solarObject)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ObjectDetailsFragmentBinding.inflate(layoutInflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        loadDetails()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                render(it)
            }

        }
        viewModel.loadData()

    }

    private fun render(uiState: ObjectDetailsViewModel.UiState) {
        when (uiState) {
            ObjectDetailsViewModel.UiState.Loading -> {

            }

            is ObjectDetailsViewModel.UiState.Success -> {
                binding.objectDetailsContent.detailsTextView.text =
                    HtmlCompat.fromHtml(uiState.detailsJson.description, FROM_HTML_MODE_LEGACY)
                showMoons(uiState.detailsJson.moons)
            }

            is ObjectDetailsViewModel.UiState.Error -> {
                Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupToolbar() {
        binding.toolbar.title = args.solarObject.name
        binding.toolbarLayout.title = args.solarObject.name
        //hack if we want to use toolbar without setting as support
        binding.toolbar.navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            //What to do on back clicked
            findNavController().navigateUp()
        }
    }

    private fun loadDetails() {
        if (!args.solarObject.video.isNullOrEmpty()) {
            binding.fab.setOnClickListener { watchYoutubeVideo(args.solarObject.video!!) }
        } else {
            val p = binding.fab.layoutParams as CoordinatorLayout.LayoutParams
            p.anchorId = View.NO_ID
            binding.fab.layoutParams = p
            binding.fab.isVisible = false
        }

        binding.detailImageView.load(args.solarObject.imageUrl, imageLoader) {
            placeholder(R.drawable.planet_placeholder)
        }
    }


    private fun showMoons(moons: List<SolarObject>) {
        val hasMoons = moons.isNotEmpty()
        binding.objectDetailsContent.moonsLabel.isVisible = hasMoons
        binding.objectDetailsContent.moonsRecycleView.isVisible = hasMoons
        if (hasMoons) {
            binding.objectDetailsContent.moonsRecycleView.apply {
                adapter = SolarObjectsAdapter {
                    showSolarObjectDetails(it)
                }.apply {
                    setObjects(moons)
                }
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                //https://code.google.com/p/android/issues/detail?id=177613
                isNestedScrollingEnabled = false
            }
        }
    }

    private fun showSolarObjectDetails(clickedObject: SolarObject) {
        findNavController().navigate(
            MainDirections.showObjectDetails(
                clickedObject
            )
        )
    }

    private fun watchYoutubeVideo(id: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            //no YouTube app - show in browser
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=$id")
            )
            startActivity(intent)
        }
    }
}
