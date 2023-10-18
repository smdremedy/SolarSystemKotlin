package pl.szkoleniaandroid.solarsystem.screens.moons

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsFragment

class MoonsFragment : SolarObjectsFragment() {
    private val args by navArgs<MoonsFragmentArgs>()
    override val viewModel: MoonsViewModel by viewModel {
        parametersOf(args.orbitId)
    }

    companion object {
        fun newInstance(orbitId: String) = MoonsFragment().apply {
            arguments = MoonsFragmentArgs.Builder(orbitId).build().toBundle()
        }
    }
}

