package com.walcker.reader.resource

data class Strings(
    val update: StringsUpdate,
    val details: StringsDetails,
    val home: StringsHome,
    val login: StringsLogin,
    val search: StringsSearch,
    val splash: StringsSplash,
    val stats: StringsStats,
)

data class StringsUpdate(
    val title: String,
    val messageDialog: String,
)

data class StringsDetails(
    val title: String,
)

data class StringsHome(
    val title: String,
)

data class StringsLogin(
    val title: String,
    val createAccountMessage: String,
)

data class StringsSearch(
    val title: String,
)

data class StringsSplash(
    val slogan: String,
)

data class StringsStats(
    val title: String,
    val yourStatus: String,
    val reading: String,
    val read: String,
    val type: String,
    val booksReading: (reading: String, count: Int, type: String) -> String = { reading, count, type ->
        val value = when (count) {
            0, 1 -> type
            else -> type+"s"
        }
        "$reading $count $value"
    },
)
