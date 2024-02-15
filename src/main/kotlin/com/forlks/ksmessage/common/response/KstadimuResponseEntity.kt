package com.crypted.kscommon.response

import java.io.Serializable


data class KstadimuResponseEntity (
    val code: Int = 0, //int 형 코드
    val statusCode: String? = null, //상태코드
    val message: String? = null,
    val dataCount: Int? = null,
    val data: Any? = null,
) : Serializable