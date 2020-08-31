package com.fmohammadi.myshop.controller.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.fmohammadi.myshop.R
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    private var userId: String? = null
    private var database: FirebaseDatabase? = null
    private var myRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        supportActionBar!!.title = "ثبت نام"

    }

    override fun onStart() {
        super.onStart()

        registerBtnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        registerBtnRegister.setOnClickListener {
            val name = registerName.text.trim().toString()
            val email = registerEmail.text.trim().toString()
            val password = registerPassword.text.trim().toString()
            val rePassword = registerRePassword.text.trim().toString()

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) && !TextUtils.isEmpty(rePassword)
            ) {

                if (password != rePassword) {
                    Toast.makeText(this, "رمز عبور با تکرار آن مساوی نیست", Toast.LENGTH_LONG)
                        .show()
                } else {
                    createUserWithEmailPassword(email, password, name)
                }

            } else {
                Toast.makeText(this, "لطفا اطلاعات خواسته شده را وارد کنید", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun createUserWithEmailPassword(
        email: String,
        password: String,
        name: String
    ) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    currentUser = mAuth!!.currentUser
                    userId = currentUser!!.uid

                    myRef = database!!.getReference("Users").child(userId!!)

                    val userObject = HashMap<String, String>()

                    userObject.put("userEmail", email)
                    userObject.put("userName", name)
                    userObject.put("userAddress", "")
                    userObject.put("userPhoneNumber", "")
                    userObject.put("userImage", "default")

                    myRef!!.setValue(userObject).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "ثبت نام با موفقیت انجام شد",
                                Toast.LENGTH_LONG
                            ).show()

                        } else {
                            Toast.makeText(
                                this,
                                "اطلاعات شما به درستی ثبت نشد لطفا از میزکار خود آنها را وارد کنید",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                } else {
                    try {
                        throw task.exception!!
                    }

                    // if user enters wrong email.
                    catch (weakPassword: FirebaseAuthWeakPasswordException) {
                        Toast.makeText(
                            this,
                            "لطفا رمز قوی انتخاب کنید",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    // if user enters exist email
                    catch (existEmail: FirebaseAuthUserCollisionException) {
                        Toast.makeText(
                            this,
                            "شما قبلا ثبت نام کرده اید لطفا وارد شوید",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    // if user enters wrong email.
                    catch (invalidEmail: FirebaseAuthInvalidUserException) {
                        Toast.makeText(
                            this,
                            "ایمیل وارد شده معتبر نیست",
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Log.d("CreateUser", "onComplete: " + e.message);
                        Toast.makeText(
                            this,
                            "ثبت نام انجام نشد دوباره امتحان کنید",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }
}