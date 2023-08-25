package com.tpss.mpapp

class UserRepository {

    fun login(username: String, password: String) : LOGIN_STATUS {

        println("UserRepository login test -->> $username and $password")
        return if(username == "testUser01" && password == "123456"){
            LOGIN_STATUS.SUCCESS
        }else{
            LOGIN_STATUS.UNKNOWN_USER
        }
    }
}