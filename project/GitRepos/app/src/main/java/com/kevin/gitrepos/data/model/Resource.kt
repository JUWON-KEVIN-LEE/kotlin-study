package com.kevin.gitrepos.data.model

/**
 * 데이터와 함께 데이터 로딩 상태를 담을 제네릭 클래스
 * Created by quf93 on 2018-04-17.
 */
data class Resource<out T> (val status: Status = Status.SUCCESS, val data: T? = null)