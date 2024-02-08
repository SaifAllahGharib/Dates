package com.example.dates.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.R
import com.example.dates.databinding.ActivityEditSecretaryBinding
import com.example.dates.repository.Repository
import com.example.dates.util.CustomDialog
import com.example.dates.util.NetworkConnection
import com.google.android.material.textfield.TextInputLayout

class EditSecretary : AppCompatActivity() {
    private lateinit var binding: ActivityEditSecretaryBinding
    private lateinit var nameTextInput: TextInputLayout
    private lateinit var emailTextInput: TextInputLayout
    private lateinit var passTextInput: TextInputLayout
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var isConnected: NetworkConnection
    private lateinit var dialog: CustomDialog
    private lateinit var view: View
    private lateinit var textWatcher: com.example.dates.util.TextWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSecretaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val nameIntent = intent.getStringExtra("name")
        val emailIntent = intent.getStringExtra("email")
        val passIntent = intent.getStringExtra("pass")

        nameTextInput = binding.name
        emailTextInput = binding.email
        passTextInput = binding.pass
        view = layoutInflater.inflate(R.layout.signup_connection, null)
        isConnected = NetworkConnection(this)
        dialog = CustomDialog(this, view, R.style.CustomAlert)
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        textWatcher = com.example.dates.util.TextWatcher()

        nameTextInput.editText!!.setText(nameIntent)
        emailTextInput.editText!!.setText(emailIntent)
        passTextInput.editText!!.setText(passIntent)

        nameTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherEditManagerAndSecretary(
                nameTextInput,
                emailTextInput,
                passTextInput,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        emailTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherEditManagerAndSecretary(
                nameTextInput,
                emailTextInput,
                passTextInput,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        passTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherEditManagerAndSecretary(
                nameTextInput,
                emailTextInput,
                passTextInput,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        binding.back.setOnClickListener {
            startActivity(Intent(this, Admin::class.java))
            finish()
        }

        binding.btnEdit.setOnClickListener {
            val name = nameTextInput.editText!!.text.toString()
            val email = emailTextInput.editText!!.text.toString()
            val pass = passTextInput.editText!!.text.toString()

            if (isConnected.isInternetAvailable()) {
                viewModel.editSecretary(id, name, email, pass)

                binding.prog.visibility = View.VISIBLE

                binding.back.isEnabled = false
                binding.email.isEnabled = false
                binding.name.isEnabled = false
                binding.pass.isEnabled = false
                binding.btnEdit.isEnabled = false
            } else {
                dialog.view.findViewById<TextView>(R.id.textError).setText(R.string.no_connection)
                dialog.showAlertDialog()

                binding.back.isEnabled = true
                binding.email.isEnabled = true
                binding.name.isEnabled = true
                binding.pass.isEnabled = true
                binding.btnEdit.isEnabled = true
            }
        }

        viewModel.defaultResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!
                if (res.message == "User updated.") {
                    binding.prog.visibility = View.GONE

                    Toast.makeText(this, "تم تحديث المستخدم", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, Admin::class.java))
                    finish()
                } else if (res.message == "User not updated.") {
                    binding.prog.visibility = View.GONE

                    Toast.makeText(this, "لم يتم تحديث المستخدم", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, Admin::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()

                binding.prog.visibility = View.GONE

                binding.back.isEnabled = true
                binding.email.isEnabled = true
                binding.name.isEnabled = true
                binding.pass.isEnabled = true
                binding.btnEdit.isEnabled = true
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (binding.prog.visibility == View.GONE) {
            startActivity(Intent(this, Admin::class.java))
            finish()
        }
    }
}