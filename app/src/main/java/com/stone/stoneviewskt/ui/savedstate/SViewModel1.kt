package com.stone.stoneviewskt.ui.savedstate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.stone.stoneviewskt.util.logi

class SViewModel1(private val savedStateHandle: SavedStateHandle): ViewModel() {

    // 通过 SavedStateHandle 获取保存的文本
    var userInput: MutableLiveData<String> = savedStateHandle.getLiveData("USER_INPUT")

    fun saveState(inputEditText: String) {
        savedStateHandle["key1"] = "value1"
        savedStateHandle.getLiveData<String>("key2").value = "value2"

//        userInput.value = inputEditText
        savedStateHandle["USER_INPUT"] = inputEditText // 这样也可以
    }

    fun getState() {
        logi(savedStateHandle["key1"] ?: "empty 1")
        logi(savedStateHandle.getLiveData<String>("key2").value ?: "empty 2")
        logi(savedStateHandle.getLiveData<String>("USER_INPUT").value ?: "empty 3")
    }
}