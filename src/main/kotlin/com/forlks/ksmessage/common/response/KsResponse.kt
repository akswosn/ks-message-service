package com.crypted.kscommon.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

enum class KsResponse(private val code: Int, private val statusCode: String, private val errorMessage: String) :
    KsStatusCode {
    //statusCode 는 우선 임의로 정의 나중에는 API Code로 전달해 주도록
    KS_SUCCESS(200, "KS1000", "SUCCESS"), KS_INVALID_PARAM(400, "KS4010", "Invalid Parameter"), KS_INVALID_CONTENT_TYPE(
        400,
        "KS4011",
        "Content-type invalid"
    ),
    KS_AUTHORIZED_NOT_FOUND(401, "KS4020", "Not Found Authorize"), KS_UN_AUTHORIZED(
        401,
        "KS4021",
        "Invalid Authorize"
    ),
    KS_NOT_FOUND_URI(404, "KS4022", "Not Found URI"), KS_METHOD_NOT_ALLOWED(
        405,
        "KS4023",
        "Not Allow Method"
    ),
    KS_CONFLICT(409, "KS4024", "Request could not be completed "), KS_NOT_EXT_SERVICE(
        409,
        "KS4101",
        "External Service does not exist"
    ),
    KS_EXT_CONNECT_EXIST(401, "KS4102", "Connected User exist"), KS_UN_EXT_CONNECTED(
        401,
        "KS4103",
        "Not Connected User"
    ),
    KS_SERVER_INTERNAL_ERROR(500, "KS5001", "Interval Server Error"), KS_EXTRA_DOMAIN_NOT_WORK(
        504,
        "KS5002",
        "NOT_ALLOW Extra Domain"
    );

    override fun code(): Int {
        return this.code
    }

    override fun statusCode(): String {
        return statusCode
    }

    override fun errorMessage(): String {
        return errorMessage
    }

    //default
    fun <T> toResponse(): ResponseEntity<KsResponseEntity> {
        return ResponseEntity(
            KsResponseEntity(
                code = this.code,
                message = errorMessage,
                statusCode = statusCode,
                dataCount = 0
            ), HttpStatus.valueOf(this.code)
        )
    }

    //성공 데이터 바인딩용
    fun <T> toDataResponse(data: T): ResponseEntity<KsResponseEntity> {
        return ResponseEntity(
            KsResponseEntity(
                code = this.code,
                message = errorMessage,
                statusCode = statusCode,
                data = data,
                dataCount = 1
            ), HttpStatus.valueOf(this.code)
        )
    }

    fun <T> toDataResponse(data: T, count: Int?): ResponseEntity<KsResponseEntity> {
        return ResponseEntity(
            KsResponseEntity(
                code = this.code,
                message = errorMessage,
                statusCode = statusCode,
                data = data,
                dataCount = count
            ), HttpStatus.valueOf(this.code)
        )
    }

    //error msg 세팅용
    fun <T> toMsgResponse(errMsg: String?): ResponseEntity<KsResponseEntity> {

        return ResponseEntity(
            KsResponseEntity(
                code = this.code,
                message = errMsg,
                statusCode = statusCode,
                dataCount = 0
            ), HttpStatus.valueOf(this.code)
        )
    }
}
