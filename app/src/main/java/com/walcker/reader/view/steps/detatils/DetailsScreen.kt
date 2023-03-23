package com.walcker.reader.view.steps.detatils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.walcker.reader.resource.LocalStrings
import com.walcker.reader.view.components.ErrorReader
import com.walcker.reader.view.components.Loading
import com.walcker.reader.view.components.TopBar
import com.walcker.reader.view.steps.detatils.areas.ShowBooksDetailsArea
import com.walcker.reader.view.steps.search.SearchScreen
import com.walcker.reader.view.utils.Step
import com.walcker.reader.view.utils.uiState.UiStateBook

internal class DetailsScreen(val bookId: String) : Step("detail_screen") {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<DetailsViewModel>()
        viewModel.getBookIdInfo(bookId)

        DetailsScreenContent( viewModel = viewModel)
    }
}

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
private fun DetailsScreenContent(
    viewModel: DetailsViewModel
) {
    val state by viewModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    val strings = LocalStrings.current

    Scaffold(
        topBar = {
            TopBar(
                title = strings.details.title,
                icon = Icons.Default.ArrowBack,
                isHomeScreen = false
            ) {
                navigator.push(SearchScreen)
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        it.calculateTopPadding()

        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state) {
                    is UiStateBook.Loading -> {
                        Loading()
                    }
                    is UiStateBook.Error -> {
                        ErrorReader(message = "Hummm .... something is wrong, try again")
                    }
                    is UiStateBook.Success -> {
                        ShowBooksDetailsArea(bookUI = (state as UiStateBook.Success).booksList)
                    }
                }
            }
        }
    }
}