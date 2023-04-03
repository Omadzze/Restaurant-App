package com.codingtroops.restaurantsapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.codingtroops.restaurantsapp.presentation.Description
import com.codingtroops.restaurantsapp.presentation.list.RestaurantsScreen
import com.codingtroops.restaurantsapp.presentation.list.RestaurantsScreenState
import com.codingtroops.restaurantsapp.ui.theme.RestaurantsAppTheme
import org.junit.Rule
import org.junit.Test

class RestaurantsScreenTest {
    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun initialState_isRendered() {
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = emptyList(),
                        isLoading = true
                    ),
                    onFavoriteClick = { _: Int, _: Boolean -> },
                    onItemClick = { })
            }
        }
        testRule.onNodeWithContentDescription(
            Description.RESTAURANTS_LOADING
        ).assertIsDisplayed()
    }

    @Test
    fun stateWithContent_isRendered() {
        val resturants = DummyContent.getDomainRestaurants()
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = resturants,
                        isLoading = false
                    ),
                    onFavoriteClick = { _: Int, _: Boolean -> },
                    onItemClick = { })
            }
        }
        // we are testing weather first restaurant and description visible or not
        testRule.onNodeWithText(resturants[0].title).assertIsDisplayed()
        testRule.onNodeWithText(resturants[0].description).assertIsDisplayed()
        // checking weather our progress bar is off
        testRule.onNodeWithContentDescription(Description.RESTAURANTS_LOADING).assertDoesNotExist()
        // checks weather we haven't error message
        testRule.onNodeWithContentDescription(Description.ERROR_MESSAGE).assertDoesNotExist()
    }

    // checks if click was on item first
    @Test
    fun stateWithContent_ClickOnItem_isRegistered() {
        val restaurants = DummyContent.getDomainRestaurants()
        val targetRestaurant = restaurants[0]
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = restaurants,
                        isLoading = false
                    ),
                    onFavoriteClick = { _, _ -> },
                    onItemClick = { id -> assert(id == targetRestaurant.id) })
            }
        }
        // click function
        testRule.onNodeWithText(targetRestaurant.title).performClick()
    }
}