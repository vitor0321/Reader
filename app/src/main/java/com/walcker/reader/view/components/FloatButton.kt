package com.walcker.reader.view.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
internal fun FloatButton(
    icon: ImageVector,
    onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = Color.Red
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Add a book",
            tint = MaterialTheme.colors.primary.copy(alpha = 0.8f)
        )
    }
}