package com.misterjedu.simpleblogapp.modelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.misterjedu.simpleblogapp.repository.Repository

class FeedsVmFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java)
            .newInstance(repository)
    }

}