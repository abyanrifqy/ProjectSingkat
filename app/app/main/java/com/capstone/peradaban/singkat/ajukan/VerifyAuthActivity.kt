package com.capstone.peradaban.singkat.ajukan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.databinding.ActivityVerifyAuthBinding

import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class VerifyAuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityVerifyAuthBinding
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val storedVerificationId = intent.getStringExtra("storedVerificationId")

//        Reference
        val verify = binding.verifyBtn
        val otpGiven = binding.idOtp


        verify.setOnClickListener{
            val otp = otpGiven.text.toString().trim()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, R.string.verify_otp, Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, KetentuanActivity::class.java))
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, R.string.failed_otp, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}