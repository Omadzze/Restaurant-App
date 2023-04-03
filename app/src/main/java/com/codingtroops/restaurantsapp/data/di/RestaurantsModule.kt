package com.codingtroops.restaurantsapp.data.di

import android.content.Context
import androidx.room.Room
import com.codingtroops.restaurantsapp.data.local.RestaurantsDao
import com.codingtroops.restaurantsapp.data.local.RestaurantsDb
import com.codingtroops.restaurantsapp.data.remote.RestaurantApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
// we can use this dependency anywhere in the project
@InstallIn(SingletonComponent::class)
object RestaurantsModule {
    @Provides
    fun provideRoomDao(database: RestaurantsDb):
        RestaurantsDao {
            return database.dao
    }
    // creates only one instance
    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext appContext: Context
    ): RestaurantsDb {
        return Room.databaseBuilder(
            appContext,
            RestaurantsDb::class.java,
            // database name
            "restaurants_database"
            // delete table if it was changed
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restaurant-trainings-default-rtdb.firebaseio.com/")
            .build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): RestaurantApiService {
        return retrofit.create(RestaurantApiService::class.java)
    }
}