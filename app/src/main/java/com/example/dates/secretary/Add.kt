package com.example.dates.secretary

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.dates.databinding.ActivityAddBinding
import com.example.dates.repository.Repository
import com.example.dates.util.CustomDialog
import com.example.dates.util.NetworkConnection
import com.example.dates.util.TextWatcher
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class Add : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var type: String
    private lateinit var status: String
    private lateinit var manager: String
    private lateinit var topicTextInput: TextInputLayout
    private lateinit var addressTextInput: TextInputLayout
    private lateinit var personTextInput: TextInputLayout
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var isConnected: NetworkConnection
    private lateinit var dialog: CustomDialog
    private lateinit var managers: ArrayList<String>
    private lateinit var view: View
    private lateinit var textWatcher: TextWatcher
    private var idManager: Int = 0
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idSecretary = intent.getIntExtra("idSecretary", 0)

        topicTextInput = binding.topic
        addressTextInput = binding.address
        personTextInput = binding.person
        managers = ArrayList()
        isConnected = NetworkConnection(this)
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        view = layoutInflater.inflate(R.layout.signup_connection, null)
        dialog = CustomDialog(this, view, R.style.CustomAlert)
        textWatcher = TextWatcher()

        topicTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherAddDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.manager,
                binding.btnAdd,
                binding.btnAddDis
            )
        )

        addressTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherAddDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.manager,
                binding.btnAdd,
                binding.btnAddDis
            )
        )

        personTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherAddDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.manager,
                binding.btnAdd,
                binding.btnAddDis
            )
        )

        binding.textTime.addTextChangedListener(
            textWatcher.textWatcherAddDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.manager,
                binding.btnAdd,
                binding.btnAddDis
            )
        )

        binding.textDate.addTextChangedListener(
            textWatcher.textWatcherAddDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.manager,
                binding.btnAdd,
                binding.btnAddDis
            )
        )

        binding.type.addTextChangedListener(
            textWatcher.textWatcherAddDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.manager,
                binding.btnAdd,
                binding.btnAddDis
            )
        )

        binding.status.addTextChangedListener(
            textWatcher.textWatcherAddDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.manager,
                binding.btnAdd,
                binding.btnAddDis
            )
        )

        binding.manager.addTextChangedListener(
            textWatcher.textWatcherAddDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.manager,
                binding.btnAdd,
                binding.btnAddDis
            )
        )


        binding.type.setAdapter(
            ArrayAdapter(
                this,
                R.layout.dropdown_item,
                arrayOf("خارج الهيئه", "داخل الهيئه")
            )
        )

        binding.type.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_dropdown_menu,
                null
            )
        )

        binding.type.setOnItemClickListener { parent, _, position, _ ->
            type = parent.getItemAtPosition(position).toString()
        }

        binding.status.setAdapter(
            ArrayAdapter(
                this,
                R.layout.dropdown_item,
                arrayOf("اكتمل", "قيد الانتظار", "تم الالغاء")
            )
        )

        binding.status.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_dropdown_menu,
                null
            )
        )

        binding.status.setOnItemClickListener { parent, _, position, _ ->
            status = parent.getItemAtPosition(position).toString()
        }

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

            viewModel.getManager(manager)
        }

        viewModel.userManagerResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.message == "User exists.") {
                    idManager = res.user[0].id
//                    viewModel.getAllDateToSecretary(idManager)
                } else if (res.message == "User not exists.") {
                    Toast.makeText(this, "هذا المدير غير متاح", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, AddDate::class.java))
            finish()
        }

        binding.date.setOnClickListener {
            openDialogDate()
        }

        binding.time.setOnClickListener {
            openDialogTime()
        }

        binding.btnAdd.setOnClickListener {
            val time = binding.textTime.text.toString()
            val person = personTextInput.editText!!.text.toString()
            val topic = topicTextInput.editText!!.text.toString()
            val address = addressTextInput.editText!!.text.toString()
            val note: String = binding.note.text.toString()

            if (isConnected.isInternetAvailable()) {
                if (idManager != 0 && idSecretary != 0) {
                    viewModel.addDate(
                        "${this.year}-${this.month + 1}-${this.day}",
                        time,
                        person,
                        topic,
                        type,
                        address,
                        status,
                        note,
                        idManager,
                        idSecretary
                    )

                    binding.prog.visibility = View.VISIBLE

                    binding.textDate.isEnabled = false
                    binding.textTime.isEnabled = false
                    binding.note.isEnabled = false
                    binding.type.isEnabled = false
                    binding.status.isEnabled = false
                    binding.manager.isEnabled = false
                    binding.topic.isEnabled = false
                    binding.address.isEnabled = false
                    binding.person.isEnabled = false
                    binding.back.isEnabled = false
                    binding.btnAdd.isEnabled = false
                }

            } else {
                dialog.view.findViewById<TextView>(R.id.textError).setText(R.string.no_connection)
                dialog.showAlertDialog()
            }
        }

        viewModel.defaultResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!
                if (res.message == "Date added.") {
                    binding.prog.visibility = View.GONE

                    startActivity(Intent(this, AddDate::class.java))
                    finish()

                    Toast.makeText(this, "تم اضافه المعاد.", Toast.LENGTH_SHORT).show()
                } else if (res.message == "Date not added") {
                    binding.prog.visibility = View.GONE

                    startActivity(Intent(this, AddDate::class.java))
                    finish()

                    Toast.makeText(this, "فشل اضافه المعاد.", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.prog.visibility = View.GONE

                startActivity(Intent(this, AddDate::class.java))
                finish()

                Toast.makeText(this, "حدث خطأ.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun openDialogDate() {
        val picker =
            MaterialDatePicker
                .Builder
                .datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.CustomMaterialDatePickerTheme)
                .build()

        picker.addOnPositiveButtonClickListener {
            val selectedDate = Calendar.getInstance()
            selectedDate.timeInMillis = it

            binding.textDate.text =
                "${selectedDate.get(Calendar.YEAR)}/${selectedDate.get(Calendar.MONTH) + 1}/${
                    selectedDate.get(Calendar.DAY_OF_MONTH)
                }"

            this.year = selectedDate.get(Calendar.YEAR)
            this.month = selectedDate.get(Calendar.MONTH)
            this.day = selectedDate.get(Calendar.DAY_OF_MONTH)
        }

        picker.show(supportFragmentManager, picker.toString())
    }

    @SuppressLint("SetTextI18n")
    private fun openDialogTime() {
        val calendar = Calendar.getInstance()

        val picker = MaterialTimePicker.Builder()
            .setTheme(R.style.CustomMaterialTimePickerTheme)
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .build()

        picker.addOnPositiveButtonClickListener {
            val hourOfDay = picker.hour
            val minute = picker.minute

            binding.textTime.text = "$hourOfDay:$minute"

            this.hour = hourOfDay
            this.minute = minute
        }

        picker.show(supportFragmentManager, picker.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (binding.prog.visibility == View.GONE) {
            startActivity(Intent(this, AddDate::class.java))
            finish()
        }
    }
}