package com.walcker.reader.presentation.screens.home.areas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.walcker.reader.presentation.components.TitleSection
import com.walcker.reader.presentation.screens.stats.StatsScreen

@Composable
fun TopOfHomeArea() {
    val navigator = LocalNavigator.currentOrThrow
    val email = FirebaseAuth.getInstance().currentUser?.email

    val currentUserName = if (!email.isNullOrEmpty()) email.split("@")[0] else "N/A"

    Row {

        TitleSection(label = "Your reading \n" + "activity right now...")

        Spacer(modifier = Modifier.fillMaxWidth(0.7f))
        Column {
            Icon(imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        navigator.push(StatsScreen)
                    }
                    .size(45.dp),
                tint = MaterialTheme.colors.onBackground)
            Text(
                text = currentUserName,
                modifier = Modifier.padding(2.dp),
                style = MaterialTheme.typography.overline,
                color = Color.Red,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Divider(color = MaterialTheme.colors.onBackground)
        }
    }
}