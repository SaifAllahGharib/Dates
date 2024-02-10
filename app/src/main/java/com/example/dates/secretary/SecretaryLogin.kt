package com.example.dates.secretary

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.R
import com.example.dates.Welcome
import com.example.dates.databinding.ActivitySecretaryLoginBinding
import com.example.dates.manager.Dates
import com.example.dates.repository.Repository
import com.example.dates.util.CustomDialog
import com.example.dates.util.NetworkConnection
import com.example.dates.util.StoreToSharedPreferences
import com.example.dates.util.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class SecretaryLogin : AppCompatActivity() {
    private lateinit var binding: ActivitySecretaryLoginBinding
    private lateinit var emailTextInput: TextInputLayout
    private lateinit var passTextInput: TextInputLayout
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var isConnected: NetworkConnection
    private lateinit var dialog: CustomDialog
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var textWatcher: TextWatcher

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecretaryLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = CustomDialog(
            this,
            layoutInflater.inflate(R.layout.signup_connection, null),
            R.style.CustomAlert
        )
        emailTextInput = binding.email
        passTextInput = binding.pass
        isConnected = NetworkConnection(this)
        editor = StoreToSharedPreferences(this, "login-status-secretary").editor()
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        textWatcher = TextWatcher()

        binding.back.setOnClickListener {
            startActivity(Intent(this, Welcome::class.java))
            finish()
        }

        emailTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherLogin(
                emailTextInput,
                passTextInput,
                binding.btnLogin,
                binding.btnLoginDis
            )
        )

        passTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherLogin(
                emailTextInput,
                passTextInput,
                binding.btnLogin,
                binding.btnLoginDis
            )
        )

        binding.btnLogin.setOnClickListener {
            val email = emailTextInput.editText!!.text.toString()
            val pass = passTextInput.editText!!.text.toString()

            if (isConnected.isInternetAvailable()) {
                viewModel.loginSecretary(email.trimEnd().trimStart(), pass)
                binding.prog.visibility = View.VISIBLE

                binding.email.isEnabled = false
                binding.pass.isEnabled = false
                binding.back.isEnabled = false
                binding.btnLogin.isEnabled = false
            } else {
                dialog.view.findViewById<TextView>(R.id.textError).setText(R.string.no_connection)
                dialog.showAlertDialog()

                this.emailTextInput.editText!!.setText("")
                this.passTextInput.editText!!.setText("")
            }
        }

        viewModel.userSecretaryResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()
                val user = res!!.user

                if (res.message == "user not exists.") {
                    emailTextInput.editText!!.setText("")
                    emailTextInput.editText!!.requestFocus()
                    passTextInput.editText!!.setText("")
                    binding.prog.visibility = View.GONE
                    emailTextInput.editText!!.error =
                        "هذا الحساب غير موجود او البريد الالكتروني غير صحيح او كلمه السر"

                    binding.email.isEnabled = true
                    binding.pass.isEnabled = true
                    binding.back.isEnabled = true
                    binding.btnLogin.isEnabled = true
                } else if (res.message == "login success.") {
                    binding.prog.visibility = View.GONE

                    editor.putBoolean("login", true)
                    editor.putString("id", user[0].id.toString())
                    editor.putString("name", user[0].name)
                    editor.putString("email", user[0].email)
                    editor.commit()

                    this.emailTextInput.editText!!.setText("")
                    this.passTextInput.editText!!.setText("")

                    startActivity(Intent(this, AddDate::class.java))
                    finish()
                }
            } else {
                binding.prog.visibility = View.GONE

                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, Dates::class.java))
                finish()
            }
        }
    }
}