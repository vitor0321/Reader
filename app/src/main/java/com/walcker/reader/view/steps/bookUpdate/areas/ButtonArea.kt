package com.walcker.reader.view.steps.bookUpdate.areas

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.firestore.FirebaseFirestore
import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.view.components.RoundedButton
import com.walcker.reader.view.components.ShowAlertDialog
import com.walcker.reader.view.steps.home.HomeScreen
import com.walcker.reader.resource.LocalStrings

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun ButtonArea(
    bookUI: MutableState<BookUI>,
    notesText: MutableState<String>,
    ratingState: MutableState<Int>,
    isStartReading: MutableState<Boolean>,
    isFinishedReading: MutableState<Boolean>,
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>
) {
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val strings = LocalStrings.current

    Column {
        val changedNotes = bookUI.value.description != notesText.value
        val changedRating = bookUI.value.rating != ratingState.value
        val isStartedTimestamp = if (isStartReading.value) true else bookUI.value.startedReading
        val isFinishedTimeStamp = if (isFinishedReading.value) true else bookUI.value.finishedReading

        val bookUpdate = changedNotes || changedRating || isFinishedReading.value || isStartReading.value

        val bookToUpdate = hashMapOf(
            "finished_reading" to isFinishedTimeStamp,
            "started_reading" to isStartedTimestamp,
            "rating" to ratingState.value,
            "notes" to notesText.value
        ).toMap()

        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            TextStatusStart(isStartReading, bookUI)

            Spacer(modifier = Modifier.height(4.dp))

            TextStatusFinished(isFinishedReading, bookUI)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {

            RoundedButton(label = "Update") {
                loading.value = true

                if (bookUpdate) {
                    FirebaseFirestore
                        .getInstance()
                        .collection("books")
                        .document(bookUI.value.id!!)
                        .update(bookToUpdate)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Success update", Toast.LENGTH_SHORT).show()
                            navigator.push(HomeScreen)
                        }
                        .addOnFailureListener {
                            error.value = true
                        }
                }
            }

            Spacer(modifier = Modifier.width(25.dp))

            val openDialog = remember { mutableStateOf(false) }
            if (openDialog.value) {
                ShowAlertDialog(
                    message = strings.update.messageDialog,
                    openDialog = openDialog
                ) {
                    loading.value = true
                    FirebaseFirestore.getInstance()
                        .collection("books")
                        .document(bookUI.value.id!!)
                        .delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                openDialog.value = false
                                navigator.push(HomeScreen)
                                loading.value = false
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Humm.. something is wrong", Toast.LENGTH_SHORT).show()
                        }
                }
            }

            RoundedButton(label = "Detele") {
                openDialog.value = true
            }
        }
    }
}

@Composable
private fun TextStatusStart(isStartReading: MutableState<Boolean>, bookUI: MutableState<BookUI>) {
    TextButton(
        onClick = {
            isStartReading.value = !isStartReading.value
            bookUI.value.startedReading = if (isStartReading.value) true else null
        },
        enabled = bookUI.value.startedReading == null
    ) {
        if (bookUI.value.startedReading == null) {
            if (!isStartReading.value) {
                Text(
                    text = "Start Reading",
                    color = MaterialTheme.colors.secondary
                )
            }
        } else {
            Text(
                text = "Started on: ${bookUI.value.startedReading}",
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun TextStatusFinished(
    isFinishedReading: MutableState<Boolean>,
    bookUI: MutableState<BookUI>
) {
    TextButton(
        onClick = { isFinishedReading.value = !isFinishedReading.value },
        enabled = bookUI.value.finishedReading == null
    ) {
        if (bookUI.value.finishedReading == null) {
            if (!isFinishedReading.value) {
                Text(
                    text = "Mark as Read",
                    color = MaterialTheme.colors.secondary
                )
            } else {
                Text(
                    text = "Finished Reading",
                    modifier = Modifier.alpha(0.6f),
                    color = Color.Red
                )
            }
        } else {
            Text(
                text = "Finished on: ${bookUI.value.finishedReading}",
                color = MaterialTheme.colors.secondary
            )
        }
    }
}