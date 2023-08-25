package com.tpss.mpapp

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpss.mpapp.databinding.ActivityMainBinding
import java.security.Signature
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var promptInfo: PromptInfo
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var executor: Executor
    private lateinit var  signature: Signature
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var adapter: TableAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)

        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main)
        executor = ContextCompat.getMainExecutor(this)
        val userRepository = UserRepository()
        loginPresenter = LoginPresenter(userRepository)
        setupLogin()
//        checkDeviceHasBiometric()
        setupRv()
    }

    private fun setupRv() {
        val layoutManager = LinearLayoutManager(this)
        mainActivityBinding.recyclerView.layoutManager = layoutManager

        val data: List<TableRowData> = ArrayList()
        for (i in 0 until 6) {
            data.plus(
                TableRowData(
                    "Data " + (i + 1) + "-1",
                    "Data " + (i + 1) + "-2",
                    "Data " + (i + 1) + "-3",
                    "Data " + (i + 1) + "-4",
                    "Data " + (i + 1) + "-5",
                    "Data " + (i + 1) + "-6",
                )
            )
        }

        adapter = TableAdapter(data)
        mainActivityBinding.recyclerView.adapter = adapter
    }

    private fun setupLogin() {
      /*    val btn = findViewById<Button>(R.id.btnLogin)
          btn.setOnClickListener {
              Toast.makeText(this, "Button Click", Toast.LENGTH_SHORT).show()
          }*/
       /* mainActivityBinding.btnLogin.setOnClickListener {
        val usernameText = mainActivityBinding.etUserName.text.toString()
        val passwordText = mainActivityBinding.etPassword.text.toString()*/

//            loginPresenter.loginUser(username = usernameText, password = passwordText)
//        }
    }

    private fun createPromptInfo() {
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication")
            .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()
    }

    private fun createBiometricListener() {
        biometricPrompt = BiometricPrompt(this, executor, object :  BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(this@MainActivity, "Authentication error - $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
             try {
                 val cryptoObject = result.cryptoObject!!
                 val cryptoSignature = cryptoObject.signature!!

                 val payload = "Hello, world!".toByteArray()
                 cryptoSignature.update(payload)

                 val signatureBytes: ByteArray = cryptoSignature.sign()

                 var signedString: String = Base64.encodeToString(signatureBytes, Base64.DEFAULT)
                 signedString = signedString.replace("\r".toRegex(), "").replace("\n".toRegex(), "")
                 Toast.makeText(this@MainActivity, "Authentication success :: $signedString", Toast.LENGTH_LONG).show()
             } catch (e: java.lang.Exception){
                 Toast.makeText(this@MainActivity, "Authentication Exception :: ${e.message}", Toast.LENGTH_LONG).show()
             }


            }

            override fun onAuthenticationFailed() {
                Toast.makeText(this@MainActivity, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkDeviceHasBiometric() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)){
            BiometricManager.BIOMETRIC_SUCCESS -> {
                createBiometricListener()
                createCryptoSignature()
                createPromptInfo()


                try{
                    val cryptoObject = BiometricPrompt.CryptoObject(signature)
                    biometricPrompt.authenticate(promptInfo, cryptoObject)
                } catch (e: java.lang.Exception){
                    Log.e("App ERROR", "checkDeviceHasBiometric: " + signature )
                    Toast.makeText(this@MainActivity, "Biometric signature error", Toast.LENGTH_SHORT).show()
                }

            }
            else -> {
                Toast.makeText(this@MainActivity, "Biometric not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createCryptoSignature() {
//        SecurityUtil.getKeyPair()
//        val entry: KeyStore.Entry? = keyStore.getEntry("keyAlias", null)

        val byteData = "AppBiometric".toByteArray()
        val privateKey = SecurityUtil.getPrivateKey()
        signature = Signature.getInstance("SHA256withRSA")
        signature.initSign(privateKey)
        signature.update(byteData)
        signature.sign()
    }

}