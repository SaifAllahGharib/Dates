package com.example.dates.repository

import com.example.dates.api.RetrofitInstance
import com.example.dates.model.DateResponse
import com.example.dates.model.DefaultResponse
import com.example.dates.model.ManagersResponse
import com.example.dates.model.SecretaryManagers
import com.example.dates.model.SecretaryResponse
import com.example.dates.model.UserManagerResponse
import com.example.dates.model.UserSecretaryResponse
import retrofit2.Response

class Repository {
    suspend fun signupManager(
        idSecretary: Int,
        idAdmin: Int,
        name: String,
        email: String,
        password: String
    ): Response<UserManagerResponse> {
        return RetrofitInstance.api.signupManager(idSecretary, idAdmin, name, email, password)
    }

    suspend fun loginManager(
        email: String,
        password: String
    ): Response<UserManagerResponse> {
        return RetrofitInstance.api.loginManager(email, password)
    }

    suspend fun getManager(name: String): Response<UserManagerResponse> {
        return RetrofitInstance.api.getManager(name)
    }

    suspend fun getAllManagers(): Response<ManagersResponse> {
        return RetrofitInstance.api.getAllManagers()
    }

    suspend fun deleteManager(id: Int): Response<DefaultResponse> {
        return RetrofitInstance.api.deleteManager(id)
    }

    suspend fun editManager(
        id: Int,
        name: String,
        email: String,
        password: String
    ): Response<DefaultResponse> {
        return RetrofitInstance.api.editManager(id, name, email, password)
    }

    suspend fun addManager(
        idSecretary: Int,
        nameSecretary: String,
        idManager: Int,
        nameManager: String
    ): Response<DefaultResponse> {
        return RetrofitInstance.api.addManager(idSecretary, nameSecretary, idManager, nameManager)
    }

    suspend fun signupSecretary(
        idAdmin: Int,
        name: String,
        email: String,
        password: String
    ): Response<UserSecretaryResponse> {
        return RetrofitInstance.api.signupSecretary(idAdmin, name, email, password)
    }

    suspend fun loginSecretary(
        email: String,
        password: String
    ): Response<UserSecretaryResponse> {
        return RetrofitInstance.api.loginSecretary(email, password)
    }

    suspend fun getSecretaryManagers(
        idSecretary: Int,
    ): Response<SecretaryManagers> {
        return RetrofitInstance.api.getSecretaryManagers(idSecretary)
    }

    suspend fun loginAdmin(
        email: String,
        password: String
    ): Response<UserSecretaryResponse> {
        return RetrofitInstance.api.loginAdmin(email, password)
    }

    suspend fun getAllSecretary(): Response<SecretaryResponse> {
        return RetrofitInstance.api.getAllSecretary()
    }

    suspend fun deleteSecretary(id: Int): Response<DefaultResponse> {
        return RetrofitInstance.api.deleteSecretary(id)
    }

    suspend fun editSecretary(
        id: Int,
        name: String,
        email: String,
        password: String
    ): Response<DefaultResponse> {
        return RetrofitInstance.api.editSecretary(id, name, email, password)
    }

    suspend fun addDate(
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
    ): Response<DefaultResponse> {
        return RetrofitInstance.api.addDate(
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
    }

    suspend fun getAllDateToSecretary(idManager: Int): Response<DateResponse> {
        return RetrofitInstance.api.getAllDateToSecretary(idManager)
    }

    suspend fun deleteDateFromSecretary(id: Int): Response<DefaultResponse> {
        return RetrofitInstance.api.deleteDateFromSecretary(id)
    }

    suspend fun updateDate(
        id: Int,
        date: String,
        time: String,
        person: String,
        topic: String,
        inOrOut: String,
        address: String,
        completed: String,
        note: String
    ): Response<DefaultResponse> {
        return RetrofitInstance.api.updateDate(
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
    }
}