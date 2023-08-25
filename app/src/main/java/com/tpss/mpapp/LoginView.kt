package com.tpss.mpapp

interface LoginView {

    fun showLoginSuccess(user: User)
    fun showLoginFailed(msg: String)

}