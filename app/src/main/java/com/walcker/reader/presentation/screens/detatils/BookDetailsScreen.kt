package com.walcker.reader.presentation.screens.detatils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.walcker.reader.presentation.components.TopBar

object BookDetailsScreen: Screen {

    @Composable
    override fun Content() {
        Scaffold(
            topBar = {

            },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            it.calculateTopPadding()
        }
    }
}