package com.crypted.kscommon.response

import java.io.Serializable

interface KsStatusCode : Serializable {
    fun code(): Int
    fun statusCode(): String?
    fun errorMessage(): String?
}