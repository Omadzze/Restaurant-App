package com.codingtroops.restaurantsapp.data.local

import androidx.room.*

@Dao
interface RestaurantsDao {
    // all functions suspending because it should not run in mainThread
    // getting all restaurants
    @Query("SELECT * FROM restaurants")
    suspend fun getAll(): List<LocalRestaurant>

    // insert restaurants and if we have error that rest. already insterted
    // then replace it with new value
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurant: List<LocalRestaurant>)

    // matching value of isFavorite in Restaurant.class with the PartialRestaurant.class
    @Update(entity = LocalRestaurant::class)
    suspend fun update(partialRestaurant: PartialLocalRestaurant)

    // we updates only favorite
    @Update(entity = LocalRestaurant::class)
    suspend fun updateAll(partialRestaurant: List<PartialLocalRestaurant>)

    // where favorite equals to true
    @Query("SELECT * FROM restaurants WHERE is_favorite = 1")
    suspend fun getAllFavorited(): List<LocalRestaurant>
}