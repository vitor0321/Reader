package com.walcker.reader.presentation.common

import cafe.adriel.voyager.core.screen.Screen

public abstract class Step(
    private val analyticsName: String,
) : Screen {

    init {
        analyticsName
    }

    override val key: String = ""
}