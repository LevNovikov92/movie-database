package com.levnovikov.core_network

/**
 * Author: lev.novikov
 * Date: 16/3/18.
 */
enum class ResponseCode(val code: Int) {
    REQUEST_LIMIT_ERROR(429),
    SUCCESS(200);

    fun equals(code: Int) = this.code == code
}