package com.walcker.reader.presentation.steps.detatils

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
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.walcker.domain.model.BookUI
import com.walcker.domain.usecase.base.ResultStatus
import com.walcker.reader.presentation.common.Step
import com.walcker.reader.presentation.components.ErrorReader
import com.walcker.reader.presentation.components.Loading
import com.walcker.reader.presentation.components.TopBar
import com.walcker.reader.presentation.steps.detatils.areas.ShowBooksDetailsArea
import com.walcker.reader.presentation.steps.search.SearchScreen
import com.walcker.reader.resource.LocalStrings

class DetailsScreen(val bookId: String) : Step("detail_screen") {

    @Composable
    override fun Content() {
        DetailsScreenContent(bookId = bookId)
    }
}

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun DetailsScreenContent(
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val navigator = LocalNavigator.currentOrThrow
    val strings = LocalStrings.current
    val bookInfo = produceState<ResultStatus<BookUI>>(initialValue = ResultStatus.Loading) {
        value = viewModel.getBookIdInfo(bookId)
    }.value

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
                when (bookInfo) {
                    is ResultStatus.Loading -> {
                        Loading()
                    }
                    is ResultStatus.Error -> {
                        ErrorReader(message = "Hummm .... something is wrong, try again")
                    }
                    is ResultStatus.Success -> {
                        ShowBooksDetailsArea(bookUI = bookInfo.data)
                    }
                }
            }
        }
    }
}