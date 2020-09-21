package com.misterjedu.simpleblogapp.helpers

import kotlin.math.roundToInt

object DateGenerator {
    private lateinit var date: String

    private var months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "June",
        "July", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    //Get day Suffix
    private fun getSuffix(date: Int): String {
        if (date == 11 || date == 12 || date == 13) {
            return "th"
        } else if (date.toString().endsWith("1")) {
            return "st"
        } else if (date.toString().endsWith("2")) {
            return "nd"
        } else if (date.toString().endsWith("2")) {
            return "rd"
        }
        return "th"
    }

    private fun getRandomDate(): Int {
        return (Math.random() * 31).roundToInt()
    }

    private fun getRandomMonth() : String{
        val randomMonthNum = (Math.random() * 12).toInt()
        return months[randomMonthNum]
    }

    fun getPostDate() : String{
        val day = getRandomDate()
        date = "$day${getSuffix(day)} ${getRandomMonth()}, 2020"
        return date
    }


    fun getCommentDate(){
        TODO()
    }


}