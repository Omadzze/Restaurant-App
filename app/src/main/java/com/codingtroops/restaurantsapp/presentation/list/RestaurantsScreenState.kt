package com.codingtroops.restaurantsapp.presentation.list

import com.codingtroops.restaurantsapp.domain.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
