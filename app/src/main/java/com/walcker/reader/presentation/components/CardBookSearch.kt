package com.walcker.reader.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbDownOffAlt
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.walcker.core.model.BookUI

@Composable
fun CardBookSearch(
    bookUI: BookUI,
    onPressDetails: (String) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .clickable {
                onPressDetails(bookUI.id.toString())
            }
            .fillMaxWidth()
            .padding(6.dp),
        elevation = 4.dp
    ) {
        Row {
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
                    .height(110.dp)
                    .width(90.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = bookUI.photoUrl),
                    contentDescription = "book image",
                    modifier = Modifier
                        .height(110.dp)
                        .width(90.dp)
                        .padding(4.dp)
                )

                if (bookUI.rating!! >= 1) {
                    Surface(
                        color = MaterialTheme.colors.secondary,
                        shape = CircleShape
                    ) {
                        Icon(
                            modifier = Modifier.padding(4.dp),
                            imageVector = if (bookUI.rating!! >= 4) Icons.Default.ThumbUp else Icons.Default.ThumbDown,
                            contentDescription = if (bookUI.rating!! >= 4) "Thumbs up" else "Thumbs Down",
                            tint = if (bookUI.rating!! >= 4) Color.Green.copy(alpha = 0.7f) else Color.Red.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(2.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = bookUI.title.toString(),
                    fontSize = 18.sp,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    modifier = Modifier.padding(2.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colors.secondary,
                                fontSize = 16.sp,
                            )
                        ) {
                            append(text = "Author: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colors.secondary,
                                fontSize = 16.sp,
                            )
                        ) {
                            append(text = bookUI.authors.toString())
                        }
                    })

                Text(
                    modifier = Modifier.padding(2.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colors.secondary,
                                fontSize = 16.sp,
                            )
                        ) {
                            append(text = "Date: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colors.secondary,
                                fontSize = 16.sp,
                            )
                        ) {
                            append(text = bookUI.date.toString())
                        }
                    })

                Text(
                    text = if (bookUI.category != null) bookUI.category.toString() else "...",
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.secondary,
                    fontSize = 16.sp,
                )
            }
        }
    }
}