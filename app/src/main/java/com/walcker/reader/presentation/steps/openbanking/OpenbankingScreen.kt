package com.walcker.reader.presentation.steps.openbanking

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.walcker.reader.presentation.common.Step

class OpenbankingScreen(
    val dataFromMovieApp: String,
) : Step("openbancking") {

    @Composable
    override fun Content() {
        OpenbankingScreenContent(dataFromMovieApp = dataFromMovieApp)
    }
}

@Composable
fun OpenbankingScreenContent(
    dataFromMovieApp: String,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        if (dataFromMovieApp.isNotEmpty())
            Text(
                text = "This message is from MoviewApp: $dataFromMovieApp",
                color = Color.White
            )

        Button(
            onClick = {
//                val intent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("com.example.movieapp")
//                )
//               // intent.putExtra("deeplink", "I am Movie App")
//                val pendingIntent = TaskStackBuilder.create(context).run {
//                    addNextIntentWithParentStack(intent)
//                    getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                    )
//                }
//                pendingIntent.send()

                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_MAIN
                    intent.component = ComponentName
                        .unflattenFromString("com.example.movieapp/.MainActivity")
                    startActivity(context, intent, null)
                }catch (error: ActivityNotFoundException) {
                    Log.d("TAG", "DeeplinkScreen: $error")
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green.copy(alpha = 0.5f)),
        ) {
            Text(
                text = "Go to Movie App",
                color = Color.White
            )
        }
    }
}