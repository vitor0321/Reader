package com.walcker.reader.presentation.screens.bookUpdate.areas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.walcker.core.model.BookUI

@Composable
fun CardBookUpdate(
    bookUI: MutableState<BookUI>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        elevation = 4.dp
    ) {
        Row {

            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {

                    },
                elevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = bookUI.value.photoUrl?.replace("http", "https")),
                        contentDescription = null,
                        modifier = Modifier
                            .height(100.dp)
                            .width(120.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(topStart = 120.dp, topEnd = 20.dp))
                    )

                    Column {
                        Text(
                            text = bookUI.value.title ?: "",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp)
                                .width(120.dp),
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = bookUI.value.authors ?: "",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                        )

                        Text(
                            text = bookUI.value.date ?: "",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                        )
                    }
                }
            }
        }
    }
}