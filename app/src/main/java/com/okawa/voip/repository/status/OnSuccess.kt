package com.okawa.voip.repository.status

abstract class OnSuccess<T>(data: T) : Result() {

    private val data: T = data

    fun getData() = data
}