package com.example.sewakendaraan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sewakendaraan.room.UserDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var layoutMain: ConstraintLayout
    lateinit var mBundle: Bundle
    val db by lazy { UserDB(this) }

    lateinit var vUsername: String
    lateinit var vPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Sewa Kendaraan")
        supportActionBar?.hide()

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        layoutMain = findViewById(R.id.main)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        val btnNavRegister:Button = findViewById(R.id.registerNavBtn)
        btnNavRegister.setOnClickListener(View.OnClickListener {
            val moveRegister = Intent(this@MainActivity, Register::class.java)
            startActivity(moveRegister)

        })

        if(intent.hasExtra("register")){
            getBundle()
        }else{
            vUsername = "admin"
            vPassword = "admin"
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLoqin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                inputUsername.setError("Required")
                checkLoqin = false
            }

            if (password.isEmpty()) {
                inputPassword.setError("Required")
                checkLoqin = false
            }

            if(!username.isEmpty() && !password.isEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val users = db.userDao().getUsernamePassword(username, password)
                    Log.d("MainActivity", "dbResponses: $users")
                    if(users != null){
                        val moveHome = Intent(this@MainActivity, Home::class.java)
                        startActivity(moveHome)
                    }
                    finish()
                }
            }

           /* if (!checkLoqin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, Home::class.java)
            startActivity(moveHome)*/
        })
    }

    fun getBundle(){
        mBundle = intent.getBundleExtra("register")!!
        if(!mBundle.isEmpty){
            vUsername = mBundle.getString("username")!!
            vPassword = mBundle.getString("password")!!
            inputUsername.getEditText()?.setText(vUsername)
        }
    }
}