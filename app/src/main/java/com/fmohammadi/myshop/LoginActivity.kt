package com.fmohammadi.myshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        supportActionBar!!.title = "ورود"

        loginBtnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

    }

    override fun onStart() {
        super.onStart()

        loginBtnLogin.setOnClickListener {
            val email = loginEmail.text.trim().toString()
            val password = loginPassword.text.trim().toString()


            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                signWithEmailPassword(email, password)
            } else {
                Toast.makeText(this, "لطفا اطلاعات خواسته شده را وارد کنید", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun signWithEmailPassword(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // todo go to next page
                    Toast.makeText(
                        this,
                        "شما وارد شدید",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        val message = e.message!!.trim()
                        Log.d("LoginUser", "onComplete: $message")
                        when (message) {
                            "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                                Toast.makeText(
                                    this,
                                    "شما قبلا ثبت نام نکرده اید لطفا ثبت نام کنید",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            "The email address is badly formatted." -> {
                                Toast.makeText(
                                    this,
                                    "ایمیل وارد شده معتبر نیست",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            "The password is invalid or the user does not have a password." -> {
                                Toast.makeText(
                                    this,
                                    "اطلاعات وارد شده نادرست است",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else -> {
                                Toast.makeText(
                                    this,
                                    "ورود انجام نشد دوباره امتحان کنید",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
    }
}