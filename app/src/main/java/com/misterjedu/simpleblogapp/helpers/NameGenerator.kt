package com.misterjedu.simpleblogapp.helpers

import kotlin.math.roundToInt

object NameGenerator {

    private val names = arrayOf(
        "Chibuzor Osuala", "Adyemi HushPuppy",
        "Louis Hotuh", "Kenechkwu Okafor", "Ola Kome"
    )


    fun getName(): String {
        val randomNum = (Math.random() * names.size).toInt()
        return names[randomNum]
    }

}
