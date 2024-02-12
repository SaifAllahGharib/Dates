package com.example.dates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dates.model.DateResponse
import com.example.dates.model.DefaultResponse
import com.example.dates.model.ManagersResponse
import com.example.dates.model.SecretaryManagers
import com.example.dates.model.SecretaryResponse
import com.example.dates.model.UserManagerResponse
import com.example.dates.model.UserSecretaryResponse
import com.example.dates.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {
    val defaultResponse: MutableLiveData<Response<DefaultResponse>> =
        MutableLiveData()
    val updateAndDeleteManagerResponse: MutableLiveData<Response<DefaultResponse>> =
        MutableLiveData()
    val updateAndDeleteSecretaryResponse: MutableLiveData<Response<DefaultResponse>> =
        MutableLiveData()
    val userSecretaryResponse: MutableLiveData<Response<UserSecretaryResponse>> = MutableLiveData()
    val userManagerResponse: MutableLiveData<Response<UserManagerResponse>> = MutableLiveData()
    val managersResponse: MutableLiveData<Response<ManagersResponse>> = MutableLiveData()
    val secretaryResponse: MutableLiveData<Response<SecretaryResponse>> = MutableLiveData()
    val datesResponse: MutableLiveData<Response<DateResponse>> =
        MutableLiveData()
    val secretaryManagersResponse: MutableLiveData<Response<SecretaryManagers>> =
        MutableLiveData()


    fun signupManager(
        idSecretary: Int,
        idAdmin: Int,
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val response = repository.signupManager(idSecretary, idAdmin, name, email, password)
            userManagerResponse.value = response
        }
    }

    fun loginManager(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.loginManager(email, password)
            userManagerResponse.value = response
        }
    }

    fun getManager(name: String) {
        viewModelScope.launch {
            val response = repository.getManager(name)
            userManagerResponse.value = response
        }
    }

    fun getAllManagers() {
        viewModelScope.launch {
            val response = repository.getAllManagers()
            managersResponse.value = response
        }
    }

    fun deleteManager(id: Int) {
        viewModelScope.launch {
            val response = repository.deleteManager(id)
            updateAndDeleteManagerResponse.value = response
        }
    }

    fun editManager(id: Int, name: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.editManager(id, name, email, password)
            updateAndDeleteManagerResponse.value = response
        }
    }

    fun signupSecretary(
        idAdmin: Int,
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val response =
                repository.signupSecretary(idAdmin, name, email, password)
            userSecretaryResponse.value = response
        }
    }

    fun loginSecretary(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.loginSecretary(email, password)
            userSecretaryResponse.value = response
        }
    }

    fun getSecretaryManagers(idSecretary: Int) {
        viewModelScope.launch {
            val response = repository.getSecretaryManagers(idSecretary)
            secretaryManagersResponse.value = response
        }
    }

    fun loginAdmin(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.loginAdmin(email, password)
            userSecretaryResponse.value = response
        }
    }

    fun getAllSecretary() {
        viewModelScope.launch {
            val response = repository.getAllSecretary()
            secretaryResponse.value = response
        }
    }

    fun deleteSecretary(id: Int) {
        viewModelScope.launch {
            val response = repository.deleteSecretary(id)
            updateAndDeleteSecretaryResponse.value = response
        }
    }

    fun editSecretary(id: Int, name: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.editSecretary(id, name, email, password)
            updateAndDeleteSecretaryResponse.value = response
        }
    }

    fun addDate(
        date: String,
        time: String,
        person: String,
        topic: String,
        inOrOut: String,
        address: String,
        status: String,
        note: String,
        idManager: Int,
        idSecretary: Int
    ) {
        viewModelScope.launch {
            val response = repository.addDate(
                date,
                time,
                person,
                topic,
                inOrOut,
                address,
                status,
                note,
                idManager,
                idSecretary
            )
            defaultResponse.value = response
        }
    }

    fun getAllDateToSecretary(idManager: Int) {
        viewModelScope.launch {
            val response = repository.getAllDateToSecretary(idManager)
            datesResponse.value = response
        }
    }

    fun deleteDateFromSecretary(id: Int) {
        viewModelScope.launch {
            val response = repository.deleteDateFromSecretary(id)
            defaultResponse.value = response
        }
    }

    fun updateDate(
        id: Int,
        date: String,
        time: String,
        person: String,
        topic: String,
        inOrOut: String,
        address: String,
        completed: String,
        note: String
    ) {
        viewModelScope.launch {
            val response =
                repository.updateDate(
                    id,
                    date,
                    time,
                    person,
                    topic,
                    inOrOut,
                    address,
                    completed,
                    note
                )
            defaultResponse.value = response
        }
    }

    fun getDailyDate() {
        viewModelScope.launch {
            val response = repository.getDailyDate()
            datesResponse.value = response
        }
    }

    fun getWeeklyDate() {
        viewModelScope.launch {
            val response = repository.getWeeklyDate()
            datesResponse.value = response
        }
    }

    fun getAllDate() {
        viewModelScope.launch {
            val response = repository.getAllDate()
            datesResponse.value = response
        }
    }
}