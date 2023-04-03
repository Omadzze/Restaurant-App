package com.codingtroops.restaurantsapp.domain

import com.codingtroops.restaurantsapp.data.RestaurantRepository
import javax.inject.Inject

class GetInitialRestaurantUseCase @Inject constructor(
    private val repository: RestaurantRepository,
    private val getSortedRestaurantUseCase: GetSortedRestaurantUseCase) {
    suspend operator fun invoke(): List<Restaurant> {
        repository.loadRestaurants()
        return getSortedRestaurantUseCase()
    }
}