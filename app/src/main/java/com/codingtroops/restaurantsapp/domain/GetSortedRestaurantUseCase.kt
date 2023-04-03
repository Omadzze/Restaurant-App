package com.codingtroops.restaurantsapp.domain

import com.codingtroops.restaurantsapp.data.RestaurantRepository
import javax.inject.Inject

class GetSortedRestaurantUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {

    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants().sortedBy { it.title }
    }
}