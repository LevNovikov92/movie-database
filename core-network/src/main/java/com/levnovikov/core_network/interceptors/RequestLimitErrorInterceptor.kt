package com.levnovikov.core_network.interceptors

import com.levnovikov.core_network.ResponseCode
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 *
 * RequestLimitErrorInterceptor will repeat requests with delay in case
 * if 429 will be returned and limit will be reached.
 * If api limit was reached only one thread will repeat with delay, other threads will wait
 *
 * Current api limit - 40 requests per 10 sec.
 * 4 times * 3 seconds > 10 sec. It will guarantee required delay.
 */
private const val MAX_REPEAT = 4
private const val DELAY_TIME_MILLIS: Long = 3000 //3 seconds

class RequestLimitErrorInterceptor @Inject constructor() : Interceptor {

    @Volatile
    private var lastResponseCode = ResponseCode.SUCCESS.code

    override fun intercept(chain: Interceptor.Chain): Response = handleResponse(chain, chain.request())

    private fun handleResponse(chain: Interceptor.Chain, request: Request): Response {
        val response = chain.proceed(request)
        if (ResponseCode.REQUEST_LIMIT_ERROR.equals(response.code())) {
            lastResponseCode = ResponseCode.REQUEST_LIMIT_ERROR.code
            //if api limit was reached only one thread will repeat with delay, other threads will wait
            synchronized(this) {
                //repeat without delay if limit was restored while current thread was waiting
                //otherwise try to repeat with delay
                return if (!ResponseCode.REQUEST_LIMIT_ERROR.equals(lastResponseCode)) {
                    val result = chain.proceed(request)
                    System.out.println("Handled in thread ${Thread.currentThread().name}, code: ${result.code()}")
                    //if limit was reached again repeat whole handling
                    if (ResponseCode.REQUEST_LIMIT_ERROR.equals(result.code())) handleResponse(chain, request) else result
                } else {
                    for (i in 0 until MAX_REPEAT) { //repeat several times
                        Thread.sleep(DELAY_TIME_MILLIS) //waiting for 3 seconds and repeat, all other requests will be blocked
                        val result = chain.proceed(request)
                        System.out.println("Handled in thread ${Thread.currentThread().name}, code: ${result.code()}")
                        if (!ResponseCode.REQUEST_LIMIT_ERROR.equals(result.code())) {
                            lastResponseCode = result.code()
                            return result
                        }
                    }
                    response //go with first response if repeating was not successful
                }
            }
        }
        return response
    }
}