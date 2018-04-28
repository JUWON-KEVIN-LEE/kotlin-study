package com.kevin.dpkotlin

/**
 * Created by quf93 on 2018-04-29.
 */
sealed class AuthorizationState

class Unauthorized : AuthorizationState()

class Authorized(val userName: String) : AuthorizationState()

class AuthorizationPresenter {

    private var state: AuthorizationState = Unauthorized()

    fun login(userLogin: String) {
        state = Authorized(userLogin)
    }

    fun logout() {
        state = Unauthorized()
    }

    // 이 경우 새롭게 초기화해서 return 하는 건지?
    // val isAuthorized = false > login > 새로운 val isAuthorized = true 를 반환하는걸까?
    private val isAuthorized: Boolean
    get() = when(state) {
        is Authorized -> true
        is Unauthorized -> false
    }

    private val userLogin: String
    get() = when(state) {
        is Authorized -> (state as Authorized).userName
        is Unauthorized -> "Unknown"
    }

    override fun toString() = "User $userLogin is logged in : $isAuthorized"
}

