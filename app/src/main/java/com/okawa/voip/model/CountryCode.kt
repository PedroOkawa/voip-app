package com.okawa.voip.model

data class CountryCode(val code: Int, val region: String, val name: String) {

    override fun toString(): String {
        return name
    }

}