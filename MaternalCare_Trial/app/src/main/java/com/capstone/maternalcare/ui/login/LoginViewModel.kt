package com.capstone.maternalcare.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.maternalcare.data.api.main.ApiConfig
import com.capstone.maternalcare.data.model.ResponseData
import com.capstone.maternalcare.data.model.User
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.util.ResponseCallback
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun loginUser(username : String, password: String, callback: ResponseCallback){
        _isLoading.value = true
        ApiConfig.getApiService().login(username, password).enqueue(object:
            Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                val responseBody = response.body()

                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success" && responseBody.data != null){
                        saveUser(User(
                            responseBody.data.full_name,
                            responseBody.data.username,
                            responseBody.data.email,
                            responseBody.data.password,
                            responseBody.data.telephone,
                            responseBody.data.date_of_birth,
                            true,
                            responseBody.data.created_at,
                            responseBody.data.updated_at,
                            responseBody.data.id
                        ))
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                        } else {
                            callback.getCallback("User Success Login", true)
                        }
                        _isLoading.value = false
                    } else {
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                        } else {
                            callback.getCallback("User Failed Login", false)
                        }
                        _isLoading.value = false
                    }
                } else {
                    callback.getCallback("User Failed Login", false)
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback.getCallback(t.message.toString(), false)
                _isLoading.value = false
            }
        })
    }
}