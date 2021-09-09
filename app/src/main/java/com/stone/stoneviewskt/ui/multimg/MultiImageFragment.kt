package com.stone.stoneviewskt.ui.multimg

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment

class MultiImageFragment : BaseFragment() {

    private val mChooseAlbumLauncher = requestForResult {
        if (it.resultCode != Activity.RESULT_OK) return@requestForResult
        it.data?.data?.let { uri ->
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
//                val file = ImageUploadHelper.imageUri2File(uri) ?: return@launchWhenCreated
//                val imgUri = Uri.fromFile(file)
//                chooseAlbumResult(imgUri)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_multi_image
    }

    fun load(view: View) {

    }

     fun requestForResult(block: (ActivityResult) -> Unit): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            block(result)
        }
    }
}