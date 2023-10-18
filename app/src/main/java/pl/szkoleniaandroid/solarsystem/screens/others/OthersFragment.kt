package pl.szkoleniaandroid.solarsystem.screens.others

import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsFragment

class OthersFragment : SolarObjectsFragment() {
    override val viewModel: OthersViewModel by viewModel()
}
