package pl.szkoleniaandroid.solarsystem.sandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.szkoleniaandroid.solarsystem.R
import pl.szkoleniaandroid.solarsystem.databinding.ActivitySandboxBinding

class SandboxActivity : AppCompatActivity() {

    private val viewModel: SandboxViewModel by viewModel()
    private lateinit var binding: ActivitySandboxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySandboxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    private fun setUp() {
        viewModel.uiState.observe(this) {
            render(it)
        }
        binding.actionButton.setOnClickListener { viewModel.doAction() }
    }

    private fun render(state: SandboxViewModel.UiState) {
        when (state) {
            SandboxViewModel.UiState.Loading -> {
                with(binding) {
                    actionButton.isEnabled = false
                    progressBar.isInvisible = false
                    console.setBackgroundColor(resources.getColor(R.color.backgroundColor))
                    console.text = ""
                }
            }

            is SandboxViewModel.UiState.Error -> {
                with(binding) {
                    actionButton.isEnabled = true
                    progressBar.isInvisible = true
                    console.setBackgroundColor(resources.getColor(R.color.consoleRed))
                    console.text = state.errorMessage
                }
            }

            is SandboxViewModel.UiState.Success -> {
                with(binding) {
                    actionButton.isEnabled = true
                    progressBar.isInvisible = true
                    console.setBackgroundColor(resources.getColor(R.color.consoleGreen))
                    console.text = state.message
                }
            }
        }
    }
}

