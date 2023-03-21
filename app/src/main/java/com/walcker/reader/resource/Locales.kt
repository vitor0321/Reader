package com.walcker.reader.resource

import androidx.compose.runtime.staticCompositionLocalOf

object Locales {
    const val EN = "en"
    const val PT = "pt"
}

val strings = mapOf(
    Locales.EN to StringsEn,
    Locales.PT to StringsPt
)

val LocalStrings = staticCompositionLocalOf { StringsPt }
