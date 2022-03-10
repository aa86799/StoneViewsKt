package com.stone.stoneviewskt.service

import android.service.autofill.UserData

class CesManager private constructor() {

    private var user: UserData? = null

    companion object {
        val instance = Holder.instance
    }

    private object Holder {
        val instance = CesManager()
    }

    fun getUserData(): UserData? {
        return user
    }
}