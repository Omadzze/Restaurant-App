package com.codingtroops.restaurantsapp.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingtroops.restaurantsapp.data.remote.RestaurantApiService
import com.codingtroops.restaurantsapp.domain.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val restInterface: RestaurantApiService,
    private val stateHandle: SavedStateHandle
    ): ViewModel() {

    val state = mutableStateOf<Restaurant?>(null)

    // initializing Retrofit
    init {
        getDataRestaurants()
    }

    private fun getDataRestaurants() {
        val id = stateHandle.get<Int>("restaurant_id") ?: 0
        viewModelScope.launch {
            val restaurant = getRemoteRestaurant(id)
            state.value = restaurant
        }
    }


    // get details in bg
    private suspend fun getRemoteRestaurant(id: Int): Restaurant {
        return withContext(Dispatchers.IO) {
            //gets id of the restaurant
            val response = restInterface.getRestaurant(id)
            return@withContext response.values.first().let {
                Restaurant(
                    id = it.id,
                    title = it.title,
                    description = it.descripton
                )
            }
        }
    }
}