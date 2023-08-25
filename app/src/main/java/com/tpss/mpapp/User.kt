package com.tpss.mpapp

data class User (val userName: String, val fullName: String)

enum class LOGIN_STATUS{
    INVALID_USER,
    INVALID_PASSWORD,
    UNKNOWN_USER,
    SUCCESS
}