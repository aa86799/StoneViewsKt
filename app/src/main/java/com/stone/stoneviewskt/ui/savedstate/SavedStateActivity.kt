package com.stone.stoneviewskt.ui.savedstate

import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.SavedStateViewModelFactory
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.StoneApplication

class SavedStateActivity : AppCompatActivity() {

    private val vm1: SViewModel1 by viewModels {
        SavedStateViewModelFactory(StoneApplication.instance, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_saved_state)
        val etVm1 = findViewById<EditText>(R.id.et_vm1)

        etVm1.doAfterTextChanged {
            vm1.saveState(it.toString())
        }

        vm1.userInput.observe(this) {
            if (it == etVm1.text.toString()) return@observe
            etVm1.setText(it)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        vm1.getState()
    }
}