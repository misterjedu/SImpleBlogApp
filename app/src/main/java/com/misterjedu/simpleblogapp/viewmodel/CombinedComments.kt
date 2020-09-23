package com.misterjedu.simpleblogapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.misterjedu.simpleblogapp.roomModel.Comment
import com.misterjedu.simpleblogapp.roomModel.Post

class CombinedComments(
    apiPost: LiveData<List<Comment>>,
    roomPost: LiveData<List<Comment>>
) : MediatorLiveData<Pair<List<Comment>, List<Comment>>>() {

    /**
     * Mediator Live Data to combine data from the Room Database and from the Api
     */
    private var listApi: List<Comment> = emptyList()
    private var listRoom: List<Comment> = emptyList()


    init {
        value = Pair(listApi, listRoom)

        addSource(apiPost) {
            if (it != null) listApi = it
            value = Pair(listApi, listRoom)
        }

        addSource(roomPost) {
            if (it != null) {
                listRoom = it
                value = Pair(listApi, listRoom)
            } else {
                value = Pair(emptyList(), listRoom)
            }
        }
    }
}