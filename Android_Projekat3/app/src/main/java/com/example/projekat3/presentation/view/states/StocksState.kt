package com.example.projekat3.presentation.view.states

import com.example.projekat3.data.models.stocks.Stock

sealed class StocksState {
    object DataFetched: StocksState()
    data class Success(val stocks: List<Stock>): StocksState()
    data class Error(val message: String): StocksState()
}