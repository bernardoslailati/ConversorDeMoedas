package com.dev.bernardoslailati.conversordemoedas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.dev.bernardoslailati.conversordemoedas.databinding.ActivityMainBinding
import com.dev.bernardoslailati.conversordemoedas.network.model.CurrencyType
import com.dev.bernardoslailati.conversordemoedas.ui.CurrencyTypesAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<CurrencyExchangeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top, systemBars.right,
                systemBars.bottom
            )
            insets
        }

        viewModel.requireCurrencyTypes()

        lifecycleScope.apply {
            launch {
                viewModel.currencyTypes.collect { result ->
                    result.onSuccess { currencyTypes ->
                        binding.configureCurrencyTypesSpinners(currencyTypes = currencyTypes)
                    }.onFailure {
                        Toast.makeText(
                            this@MainActivity, it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            launch {
                viewModel.exchangeRate.collect { result ->
                    result.onSuccess {
                        Log.d("MainActivity", it.toString())
                    }.onFailure {
                        Log.d("MainActivity", it.message.toString())
                    }
                }
            }
        }
    }

    private fun ActivityMainBinding.configureCurrencyTypesSpinners(currencyTypes: List<CurrencyType>) {
        spnFromExchange.apply {
            adapter = CurrencyTypesAdapter(currencyTypes)
            onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val from = currencyTypes[position]
                    val to = currencyTypes[spnToExchange.selectedItemPosition]

                    viewModel.requireExchangeRate(
                        from = from.acronym,
                        to = to.acronym
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }


        spnToExchange.apply {
            adapter = CurrencyTypesAdapter(currencyTypes)
            onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val from = currencyTypes[spnFromExchange.selectedItemPosition]
                    val to = currencyTypes[position]

                    viewModel.requireExchangeRate(
                        from = from.acronym,
                        to = to.acronym
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    currencyTypes.firstOrNull()?.let { firstCurrencyType ->
                        viewModel.requireExchangeRate(
                            from = firstCurrencyType.acronym,
                            to = firstCurrencyType.acronym
                        )
                    }
                }
            }
        }
    }

}