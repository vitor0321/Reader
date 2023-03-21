package com.walcker.reader.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import cafe.adriel.voyager.navigator.Navigator
import com.walcker.reader.presentation.steps.splash.SplashScreen
import com.walcker.reader.presentation.ui.theme.ReaderTheme
import com.walcker.reader.resource.LocalStrings
import com.walcker.reader.resource.StringsEn
import com.walcker.reader.resource.strings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val lyricist = rememberStrings(translations = strings)
            ProvideStrings(lyricist = lyricist, provider = LocalStrings) {
                BaseApp { Navigator(screen = SplashScreen) }
            }
        }
    }
}

@Composable
fun BaseApp(content: @Composable () -> Unit) {
    ReaderTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BaseApp { }
}