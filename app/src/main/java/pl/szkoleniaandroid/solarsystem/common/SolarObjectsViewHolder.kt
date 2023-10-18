package pl.szkoleniaandroid.solarsystem.common

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import org.koin.java.KoinJavaComponent.inject
import pl.szkoleniaandroid.solarsystem.R
import pl.szkoleniaandroid.solarsystem.databinding.SolarObjectItemBinding
import pl.szkoleniaandroid.solarsystem.domain.SolarObject

class SolarObjectsViewHolder(
    private val binding: SolarObjectItemBinding,
    private val objectClicked: (SolarObject) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private val imageLoader: ImageLoader by inject(ImageLoader::class.java)
    private lateinit var solarObject: SolarObject

    init {
        binding.root.setOnClickListener { objectClicked(solarObject) }
    }

    fun setSolarObject(solarObject: SolarObject) {
        this.solarObject = solarObject
        binding.itemTextView.text = solarObject.name
        binding.itemImageView.load(solarObject.imageUrl, imageLoader) {
            placeholder(R.drawable.planet_placeholder)
        }
    }
}
