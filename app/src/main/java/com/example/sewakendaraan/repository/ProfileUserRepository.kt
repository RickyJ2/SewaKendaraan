package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.ResponseDataUser
import com.example.sewakendaraan.room.userRoom.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileUserRepository {
    private val _code = MutableLiveData<Int>()
    private val _msg = MutableLiveData<String>()
    private val _error = MutableLiveData<User>()
    val code: LiveData<Int>
        get() = _code
    val msg: LiveData<String>
        get() = _msg
    val error: LiveData<User>
        get() = _error

    private val _readLoginData = MutableLiveData<User>()
    val readLoginData: LiveData<User>
        get() = _readLoginData

    init {
        _readLoginData.value = null
        resetVal()
    }
    private fun resetVal(){
        _code.value = null
        _msg.value = ""
        _error.value = null
    }
    fun userData(userId: Int){
        resetVal()
        RClient.instances.userData(userId).enqueue(object:
            Callback<ResponseDataUser> {
            override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _readLoginData.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                _code.value = 0
            }
        })
    }
    fun updateUser(user: User){
        resetVal()
        RClient.instances.updateUser(user.id, user.username, user.email, user.password, user.dateOfBirth, user.handphone).enqueue(
            object: Callback<ResponseDataUser> {
                override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                    if(response.isSuccessful){
                        _msg.value = response.body()?.msg.toString()
                        response.body().also { _readLoginData.value = it?.data }
                        _code.value = response.code()
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        _code.value = response.code()
                        if(_code.value == 400 && jsonObj.has("error")){
                            val jsonError = jsonObj.getJSONObject("error")
                            _error.value = User(
                                0,
                                if(jsonError.has("username")) jsonError.getJSONArray("username")[0].toString() else "",
                                if(jsonError.has("email")) jsonError.getJSONArray("email")[0].toString() else "",
                                if(jsonError.has("password")) jsonError.getJSONArray("password")[0].toString() else "",
                                if(jsonError.has("dateOfBirth")) jsonError.getJSONArray("dateOfBirth")[0].toString() else "",
                                if(jsonError.has("handphone")) jsonError.getJSONArray("handphone")[0].toString() else ""
                            )
                        }else {
                            _msg.value = jsonObj.getString("message")
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                    _code.value = 0
                }
            })
    }
}