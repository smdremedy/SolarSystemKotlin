package pl.szkoleniaandroid.solarsystem.screens.planets

import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsFragment

class PlanetsFragment : SolarObjectsFragment() {
    override val viewModel: PlanetsViewModel by viewModel()
}
