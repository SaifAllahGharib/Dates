package com.example.dates.secretary

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.R
import com.example.dates.Welcome
import com.example.dates.adapter.SecretaryDatesAdapter
import com.example.dates.databinding.ActivityAddDateBinding
import com.example.dates.model.Date
import com.example.dates.repository.Repository
import com.example.dates.util.CustomDialog
import com.example.dates.util.NetworkConnection
import com.example.dates.util.StoreToSharedPreferences
import com.google.android.material.button.MaterialButton

class AddDate : AppCompatActivity() {
    private lateinit var binding: ActivityAddDateBinding
    private lateinit var tabs: ArrayList<String>
    private lateinit var sharedPreferencesLoginSecretary: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dialog: CustomDialog
    private lateinit var dialogConnection: CustomDialog
    private lateinit var manager: String
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var isConnected: NetworkConnection
    private lateinit var managers: ArrayList<String>
    private var adapter: SecretaryDatesAdapter? = null
    private lateinit var list: ArrayList<Date>
    private var idManager: Int = 0

    @SuppressLint("NotifyDataSetChanged", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogConnection = CustomDialog(
            this,
            layoutInflater.inflate(R.layout.signup_connection, null),
            R.style.CustomAlert
        )
        dialog = CustomDialog(
            this,
            layoutInflater.inflate(R.layout.signout_dialog, null),
            R.style.CustomAlert
        )
        isConnected = NetworkConnection(this)
        managers = ArrayList()
        list = ArrayList()
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        tabs = arrayListOf("اليومي", "الاسبوعي", "الكل")
        sharedPreferencesLoginSecretary =
            StoreToSharedPreferences(this, "login-status-secretary").editor()
        sharedPreferences = StoreToSharedPreferences(this, "login-status-secretary").getValues()

        val idSecretary: Int = sharedPreferences.getString("id", "")!!.toInt()

        if (isConnected.isInternetAvailable() && idSecretary != 0) {
            viewModel.getSecretaryManagers(idSecretary)
        }

        viewModel.secretaryManagersResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.message == "Managers geted") {

                    for (manager in res.manager) {
                        managers.add(manager.name)
                    }

                    binding.manager.setAdapter(
                        ArrayAdapter(
                            this,
                            R.layout.dropdown_item,
                            managers
                        )
                    )
                }
            } else {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
        }

        binding.manager.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_dropdown_menu,
                null
            )
        )

        binding.manager.setOnItemClickListener { parent, _, position, _ ->
            manager = parent.getItemAtPosition(position).toString()

            list.clear()
            binding.datesRv.visibility = View.GONE

            viewModel.getManager(manager)
        }

        viewModel.userManagerResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.message == "User exists.") {
                    binding.prog.visibility = View.GONE
                    binding.datesRv.visibility = View.VISIBLE
                    binding.isEmpty.visibility = View.GONE
                    binding.notManager.visibility = View.GONE

                    idManager = res.user[0].id
                    viewModel.getAllDateToSecretary(idManager)
                } else if (res.message == "User not exists.") {
                    binding.prog.visibility = View.GONE
                    binding.isEmpty.visibility = View.VISIBLE
                    binding.datesRv.visibility = View.GONE
                    binding.notManager.visibility = View.GONE
                }
            } else {
                binding.prog.visibility = View.GONE
                binding.isEmpty.visibility = View.VISIBLE
                binding.datesRv.visibility = View.GONE

                Toast.makeText(this, "فشل في جلب البيانات.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.datesResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.message == "Dates exists") {
                    list = response.body()!!.date
                    binding.datesRv.visibility = View.VISIBLE
                    binding.prog.visibility = View.GONE
                    binding.datesRv.layoutManager = LinearLayoutManager(this)
                    adapter = SecretaryDatesAdapter(
                        this,
                        this,
                        list
                    )
                    binding.datesRv.adapter = adapter

                    if (list.isEmpty()) {
                        binding.datesRv.visibility = View.GONE
                        binding.isEmpty.visibility = View.VISIBLE
                    } else {
                        binding.datesRv.visibility = View.VISIBLE
                        binding.isEmpty.visibility = View.GONE
                    }

                    adapter!!.notifyDataSetChanged()
                } else if (res.message == "Dates not exists") {
                    binding.datesRv.visibility = View.GONE
                    binding.prog.visibility = View.GONE
                    binding.isEmpty.visibility = View.VISIBLE
                }
            } else {
                binding.prog.visibility = View.GONE
                binding.isEmpty.visibility = View.VISIBLE
                binding.datesRv.visibility = View.GONE

                Toast.makeText(this, "فشل في جلب البيانات.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (list.isNotEmpty()) {
                    val filterList = ArrayList<Date>()

                    for (item in list) {
                        if (item.ap_date.contains(s.toString())) {
                            filterList.add(item)
                        }
                    }

                    if (adapter != null) {
                        adapter!!.filterList(filterList)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.ad.setOnClickListener {
            binding.search.clearFocus()
        }

        binding.addDate.setOnClickListener {
            val i = Intent(this, Add::class.java)
            i.putExtra("idSecretary", idSecretary)
            startActivity(i)
            finish()
        }

        binding.signOut.setOnClickListener {
            dialog.showAlertDialog()
        }

        dialog.view.findViewById<MaterialButton>(R.id.btnOk).setOnClickListener {
            startActivity(Intent(this, Welcome::class.java))
            finish()
            sharedPreferencesLoginSecretary.remove("login").apply()
            sharedPreferencesLoginSecretary.remove("id").apply()
            sharedPreferencesLoginSecretary.remove("name").apply()
            sharedPreferencesLoginSecretary.remove("email").apply()
        }

        dialog.view.findViewById<MaterialButton>(R.id.btnNo).setOnClickListener {
            dialog.alertDialogDismiss()
        }
    }
}