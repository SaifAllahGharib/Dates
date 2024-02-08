package com.example.dates.api

import com.example.dates.model.DateResponse
import com.example.dates.model.DefaultResponse
import com.example.dates.model.ManagerFromSecretaryResponse
import com.example.dates.model.Managers
import com.example.dates.model.ManagersResponse
import com.example.dates.model.SecretaryResponse
import com.example.dates.model.UserManagerResponse
import com.example.dates.model.UserSecretaryResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("signupmanager.php")
    suspend fun signupManager(
        @Field("id_secretary") idSecretary: Int,
        @Field("id_admin") idAdmin: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserManagerResponse>

    @FormUrlEncoded
    @POST("loginmanager.php")
    suspend fun loginManager(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserManagerResponse>

    @FormUrlEncoded
    @POST("getmanager.php")
    suspend fun getManager(
        @Field("name") name: String,
    ): Response<UserManagerResponse>

    @GET("getallmanagers.php")
    suspend fun getAllManagers(): Response<ManagersResponse>

    @FormUrlEncoded
    @POST("deletemanager.php")
    suspend fun deleteManager(@Field("id") id: Int): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("editmanager.php")
    suspend fun editManager(
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("addmanager.php")
    suspend fun addManager(
        @Field("id_secretary") idSecretary: Int,
        @Field("name_secretary") nameSecretary: String,
        @Field("id_manager") idManager: Int,
        @Field("name_manager") nameManager: String
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("getallmanageraddedtosecretary.php")
    suspend fun getAllManagerToSecretary(
        @Field("id_secretary") idSecretary: Int
    ): Response<Managers>

    @FormUrlEncoded
    @POST("deletemanagertosecretary.php")
    suspend fun deleteManagerToSecretary(
        @Field("id_secretary") idSecretary: Int,
        @Field("id_manager") idManager: Int
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("deletemanagertosecretarysingel.php")
    suspend fun deleteManagerToSecretarySingle(
        @Field("id_manager") idManager: Int
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("getmanagerfromsecretary.php")
    suspend fun getManagersFromSecretary(
        @Field("id_secretary") idSecretary: Int
    ): Response<ManagerFromSecretaryResponse>

    @FormUrlEncoded
    @POST("signupsecretary.php")
    suspend fun signupSecretary(
        @Field("id_admin") idAdmin: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserSecretaryResponse>

    @FormUrlEncoded
    @POST("loginsecretary.php")
    suspend fun loginSecretary(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserSecretaryResponse>

    @FormUrlEncoded
    @POST("loginadmin.php")
    suspend fun loginAdmin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserSecretaryResponse>

    @GET("getallsecretary.php")
    suspend fun getAllSecretary(): Response<SecretaryResponse>

    @FormUrlEncoded
    @POST("deletesecretary.php")
    suspend fun deleteSecretary(@Field("id") id: Int): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("editsecretary.php")
    suspend fun editSecretary(
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("deletesecretarytosecretarysingel.php")
    suspend fun deleteSecretaryToSecretarySingle(
        @Field("id_secretary") idSecretary: Int
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("adddate.php")
    suspend fun addDate(
        @Field("ap_date") date: String,
        @Field("ap_time") time: String,
        @Field("personOrSide") person: String,
        @Field("topic") topic: String,
        @Field("insideOrOutside") inOrOut: String,
        @Field("address") address: String,
        @Field("completed") status: String,
        @Field("id_manager") idManager: Int,
        @Field("id_secretary") idSecretary: Int
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("getalldatestosecretary.php")
    suspend fun getAllDateToSecretary(@Field("id_manager") idManager: Int): Response<DateResponse>

    @FormUrlEncoded
    @POST("deletedatesromsecretary.php")
    suspend fun deleteDateFromSecretary(@Field("id") id: Int): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("updatedate.php")
    suspend fun updateDate(
        @Field("id") id: Int,
        @Field("ap_date") date: String,
        @Field("ap_time") time: String,
        @Field("personOrSide") person: String,
        @Field("topic") topic: String,
        @Field("insideOrOutside") inOrOut: String,
        @Field("address") address: String,
        @Field("completed") completed: String
    ): Response<DefaultResponse>
}