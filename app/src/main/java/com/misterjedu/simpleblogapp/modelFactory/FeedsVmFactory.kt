package com.misterjedu.simpleblogapp.modelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.misterjedu.simpleblogapp.repository.IRepository

class FeedsVmFactory(private val repository: IRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IRepository::class.java)
            .newInstance(repository)
    }

}