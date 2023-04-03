package com.codingtroops.restaurantsapp.data

import com.codingtroops.restaurantsapp.domain.Restaurant
import com.codingtroops.restaurantsapp.RestaurantsApplication
import com.codingtroops.restaurantsapp.data.local.LocalRestaurant
import com.codingtroops.restaurantsapp.data.local.PartialLocalRestaurant
import com.codingtroops.restaurantsapp.data.local.RestaurantsDao
import com.codingtroops.restaurantsapp.data.local.RestaurantsDb
import com.codingtroops.restaurantsapp.data.remote.RestaurantApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepository @Inject constructor(
    private val restInterface: RestaurantApiService,
    private val restaurantsDao: RestaurantsDao
) {
    suspend fun toggleFavoriteRestaurant(id: Int, value: Boolean) =
        withContext(Dispatchers.IO) {
            // calling our Dao method and updating old value to new value
            restaurantsDao.update(
                PartialLocalRestaurant(
                    id = id,
                    isFavorite = value
                )
            )
        }

    suspend fun getRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            return@withContext restaurantsDao.getAll().map {
                Restaurant (it.id, it.title, it.description, it.isFavorite)
            }
        }
    }

    suspend fun loadRestaurants() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    // if we have internet problem then show list from database
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty())
                            throw Exception(
                                "Something went wrong. " + "We have no data."
                            )
                    }
                    else -> throw e
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        // getting all favorite data
        val favoriteRestaurants = restaurantsDao.getAllFavorited()
        restaurantsDao.addAll(remoteRestaurants.map {
            LocalRestaurant(it.id, it.title, it.descripton, false)
        })
        // then overriding values with the exisitng values that we had
        restaurantsDao.updateAll(favoriteRestaurants.map {
            PartialLocalRestaurant(id = it.id, isFavorite = true)
        })
    }
}