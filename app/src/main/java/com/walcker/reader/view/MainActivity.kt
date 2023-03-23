package com.walcker.reader.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import cafe.adriel.voyager.navigator.Navigator
import com.walcker.reader.resource.LocalStrings
import com.walcker.reader.resource.strings
import com.walcker.reader.resource.theme.ReaderTheme
import com.walcker.reader.view.steps.splash.SplashScreen
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

class MainActivity : ComponentActivity(), DIAware {

    override val di by closestDI()

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