package com.badadarr.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar: Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        mAuth = FirebaseAuth.getInstance()

        register_btn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username: String = username_register.text.toString()
        val email: String = email_register.text.toString()
        val password: String = password_register.text.toString()

        if (username == "") {
            Toast.makeText(this@RegisterActivity, "Tulis Nama Pengguna Anda.", Toast.LENGTH_LONG).show()
        }
        else if (email == "") {
            Toast.makeText(this@RegisterActivity, "Tulis Email Anda.", Toast.LENGTH_LONG).show()
        }
        else if (password == "") {
            Toast.makeText(this@RegisterActivity, "Tulis Password Anda Disini.", Toast.LENGTH_LONG).show()
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        firebaseUserID = mAuth.currentUser!!.uid
                        refUsers =  FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["username"] = username
                        userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/messengerapp-cc588.appspot.com/o/profile.jpg?alt=media&token=f5d68868-9fa8-4dae-8ab6-95b3b762452e"
                        userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/messengerapp-cc588.appspot.com/o/cover.jpg?alt=media&token=bd9a1ef1-bf4b-464f-b774-0ab8ee18b5ee"
                        userHashMap["status"] = "Idle"
                        userHashMap["search"] = username.toLowerCase()
                        userHashMap["facebook"] = "https://darknet.tor"
                        userHashMap["instagram"] = "https://instagram.com/login"
                        userHashMap["website"] = "https://reddit.com"

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                    }
                    else {
                        Toast.makeText(this@RegisterActivity, "Message Error: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}