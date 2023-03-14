package com.walcker.reader.presentation.screens.detatils.areas

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.walcker.core.model.BookUI
import com.walcker.reader.framework.network.firebaseModel.toBookUIFirebase
import com.walcker.reader.presentation.components.ErrorReader
import com.walcker.reader.presentation.components.Loading
import com.walcker.reader.presentation.components.RoundedButton
import com.walcker.reader.presentation.screens.home.HomeScreen
import com.walcker.reader.presentation.screens.search.SearchScreen

@Composable
fun ShowBooksDetailsArea(
    bookUI: BookUI
) {

    val navigator = LocalNavigator.currentOrThrow
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier.padding(34.dp),
            shape = CircleShape,
            elevation = 4.dp
        ) {
            Image(
                painter = rememberImagePainter(data = bookUI.photoUrl),
                contentDescription = "image book",
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .padding(1.dp)
            )
        }

        Text(
            text = bookUI.title ?: "...",
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 19
        )
        Text(text = "Authors: " + if (bookUI.authors != null) bookUI.authors else "...")
        Text(text = "Page Count: " + bookUI.pageCount)
        Text(
            text = "Categories: " + if (bookUI.category != null) bookUI.category else "...",
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 19
        )
        Text(
            text = "Published: " + if (bookUI.date != null) bookUI.date else "...",
            style = MaterialTheme.typography.subtitle1
        )

        Spacer(modifier = Modifier.height(5.dp))

        val cleanNotes = bookUI.description?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() }
        val localDims = LocalContext.current.resources.displayMetrics
        Surface(
            modifier = Modifier
                .height(localDims.heightPixels.dp.times(0.09f))
                .padding(4.dp),
            shape = RectangleShape,
            border = BorderStroke(1.dp, Color.DarkGray)
        ) {
            LazyColumn(
                modifier = Modifier.padding(3.dp)
            ) {
                item {
                    Text(text = if (cleanNotes != null) "$cleanNotes" else "isn't description about this book")
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            RoundedButton(label = "Save") {
                loading.value = true
                // save this book in Firestore
                val book = BookUI(
                    title = bookUI.title,
                    authors = bookUI.authors,
                    description = bookUI.description,
                    date = bookUI.date,
                    category = bookUI.category,
                    photoUrl = bookUI.photoUrl,
                    rating = bookUI.rating,
                    notes = bookUI.notes,
                    pageCount = bookUI.pageCount,
                    startedReading = bookUI.startedReading,
                    finishedReading = bookUI.finishedReading,
                    userId = FirebaseAuth.getInstance().currentUser?.uid,
                    googleBookId = bookUI.id,
                )
                saveToFirebase(
                    bookUI = book,
                    navigator = navigator,
                    error = error
                )
            }

            Spacer(modifier = Modifier.width(25.dp))

            RoundedButton(label = "Cancel") {
                loading.value = true
                navigator.push(SearchScreen)
            }
        }

        if (loading.value) Loading()
        if (error.value) ErrorReader(message = "Humm something is wrong .... try again")
    }
}

private fun saveToFirebase(bookUI: BookUI, navigator: Navigator, error: MutableState<Boolean>) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (bookUI.toString().isNotEmpty()) {
        dbCollection.add(bookUI.toBookUIFirebase())
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navigator.push(HomeScreen)
                        }
                    }.addOnFailureListener {
                        error.value = true
                        Log.d("Firebase", "saveToFirebase: $it ")
                    }
            }.addOnFailureListener {
                error.value = true
                Log.d("Firebase", "saveToFirebase: $it ")
            }
    }
}
