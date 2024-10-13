package com.dev.bernardoslailati.conversordemoedas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.bernardoslailati.conversordemoedas.network.KtorHttpClient
import com.dev.bernardoslailati.conversordemoedas.network.model.CurrencyType
import com.dev.bernardoslailati.conversordemoedas.network.model.ExchangeRateResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyExchangeViewModel : ViewModel() {

    private val _currencyTypes =
        MutableStateFlow<Result<List<CurrencyType>>>(Result.success(emptyList()))
    val currencyTypes: StateFlow<Result<List<CurrencyType>>> = _currencyTypes.asStateFlow()

    private val _exchangeRate =
        MutableStateFlow<Result<ExchangeRateResult?>>(Result.success(null))
    val exchangeRate: StateFlow<Result<ExchangeRateResult?>> = _exchangeRate.asStateFlow()

    init {
        viewModelScope.launch {
            _currencyTypes.value = KtorHttpClient.getCurrencyTypes().mapCatching { result ->
                result.values
            }

            _exchangeRate.value = KtorHttpClient.getExchangeRate(from = "BRL", to = "USD")
        }
    }

}