package com.example.dates.util

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class TextWatcher {

    fun textWatcherAdminLogin(
        emailTextInput: TextInputLayout,
        passTextInput: TextInputLayout,
        btnLogin: MaterialButton,
        btnLoginDis: MaterialButton
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (emailTextInput.editText!!.text.toString()
                        .isNotEmpty() && passTextInput.editText!!.text.toString().isNotEmpty()
                ) {
                    btnLogin.visibility = View.VISIBLE
                    btnLoginDis.visibility = View.GONE
                } else {
                    btnLogin.visibility = View.GONE
                    btnLoginDis.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    fun textWatcherAddManager(
        manager: AutoCompleteTextView,
        btnAdd: MaterialButton,
        btnAddDis: MaterialButton
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (manager.text.toString().isNotEmpty()) {
                    btnAdd.visibility = View.VISIBLE
                    btnAddDis.visibility = View.GONE
                } else {
                    btnAdd.visibility = View.GONE
                    btnAddDis.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    fun textWatcherEditManagerAndSecretary(
        nameTextInput: TextInputLayout,
        emailTextInput: TextInputLayout,
        passTextInput: TextInputLayout,
        btnEdit: MaterialButton,
        btnEditDis: MaterialButton
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (nameTextInput.editText!!.text.toString()
                        .isNotEmpty() && emailTextInput.editText!!.text.toString()
                        .isNotEmpty() && passTextInput.editText!!.text.toString()
                        .isNotEmpty()
                ) {
                    btnEdit.visibility = View.VISIBLE
                    btnEditDis.visibility = View.GONE
                } else {
                    btnEdit.visibility = View.GONE
                    btnEditDis.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    fun textWatcherCreateAccounts(
        nameTextInput: TextInputLayout,
        emailTextInput: TextInputLayout,
        passTextInput: TextInputLayout,
        type: AutoCompleteTextView,
        btnSignup: MaterialButton,
        btnSignupDis: MaterialButton
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (nameTextInput.editText!!.text.toString()
                        .isNotEmpty() && emailTextInput.editText!!.text.toString()
                        .isNotEmpty() && passTextInput.editText!!.text.toString()
                        .isNotEmpty() && type.text.toString().isNotEmpty()
                ) {
                    btnSignup.visibility = View.VISIBLE
                    btnSignupDis.visibility = View.GONE
                } else {
                    btnSignup.visibility = View.GONE
                    btnSignupDis.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
    }

    fun textWatcherAddDate(
        textDate: TextView,
        textTime: TextView,
        topicTextInput: TextInputLayout,
        addressTextInput: TextInputLayout,
        personTextInput: TextInputLayout,
        type: AutoCompleteTextView,
        status: AutoCompleteTextView,
        manager: AutoCompleteTextView,
        btnAdd: MaterialButton,
        btnAddDis: MaterialButton
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (
                    textDate.text != "التاريخ"
                    && textTime.text != "الوقت"
                    && topicTextInput.editText!!.text.toString().isNotEmpty()
                    && addressTextInput.editText!!.text.toString().isNotEmpty()
                    && personTextInput.editText!!.text.toString().isNotEmpty()
                    && type.text.toString().isNotEmpty()
                    && status.text.toString().isNotEmpty()
                    && manager.text.toString().isNotEmpty()
                ) {
                    btnAdd.visibility = View.VISIBLE
                    btnAddDis.visibility = View.GONE
                } else {
                    btnAdd.visibility = View.GONE
                    btnAddDis.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    fun textWatcherEditDate(
        textDate: TextView,
        textTime: TextView,
        topicTextInput: TextInputLayout,
        addressTextInput: TextInputLayout,
        personTextInput: TextInputLayout,
        type: AutoCompleteTextView,
        status: AutoCompleteTextView,
        btnAdd: MaterialButton,
        btnAddDis: MaterialButton
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (
                    textDate.text != "التاريخ"
                    && textTime.text != "الوقت"
                    && topicTextInput.editText!!.text.toString().isNotEmpty()
                    && addressTextInput.editText!!.text.toString().isNotEmpty()
                    && personTextInput.editText!!.text.toString().isNotEmpty()
                    && type.text.toString().isNotEmpty()
                    && status.text.toString().isNotEmpty()
                ) {
                    btnAdd.visibility = View.VISIBLE
                    btnAddDis.visibility = View.GONE
                } else {
                    btnAdd.visibility = View.GONE
                    btnAddDis.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    fun textWatcherLogin(
        emailTextInput: TextInputLayout,
        passTextInput: TextInputLayout,
        btnLogin: MaterialButton,
        btnLoginDis: MaterialButton
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (emailTextInput.editText!!.text.toString()
                        .isNotEmpty() && passTextInput.editText!!.text.toString().isNotEmpty()
                ) {
                    btnLogin.visibility = View.VISIBLE
                    btnLoginDis.visibility = View.GONE
                } else {
                    btnLogin.visibility = View.GONE
                    btnLoginDis.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
    }
}