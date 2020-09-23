package com.misterjedu.simpleblogapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.misterjedu.simpleblogapp.roomModel.Post

class CombinedPosts(
    apiPost: LiveData<List<Post>>,
    roomPost: LiveData<List<Post>>
) : MediatorLiveData<Pair<List<Post>, List<Post>>>() {

    /**
     * Mediator Live Data to combine data from the Room Database and from the Api
     */
    private var listApi: List<Post> = emptyList()
    private var listRoom: List<Post> = emptyList()


    init {
        value = Pair(listApi, listRoom)

        addSource(apiPost) {
            if (it != null) {
                listApi = it
                value = Pair(listApi, listRoom)
            } else {
                value = Pair(emptyList(), listRoom)
            }
        }

        addSource(roomPost) {
            if (it != null) listRoom = it
            value = Pair(listApi, listRoom)
        }


    }
}