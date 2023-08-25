package com.tpss.mpapp

import android.util.Log

class LoginPresenter(private val userRepository: UserRepository) {

    fun loginUser(username: String, password: String) : String{
        val loginStatus = userRepository.login(username, password)
        Log.e("Login Presenter", "loginUser: $username || $password" )
        Log.e("Login Presenter", "loginUser: $loginStatus" )

        println("During login test -->> $username and $password ||| $loginStatus")
        return when(loginStatus) {
            LOGIN_STATUS.SUCCESS -> "Login Success"
            LOGIN_STATUS.UNKNOWN_USER -> "User not found"
            LOGIN_STATUS.INVALID_PASSWORD -> "Password is invalid"
            LOGIN_STATUS.INVALID_USER -> "User is invalid"
            else -> "Something went wrong"
        }
    }
}