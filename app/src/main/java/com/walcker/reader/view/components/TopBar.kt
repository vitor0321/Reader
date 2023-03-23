package com.walcker.reader.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.walcker.reader.R
import com.walcker.reader.view.steps.login.LoginScreen

@Composable
internal fun TopBar(
    title: String = "title",
    icon: ImageVector? = null,
    isHomeScreen: Boolean = true,
    onBackArrowClicked: () -> Unit = {}
) {

    val navigator = LocalNavigator.currentOrThrow

    Column(modifier = Modifier.padding(end = 2.dp)) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier.fillMaxWidth(0.02f))
                    if (isHomeScreen) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_img),
                            contentDescription = "icon",
                            colorFilter = ColorFilter.tint(Color.Green.copy(alpha = 0.7f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .scale(0.9f)
                        )
                    }

                    icon?.let {
                        Icon(imageVector = icon,
                            contentDescription = "arrow back",
                            tint = Color.Red.copy(alpha = 0.7f),
                            modifier = Modifier.clickable {
                                onBackArrowClicked()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                    Text(
                        text = title,
                        color = Color.Red.copy(alpha = 0.7f),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    FirebaseAuth.getInstance().signOut().run {
                        navigator.push(LoginScreen)
                    }
                }) {
                    if (isHomeScreen) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Logout",
                            tint = Color.Green.copy(alpha = 0.7f)
                        )
                    }
                }
            },
            backgroundColor = Color.Transparent,
        )
        Divider(color = Color.LightGray)
    }
}
