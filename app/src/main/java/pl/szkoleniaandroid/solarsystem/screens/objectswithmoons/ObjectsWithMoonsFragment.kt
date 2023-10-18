package pl.szkoleniaandroid.solarsystem.screens.objectswithmoons

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.databinding.MoonsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.szkoleniaandroid.solarsystem.domain.SolarObject
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsViewModel
import pl.szkoleniaandroid.solarsystem.screens.moons.MoonsFragment


class ObjectsWithMoonsFragment : Fragment() {

    private val viewModel: ObjectsWithMoonsViewModel by viewModel()

    private lateinit var adapter: PlanetsWithMoonPagerAdapter
    private var callback: Callback? = null

    private lateinit var binding: MoonsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoonsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlanetsWithMoonPagerAdapter(requireActivity())
        binding.viewpager.adapter = adapter
        callback?.showTabs(binding.viewpager, adapter::mapPositionToTitle)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it is SolarObjectsViewModel.UiState.Success) {
                        adapter.setObjects(it.objects)
                    }
                }
            }
        }

        viewModel.fetchObjects()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback?.hideTabs()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    interface Callback {
        fun showTabs(viewPager: ViewPager2, mapper: (Int) -> String)
        fun hideTabs()
    }
}

class PlanetsWithMoonPagerAdapter(
    activity: FragmentActivity,

    ) : FragmentStateAdapter(activity) {

    private val planetsWithMoon: MutableList<SolarObject> = mutableListOf()

    //override fun getPageTitle(position: Int) =

    fun setObjects(objects: List<SolarObject>) {
        planetsWithMoon.addAll(objects)
        notifyDataSetChanged()
    }

    override fun getItemCount() = planetsWithMoon.size

    fun mapPositionToTitle(position: Int) = planetsWithMoon[position].name

    override fun createFragment(position: Int) =
        MoonsFragment.newInstance(planetsWithMoon[position].id)

}
