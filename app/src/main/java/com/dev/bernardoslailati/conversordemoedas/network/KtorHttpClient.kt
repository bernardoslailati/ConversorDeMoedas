package com.dev.bernardoslailati.conversordemoedas.network

import android.util.Log
import com.dev.bernardoslailati.conversordemoedas.network.model.CurrencyTypesResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking

object KtorHttpClient {

    private val client = HttpClient(Android) {
        install(Logging)
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        runBlocking {
            val currencyResult: CurrencyTypesResult =
                client.get("http://10.0.2.2:8080/currency_types").body()
            currencyResult.values.forEach {
                Log.d("CurrencyResult", it.toString())
            }
        }
    }

}