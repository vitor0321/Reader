package com.walcker.reader.resource

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.PT, default = true)
internal val StringsPt = Strings(
    update = StringsUpdate(
        title = "Update",
        messageDialog = """
            Você tem certteza que quer deletar esse livro?
            Não é possível reveter essa ação...
        """.trimIndent()
    ),
    details = StringsDetails(
        title = "Detalhes"
    ),
    home = StringsHome(
        title = "Home"
    ),
    login = StringsLogin(
        title = "Login",
        createAccountMessage = "Por favor entre com um email valido e uma senha com pelo menos 6 caracteres"
    ),
    search = StringsSearch(
        title = "Pesquisar"
    ),
    splash = StringsSplash(
        slogan = "Ler. Muda. Você mesmo"
    ),
    stats = StringsStats(
        title = "Status do livro",
        yourStatus = "Seus estatos",
        reading = "Você está lendo:",
        read = "Você leu:",
        type = "livro",
        booksReading = { reading, count, type ->
            val value = when (count) {
                0, 1 -> type
                else -> type + "s"
            }
            "$reading $count $value"
        }
    )
)
