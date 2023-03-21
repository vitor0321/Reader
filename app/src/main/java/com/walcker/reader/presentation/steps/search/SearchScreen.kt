package com.walcker.reader.presentation.steps.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.walcker.reader.presentation.common.Step
import com.walcker.reader.presentation.components.TopBar
import com.walcker.reader.presentation.steps.detatils.DetailsScreen
import com.walcker.reader.presentation.steps.home.HomeScreen
import com.walcker.reader.presentation.steps.search.areas.SearchFromTopArea
import com.walcker.reader.presentation.steps.search.areas.SearchListOfBooks
import com.walcker.reader.resource.LocalStrings

object SearchScreen : Step("search_screen") {

    @Composable
    override fun Content() {
        SearchContent()
    }
}

@Composable
fun SearchContent(viewModel: SearchViewModel = hiltViewModel()) {
    val navigator = LocalNavigator.currentOrThrow
    val strings = LocalStrings.current

    Scaffold(
        topBar = {
            TopBar(
                title = strings.search.title,
                icon = Icons.Default.ArrowBack,
                isHomeScreen = false
            ) {
                navigator.push(HomeScreen)
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        it.calculateTopPadding()

        Surface {
            Column {
                SearchFromTopArea(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) { search ->
                    viewModel.getAllBooks(search)
                }

                Spacer(modifier = Modifier.height(10.dp))

                SearchListOfBooks { bookId ->
                    navigator.push(DetailsScreen(bookId = bookId))
                }
            }
        }
    }
}
