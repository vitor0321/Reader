package com.walcker.reader.presentation.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.walcker.reader.presentation.components.ReaderLogo
import com.walcker.reader.presentation.screens.home.HomeScreen
import com.walcker.reader.presentation.screens.login.LoginScreen
import kotlinx.coroutines.delay

object SplashScreen : Screen {
    @Composable
    override fun Content() {
        ContentSplashScreen()
    }
}

@Preview
@Composable
private fun ContentSplashScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {

        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = { OvershootInterpolator(8f).getInterpolation(it) }
            )
        )
        delay(1000L)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navigator.push(LoginScreen)
        } else {
            navigator.push(HomeScreen)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.primary
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .scale(scale.value)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Surface(
                modifier = Modifier
                    .padding(15.dp)
                    .size(330.dp),
                shape = CircleShape,
                color = Color.White,
                border = BorderStroke(width = 2.dp, color = Color.LightGray)
            ) {
                Column(
                    modifier = Modifier
                        .padding(1.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    ReaderLogo()

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "\"Read. Change. Yourself\"",
                        style = MaterialTheme.typography.h5,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}
