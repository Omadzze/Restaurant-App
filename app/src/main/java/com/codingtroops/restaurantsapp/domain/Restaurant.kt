package com.codingtroops.restaurantsapp.domain

data class Restaurant(val id: Int, val title: String,
                      val description: String, val isFavorite: Boolean = false)