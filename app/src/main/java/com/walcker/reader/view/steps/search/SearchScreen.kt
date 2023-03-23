package com.walcker.reader.view.steps.search

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
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.walcker.reader.resource.LocalStrings
import com.walcker.reader.view.components.TopBar
import com.walcker.reader.view.steps.detatils.DetailsScreen
import com.walcker.reader.view.steps.home.HomeScreen
import com.walcker.reader.view.steps.search.areas.SearchFromTopArea
import com.walcker.reader.view.steps.search.areas.SearchListOfBooks
import com.walcker.reader.view.utils.Step

internal object SearchScreen : Step("search_screen") {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<SearchViewModel>()

        SearchContent(viewModel = viewModel)
    }
}

@Composable
private fun SearchContent(viewModel: SearchViewModel) {
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

                SearchListOfBooks(viewModel = viewModel) { bookId ->
                    navigator.push(DetailsScreen(bookId = bookId))
                }
            }
        }
    }
}
