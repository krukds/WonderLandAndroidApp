package com.example.wonderland.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderland.Models.ExceptionModel
import com.example.wonderland.Models.SignupRequestModel
import com.example.wonderland.Models.UserModel
import com.example.wonderland.Models.UserRequestModel
import com.example.wonderland.authService
import com.google.gson.Gson
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val _userState = mutableStateOf(UserModel(0,"","","","",""))
    val userState: State<UserModel> = _userState

    private val _errorState = mutableStateOf("")
    val errorState = _errorState

    private val _tokenState = mutableStateOf("")
    val tokenState =_tokenState

    fun clearToken() {
        _tokenState.value = ""
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authService.login(username, password)
                _tokenState.value = response.access_token
            } catch (e: Exception) {
                _errorState.value = "Incorrect email or password"
            }

        }
    }

    fun signup(email: String,
               password: String,
               firstName: String,
               lastName: String,
               phone: String) {
        viewModelScope.launch {
            try {
                if (email == "")
                    throw Exception("Email can not be empty")
                else if (firstName == "")
                    throw Exception("Firstname can not be empty")
                else if (lastName == "")
                    throw Exception("Lastname can not be empty")
                else if (phone == "")
                    throw Exception("Phone can not be empty")
                else if (password == "")
                    throw Exception("Password can not be empty")

                val request = SignupRequestModel(email, password, firstName, lastName, phone)
                val response = authService.signup(request)
                if(response.isSuccessful) {
                    _tokenState.value = response.body()?.access_token ?: ""
                } else {
                    // Обробка помилок
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ExceptionModel::class.java)
                    _errorState.value = errorResponse?.detail ?: "Unknown error"
                }
            } catch (e: Exception) {
                _errorState.value = "Error: ${e.message}"
            }
        }
    }

    fun updateUserId (id: Int){
        _userState.value = userState.value.copy(id=id)
    }

    fun fetchUser(token: String?) {
        viewModelScope.launch {
            if (token != null && token != ""){
                try {
                    val user = authService.getMe(token)
                    _userState.value = user
                }catch(e: Exception) {
                    _userState.value = _userState.value.copy(id=-1)
                }
            } else {
                _userState.value = _userState.value.copy(id=-1)
            }
        }
    }

    fun deleteUser(token: String?) {
        viewModelScope.launch {
            try {
                authService.deleteMe(token)
            } catch (e: Exception) {
                _errorState.value = "Error: ${e.message}"
            }
        }
    }

    fun updateUser(token: String?,
                   email: String,
                   password: String,
                   firstName: String,
                   lastName: String,
                   phone: String) {
        viewModelScope.launch {
            try {
                if (email == "")
                    throw Exception("Email can not be empty")
                else if (firstName == "")
                    throw Exception("Firstname can not be empty")
                else if (lastName == "")
                    throw Exception("Lastname can not be empty")
                else if (phone == "")
                    throw Exception("Phone can not be empty")
                else if (password == "")
                    throw Exception("Password can not be empty")
                authService.updateMe(token, UserRequestModel(email, password, firstName, lastName, phone))
            } catch (e: Exception) {
                _errorState.value = "Error: ${e.message}"

            }
        }
    }
}
