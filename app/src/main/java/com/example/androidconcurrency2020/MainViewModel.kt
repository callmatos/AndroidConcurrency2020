package com.example.androidconcurrency2020

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset

class MainViewModel: ViewModel() {

    val myData = MutableLiveData<String> ()

    private lateinit var job: Job

    fun doWork(){
        job = viewModelScope.launch {
                myData.value = fetchSomething()
        }
    }

    fun cancelJob(){
        try {
            job.cancel()
            myData.value = "Job cancelled"
        } catch (ignore: UninitializedPropertyAccessException ) {
        }
    }

    private suspend fun fetchSomething() : String?{

        var contents: String? = null
        return withContext(Dispatchers.IO){
            val url = URL(fileUrl)
            return@withContext url.readText(Charset.defaultCharset())
        }
    }

}