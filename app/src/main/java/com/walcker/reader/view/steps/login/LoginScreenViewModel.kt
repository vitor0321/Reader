package com.walcker.reader.view.steps.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.walcker.reader.domain.model.UserUI
import com.walcker.reader.view.utils.uiState.UiStateLogin
import kotlinx.coroutines.launch
import java.util.Date

internal class LoginScreenViewModel : StateScreenModel<UiStateLogin>(UiStateLogin.Loading) {

    private val auth: FirebaseAuth = Firebase.auth

    fun signInWithEmailAndPassword(email: String, password: String) = coroutineScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mutableState.value = UiStateLogin.SuccessToHome
                    } else {
                        val error = Exception()
                        mutableState.value = UiStateLogin.Error(error)
                    }
                }
        } catch (error: Exception) {
            mutableState.value = UiStateLogin.Error(error)
        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        mutableState.value = UiStateLogin.SuccessToLogin
                    } else {
                        mutableState.value = UiStateLogin.Error(Exception())
                    }
                }
        } catch (error: Exception) {
            mutableState.value = UiStateLogin.Error(error)
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = UserUI(
            id = null,
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = ""
        ).toMap()

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .add(user)
    }
}
