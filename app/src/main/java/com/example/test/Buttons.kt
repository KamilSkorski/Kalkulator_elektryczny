package com.example.test

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class HomeButton {

    val padding = 16.dp
    val with =400.dp
    val height = 100.dp
    val fontSize =35.sp
    val onClickFunctions = listOf(
        { "screen1" },
        { "screen2" },
        { "screen3" },
        { "screen4" },
        { "screen5" }
    )
    val buttonTexts = listOf(
        "Połączenie kondensatorów",
        "Połaczenie rezystorów",
        "Moc/Napięcie/Natężenie",
        "Rezystancja przewodów"
    )

}