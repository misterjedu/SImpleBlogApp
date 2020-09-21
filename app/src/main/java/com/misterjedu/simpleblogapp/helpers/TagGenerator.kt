package com.misterjedu.simpleblogapp.helpers

import kotlin.math.roundToInt

object TagGenerator {
    private val tags = arrayOf("UI/UX", "C#", "PYTHON", "ANDROID", "IOS", "NODE")

    fun getTag(): String {
        val randomMonthNum = (Math.random() * tags.size).toInt()
        return tags[randomMonthNum]
    }
}