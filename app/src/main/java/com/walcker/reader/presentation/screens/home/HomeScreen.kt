package com.walcker.reader.presentation.screens.home

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.walcker.core.model.BookUI
import com.walcker.reader.presentation.components.FloatButton
import com.walcker.reader.presentation.components.TitleSection
import com.walcker.reader.presentation.components.TopBar
import com.walcker.reader.presentation.screens.home.areas.BookListArea
import com.walcker.reader.presentation.screens.home.areas.ReadingRightNowArea
import com.walcker.reader.presentation.screens.home.areas.TopOfHomeArea
import com.walcker.reader.presentation.screens.search.SearchScreen
import com.walcker.reader.presentation.screens.search.SearchViewModel

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        HomeContent()
    }
}

@Composable
fun HomeContent() {
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        topBar = { TopBar(title = "A.Reader") },
        backgroundColor = MaterialTheme.colors.primary,
        floatingActionButton = {
            FloatButton {
                navigator.push(SearchScreen)
            }
        }
    ) {
        it.calculateTopPadding()
        Surface(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            color = MaterialTheme.colors.primary
        ) {

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top) {

                TopOfHomeArea()

                ReadingRightNowArea(books = listOf()){

                }

                TitleSection(label = "Reading List")

                BookListArea(listOfBooks = listOf()){

                }
            }
        }
    }
}
