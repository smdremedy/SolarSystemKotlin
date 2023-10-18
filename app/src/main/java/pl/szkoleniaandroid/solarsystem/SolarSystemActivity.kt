package pl.szkoleniaandroid.solarsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import pl.szkoleniaandroid.solarsystem.databinding.SolarSystemActivityBinding
import pl.szkoleniaandroid.solarsystem.screens.objectswithmoons.ObjectsWithMoonsFragment

class SolarSystemActivity : AppCompatActivity(), ObjectsWithMoonsFragment.Callback {

    private lateinit var binding: SolarSystemActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SolarSystemActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavController()
    }

    private fun setupNavController() {
        findNavController(R.id.nav_host_fragment).apply {
            addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.nav_details) {
                    updateNavigationUiVisibility(visible = false)
                } else {
                    updateNavigationUiVisibility(visible = true)
                }
            }
            binding.bottomNavigation.setupWithNavController(this)
        }
    }

    private fun updateNavigationUiVisibility(visible: Boolean) {
        binding.appBarLayout.isVisible = visible
        window.decorView.post {
            binding.bottomNavigation.isVisible = visible
        }
    }

    override fun showTabs(viewPager: ViewPager2, mapper: (Int) -> String) {
        binding.tabLayout.visibility = View.VISIBLE
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = mapper(position)
        }.attach()
    }

    override fun hideTabs() {
        binding.tabLayout.removeAllTabs()
        binding.tabLayout.setOnTabSelectedListener(null)
        binding.tabLayout.visibility = View.GONE
    }
}
