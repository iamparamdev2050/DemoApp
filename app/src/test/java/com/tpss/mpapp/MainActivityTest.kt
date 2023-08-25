package com.tpss.mpapp

import android.util.Log
import android.widget.Toast
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
//        `when`(userRepository.login(anyString(), anyString())).thenReturn(LOGIN_STATUS.INVALID_PASSWORD)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testLogin() {
        testValidUserNameAndPassword()
        testInValidUserNameAndPassword()
    }

    @Test
    fun testValidUserNameAndPassword() {
       val task = LoginPresenter(userRepository)
//        val userRepository = mock(UserRepository::class.java)
            val username = "testUser01"
        val password = "123456"
        val loginStatus = LOGIN_STATUS.SUCCESS
        `when`(userRepository.login(anyString(), anyString())).thenReturn(loginStatus)
        val status = task.loginUser(username, password)
        assertEquals("Login Success",status)
    }

    @Test
    fun testInValidUserNameAndPassword() {
        val task = LoginPresenter(userRepository)
//        val userRepository = mock(UserRepository::class.java)
        val username = "testUser"
        val password = "123456"
        val loginStatus = LOGIN_STATUS.UNKNOWN_USER
        `when`(userRepository.login(anyString(), anyString())).thenReturn(loginStatus)
        val status = task.loginUser(username, password)
        assertEquals("User is invalid",status)
    }


}