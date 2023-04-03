package com.codingtroops.restaurantsapp.domain

import com.codingtroops.restaurantsapp.data.RestaurantRepository
import javax.inject.Inject

class ToggleRestaurantUseCase @Inject constructor(
    private val repository: RestaurantRepository,
    private val getSortedRestaurantUseCase: GetSortedRestaurantUseCase
) {

    suspend operator fun invoke(id: Int, oldValue: Boolean): List<Restaurant> {
        val newFav = oldValue.not()
        repository.toggleFavoriteRestaurant(id, newFav)
        return getSortedRestaurantUseCase()
    }
}