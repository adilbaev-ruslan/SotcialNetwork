package com.example.socialnetwork

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            loading.visibility = View.VISIBLE
            if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                mAuth.createUserWithEmailAndPassword(username.text.toString(), password.text.toString())
                    .addOnCompleteListener {task->
                        if (task.isSuccessful) {
                            val currentUser = mAuth.currentUser
                            loading.visibility = View.GONE
                            updateUI(currentUser)
                        } else {
                            loading.visibility = View.GONE
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                loading.visibility = View.GONE
                Toast.makeText(this, "Poliyalardi toltiriw shart", Toast.LENGTH_LONG).show()
            }
        }

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}