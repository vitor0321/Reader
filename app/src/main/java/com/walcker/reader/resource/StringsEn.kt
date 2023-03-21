package com.walcker.reader.resource

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.EN, default = true)
internal val StringsEn = Strings(
    update = StringsUpdate(
        title = "Update",
        messageDialog = """
            Are you sure you want delete this book?
            This action is not reversible
        """.trimIndent()
    ),
    details = StringsDetails(
        title = "Details"
    ),
    home = StringsHome(
        title = "Home"
    ),
    login = StringsLogin(
        title = "Login",
        createAccountMessage = "Please enter a valid email and password that is at least 6 characters"
    ),
    search = StringsSearch(
        title = "Search"
    ),
    splash = StringsSplash(
        slogan = "Read. Change. Yourself"
    ),
    stats = StringsStats(
        title = "Book Stats",
        yourStatus = "Your Stats",
        reading = "You're reading:",
        read = "You've read",
        type = "book",
        booksReading = { reading, count, type ->
            val value = when (count) {
                0, 1 -> type
                else -> type + "s"
            }
            "$reading $count $value"
        }
    )
)
