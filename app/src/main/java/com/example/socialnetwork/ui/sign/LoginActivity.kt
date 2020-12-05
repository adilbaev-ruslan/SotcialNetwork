package com.example.socialnetwork.ui.sign

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.socialnetwork.R
import com.example.socialnetwork.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.loading
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.username


class LoginActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener{
            loading.visibility = View.VISIBLE
            if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                mAuth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
                    .addOnCompleteListener { task->
                        if (task.isSuccessful) {
                            val currentUser = mAuth.currentUser
                            loading.visibility = View.GONE
                            updateUI(currentUser)
                        } else {
                            loading.visibility = View.GONE
                            Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                loading.visibility = View.GONE
                Toast.makeText(this, "Poliyalar toltiriliwi shart", Toast.LENGTH_LONG).show()
            }
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
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