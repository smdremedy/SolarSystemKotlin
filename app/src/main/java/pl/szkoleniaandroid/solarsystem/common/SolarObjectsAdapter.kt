package pl.szkoleniaandroid.solarsystem.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.szkoleniaandroid.solarsystem.domain.SolarObject
import pl.szkoleniaandroid.solarsystem.databinding.SolarObjectItemBinding

class SolarObjectsAdapter(
    private val objectClicked: (SolarObject) -> Unit
) : RecyclerView.Adapter<SolarObjectsViewHolder>() {

    private val objects = mutableListOf<SolarObject>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarObjectsViewHolder {
        val binding = SolarObjectItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SolarObjectsViewHolder(binding, objectClicked)
    }

    override fun getItemCount() = objects.size

    override fun onBindViewHolder(holder: SolarObjectsViewHolder, position: Int) {
        holder.setSolarObject(objects[position])
    }

    fun setObjects(objectsToSet: List<SolarObject>) {
        objects.clear()
        objects.addAll(objectsToSet)
        notifyDataSetChanged()
    }
}
