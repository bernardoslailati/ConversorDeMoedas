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

    private const val BASE_URL = "http://10.0.2.2:8080"

    private val client = HttpClient(Android) {
        install(Logging)
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        val result: CurrencyTypesResult = runBlocking {
            client.get("$BASE_URL/currency_types").body()
        }

        Log.d("KtorHttpClient", result.toString())
    }

}