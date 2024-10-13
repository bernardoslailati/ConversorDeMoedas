package com.dev.bernardoslailati.conversordemoedas.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateResult(
    val from: String,
    val to: String,
    @SerialName("exchange_rate")
    val exchangeRate: Double
)