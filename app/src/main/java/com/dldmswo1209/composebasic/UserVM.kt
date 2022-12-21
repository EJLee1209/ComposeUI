package com.dldmswo1209.composebasic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserVM: ViewModel() {

    val usersFlow = MutableStateFlow<List<User>>(listOf())

    init{
        Log.d("testt", "UserVm() init called ")
        viewModelScope.launch {
            kotlin.runCatching {
                // 에러 발생 잡기 위한 runCatching 블럭
                UserRepo.fetchUsers()
            }.onSuccess { fetchedUsers ->
                Log.d("testt", "UserVM() onSuccess ")
                usersFlow.value = fetchedUsers
            }.onFailure {
                Log.d("testt", "UserVM() onFailure ")
            }
        }
    }
}