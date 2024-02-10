package com.example.dates.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.R
import com.example.dates.databinding.ActivityCreateAccountsBinding
import com.example.dates.model.UserSecretary
import com.example.dates.repository.Repository
import com.example.dates.util.CustomDialog
import com.example.dates.util.NetworkConnection
import com.example.dates.util.StoreToSharedPreferences
import com.example.dates.util.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class CreateAccounts : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountsBinding
    private lateinit var nameTextInput: TextInputLayout
    private lateinit var emailTextInput: TextInputLayout
    private lateinit var passTextInput: TextInputLayout
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var isConnected: NetworkConnection
    private lateinit var dialog: CustomDialog
    private lateinit var textWatcher: TextWatcher
    private lateinit var view: View
    private var type: String? = null
    private lateinit var secretary: String
    private lateinit var list: ArrayList<String>
    private lateinit var secretarys: ArrayList<UserSecretary>
    private lateinit var sharedPreferences: SharedPreferences
    private var idSecretary: Int = 0

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        secretarys = ArrayList()
        nameTextInput = binding.name
        emailTextInput = binding.email
        passTextInput = binding.pass
        view = layoutInflater.inflate(R.layout.signup_connection, null)
        isConnected = NetworkConnection(this)
        dialog = CustomDialog(this, view, R.style.CustomAlert)
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        sharedPreferences = StoreToSharedPreferences(this, "login-status-admin").getValues()
        textWatcher = TextWatcher()

        val adminId: Int = sharedPreferences.getString("id", "")!!.toInt()

        nameTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherCreateAccounts(
                nameTextInput,
                emailTextInput,
                passTextInput,
                binding.type,
                binding.btnSignup,
                binding.btnSignupDis
            )
        )

        emailTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherCreateAccounts(
                nameTextInput,
                emailTextInput,
                passTextInput,
                binding.type,
                binding.btnSignup,
                binding.btnSignupDis
            )
        )

        passTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherCreateAccounts(
                nameTextInput,
                emailTextInput,
                passTextInput,
                binding.type,
                binding.btnSignup,
                binding.btnSignupDis
            )
        )

        binding.type.addTextChangedListener(
            textWatcher.textWatcherCreateAccounts(
                nameTextInput,
                emailTextInput,
                passTextInput,
                binding.type,
                binding.btnSignup,
                binding.btnSignupDis
            )
        )

        binding.type.setAdapter(
            ArrayAdapter(
                this,
                R.layout.dropdown_item,
                arrayOf("مدير", "مساعد")
            )
        )

        binding.type.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_dropdown_menu,
                null
            )
        )

        binding.type.setOnItemClickListener { parent, view, position, id ->
            type = parent.getItemAtPosition(position).toString()

            list.clear()
            secretarys.clear()

            if (type == "مدير") {
                binding.secretaryTIL.visibility = View.VISIBLE

                if (isConnected.isInternetAvailable()) {
                    viewModel.getAllSecretary()
                    binding.prog.visibility = View.VISIBLE

                    binding.email.isEnabled = false
                    binding.name.isEnabled = false
                    binding.pass.isEnabled = false
                    binding.type.isEnabled = false
                    binding.secretary.isEnabled = false
                    binding.back.isEnabled = false
                    binding.btnSignup.isEnabled = false
                } else {
                    Toast.makeText(
                        this,
                        "لا يوجد اتصال بالانترنت لجلب المساعدين",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                binding.secretaryTIL.visibility = View.GONE
                binding.prog.visibility = View.GONE
            }
        }

        binding.secretary.setOnItemClickListener { parent, view, position, id ->
            secretary = parent.getItemAtPosition(position).toString()

            for (sec in secretarys) {
                if (secretary == sec.name) {
                    idSecretary = sec.id
                }
            }
        }

        binding.secretary.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_dropdown_menu,
                null
            )
        )

        viewModel.secretaryResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.secretary.isNotEmpty()) {
                    for (secretary in res.secretary) {
                        list.add(secretary.name)
                        secretarys.add(secretary)
                    }

                    binding.secretary.setAdapter(
                        ArrayAdapter(
                            this,
                            R.layout.dropdown_item,
                            list
                        )
                    )

                    binding.prog.visibility = View.GONE

                    Toast.makeText(this, "تم جلب المساعدين", Toast.LENGTH_SHORT).show()

                    binding.email.isEnabled = true
                    binding.name.isEnabled = true
                    binding.pass.isEnabled = true
                    binding.type.isEnabled = true
                    binding.secretary.isEnabled = true
                    binding.back.isEnabled = true
                    binding.btnSignup.isEnabled = true
                }
            } else {
                binding.prog.visibility = View.GONE

                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()

                binding.email.isEnabled = true
                binding.name.isEnabled = true
                binding.pass.isEnabled = true
                binding.type.isEnabled = true
                binding.secretary.isEnabled = true
                binding.back.isEnabled = true
                binding.btnSignup.isEnabled = true
            }
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, Admin::class.java))
            finish()
        }

        binding.btnSignup.setOnClickListener {
            val name = nameTextInput.editText!!.text.toString()
            val email = emailTextInput.editText!!.text.toString()
            val pass = passTextInput.editText!!.text.toString()

            if (isConnected.isInternetAvailable()) {
                if (type == "مدير" && adminId != 0) {
                    viewModel.signupManager(
                        idSecretary,
                        adminId,
                        name.trimStart().trimEnd(),
                        email.trimStart().trimEnd(),
                        pass
                    )
                } else if (type == "مساعد" && adminId != 0) {
                    viewModel.signupSecretary(
                        adminId,
                        name.trimStart().trimEnd(),
                        email.trimStart().trimEnd(),
                        pass
                    )
                }

                binding.prog.visibility = View.VISIBLE

                binding.back.isEnabled = false
                binding.email.isEnabled = false
                binding.name.isEnabled = false
                binding.type.isEnabled = false
                binding.pass.isEnabled = false
                binding.btnSignup.isEnabled = false

                if (type == "مدير") {
                    viewModel.userManagerResponse.observe(this) { response ->
                        if (response.isSuccessful) {
                            val res = response.body()!!

                            if (res.message == "User already exists.") {
                                emailTextInput.editText!!.setText("")
                                emailTextInput.editText!!.requestFocus()
                                binding.prog.visibility = View.GONE
                                emailTextInput.editText!!.error = "هذا الحساب موجود بالفعل"

                                binding.back.isEnabled = true
                                binding.email.isEnabled = true
                                binding.name.isEnabled = true
                                binding.type.isEnabled = true
                                binding.pass.isEnabled = true
                                binding.btnSignup.isEnabled = true
                            } else if (res.message == "User created.") {
                                binding.prog.visibility = View.GONE

                                startActivity(Intent(this, Admin::class.java))
                                finish()
                            }
                        } else {
                            Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()

                            binding.prog.visibility = View.GONE

                            binding.back.isEnabled = true
                            binding.email.isEnabled = true
                            binding.name.isEnabled = true
                            binding.type.isEnabled = true
                            binding.pass.isEnabled = true
                            binding.btnSignup.isEnabled = true
                        }
                    }
                } else if (type == "مساعد") {
                    viewModel.userSecretaryResponse.observe(this) { response ->
                        if (response.isSuccessful) {
                            val res = response.body()!!

                            if (res.message == "User already exists.") {
                                emailTextInput.editText!!.setText("")
                                emailTextInput.editText!!.requestFocus()
                                binding.prog.visibility = View.GONE
                                emailTextInput.editText!!.error = "هذا الحساب موجود بالفعل"

                                binding.back.isEnabled = true
                                binding.email.isEnabled = true
                                binding.name.isEnabled = true
                                binding.type.isEnabled = true
                                binding.pass.isEnabled = true
                                binding.btnSignup.isEnabled = true
                            } else if (res.message == "User created.") {
                                binding.prog.visibility = View.GONE

                                startActivity(Intent(this, Admin::class.java))
                                finish()
                            }
                        } else {
                            Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()

                            binding.prog.visibility = View.GONE

                            binding.back.isEnabled = true
                            binding.email.isEnabled = true
                            binding.name.isEnabled = true
                            binding.type.isEnabled = true
                            binding.pass.isEnabled = true
                            binding.btnSignup.isEnabled = true
                        }
                    }
                }
            } else {
                dialog.view.findViewById<TextView>(R.id.textError).setText(R.string.no_connection)
                dialog.showAlertDialog()

                this.nameTextInput.editText!!.setText("")
                this.emailTextInput.editText!!.setText("")
                this.passTextInput.editText!!.setText("")

                binding.back.isEnabled = true
                binding.email.isEnabled = true
                binding.name.isEnabled = true
                binding.type.isEnabled = true
                binding.pass.isEnabled = true
                binding.btnSignup.isEnabled = true
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