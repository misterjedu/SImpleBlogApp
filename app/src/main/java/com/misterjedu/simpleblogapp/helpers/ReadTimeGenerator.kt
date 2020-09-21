package com.misterjedu.simpleblogapp.helpers

object ReadTimeGenerator {

    fun getTime(): String {
        val randomNum = (Math.random() * 15).toInt()

        if (randomNum > 1) {
            return "$randomNum mins"
        }
        return "5 mins"
    }
}