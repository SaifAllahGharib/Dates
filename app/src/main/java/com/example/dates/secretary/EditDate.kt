package com.example.dates.secretary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.R
import com.example.dates.databinding.ActivityEditDateBinding
import com.example.dates.repository.Repository
import com.example.dates.util.CustomDialog
import com.example.dates.util.NetworkConnection
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class EditDate : AppCompatActivity() {
    private lateinit var binding: ActivityEditDateBinding
    private lateinit var type: String
    private lateinit var status: String
    private lateinit var topicTextInput: TextInputLayout
    private lateinit var addressTextInput: TextInputLayout
    private lateinit var personTextInput: TextInputLayout
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var isConnected: NetworkConnection
    private lateinit var dialog: CustomDialog
    private lateinit var textWatcher: com.example.dates.util.TextWatcher
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val view = layoutInflater.inflate(R.layout.signup_connection, null)
        val id = intent.getStringExtra("id")!!
        val date = intent.getStringExtra("date")!!
        val time = intent.getStringExtra("time")!!
        val person = intent.getStringExtra("person")!!
        val topic = intent.getStringExtra("topic")!!
        val address = intent.getStringExtra("address")!!

        topicTextInput = binding.topic
        addressTextInput = binding.address
        personTextInput = binding.person
        isConnected = NetworkConnection(this)
        dialog = CustomDialog(this, view, R.style.CustomAlert)
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        textWatcher = com.example.dates.util.TextWatcher()

        topicTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherEditDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        addressTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherEditDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        personTextInput.editText!!.addTextChangedListener(
            textWatcher.textWatcherEditDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        binding.textTime.addTextChangedListener(
            textWatcher.textWatcherEditDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        binding.textDate.addTextChangedListener(
            textWatcher.textWatcherEditDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        binding.type.addTextChangedListener(
            textWatcher.textWatcherEditDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        binding.status.addTextChangedListener(
            textWatcher.textWatcherEditDate(
                binding.textDate,
                binding.textTime,
                topicTextInput,
                addressTextInput,
                personTextInput,
                binding.type,
                binding.status,
                binding.btnEdit,
                binding.btnEditDis
            )
        )

        binding.back.setOnClickListener {
            if (binding.prog.visibility == View.GONE) {
                startActivity(Intent(this, AddDate::class.java))
                finish()
            }
        }

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

        binding.textDate.text = date
        binding.textTime.text = time
        binding.topic.editText!!.setText(topic)
        binding.address.editText!!.setText(address)
        binding.person.editText!!.setText(person)

        binding.date.setOnClickListener {
            openDialogDate()
        }

        binding.time.setOnClickListener {
            openDialogTime()
        }

        binding.btnEdit.setOnClickListener {
            if (isConnected.isInternetAvailable() && id.toInt() != 0) {
                viewModel.updateDate(
                    id.toInt(),
                    binding.textDate.text.toString(),
                    binding.textTime.text.toString(),
                    binding.person.editText!!.text.toString(),
                    binding.topic.editText!!.text.toString(),
                    type,
                    binding.address.editText!!.text.toString(),
                    status
                )

                binding.prog.visibility = View.VISIBLE

                binding.date.isEnabled = false
                binding.time.isEnabled = false
                binding.topic.isEnabled = false
                binding.address.isEnabled = false
                binding.status.isEnabled = false
                binding.type.isEnabled = false
                binding.person.isEnabled = false
                binding.back.isEnabled = false
                binding.btnEdit.isEnabled = false
            }
        }

        viewModel.defaultResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.message == "Date updated") {
                    binding.prog.visibility = View.VISIBLE

                    Toast.makeText(this, "تم تعديل المعاد", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, AddDate::class.java))
                    finish()
                } else if (res.message == "Date not updated") {
                    binding.prog.visibility = View.VISIBLE

                    Toast.makeText(this, "لم يتم تعديل المعاد حاول لاحقا", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this, AddDate::class.java))
                    finish()
                }
            } else {
                binding.prog.visibility = View.VISIBLE

                Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, AddDate::class.java))
                finish()
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

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (
                    binding.textDate.text != "التاريخ"
                    && binding.textDate.text != "الوقت"
                    && topicTextInput.editText!!.text.toString().isNotEmpty()
                    && addressTextInput.editText!!.text.toString().isNotEmpty()
                    && personTextInput.editText!!.text.toString().isNotEmpty()
                    && binding.type.text.toString().isNotEmpty()
                    && binding.status.text.toString().isNotEmpty()
                ) {
                    binding.btnEdit.visibility = View.VISIBLE
                    binding.btnEditDis.visibility = View.GONE
                } else {
                    binding.btnEdit.visibility = View.GONE
                    binding.btnEditDis.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (binding.prog.visibility == View.GONE) {
            startActivity(Intent(this, AddDate::class.java))
            finish()
        }
    }
}