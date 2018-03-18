package com.levnovikov.core_network.interceptors

import junit.framework.Assert
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Author: lev.novikov
 * Date: 16/3/18.
 */
class RequestLimitErrorInterceptorTest {

    @Volatile
    private var facedException = false

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        facedException = false
        server = MockWebServer()
    }

    @Test
    fun intercept_normal() {
        val url = server.url("/")
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(100, TimeUnit.MILLISECONDS).setBody("body 1").setBodyDelay(100, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(200, TimeUnit.MILLISECONDS).setBody("body 2").setBodyDelay(200, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 4"))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 6"))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 6"))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 6"))

        behaviourAssertion(url)
    }

    @Test
    fun intercept_limitReachedAgain() {
        val url = server.url("/")
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(100, TimeUnit.MILLISECONDS).setBody("body 1").setBodyDelay(100, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(200, TimeUnit.MILLISECONDS).setBody("body 2").setBodyDelay(200, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 4"))
        //somehow limit was reached again, handling repeat checking
        server.enqueue(MockResponse().setResponseCode(429).setBody("body 6"))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 6"))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 6"))

        behaviourAssertion(url)
    }

    @Test(expected = AssertionError::class)
    fun intercept_unsuccessfulRepeating() {
        val url = server.url("/")
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(100, TimeUnit.MILLISECONDS).setBody("body 1").setBodyDelay(100, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(200, TimeUnit.MILLISECONDS).setBody("body 2").setBodyDelay(200, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(429).setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("body 3").setBodyDelay(300, TimeUnit.MILLISECONDS))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 4"))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 6"))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 6"))
        server.enqueue(MockResponse().setResponseCode(200).setBody("body 6"))

        behaviourAssertion(url)
    }

    private fun behaviourAssertion(url: HttpUrl) {
        val client = OkHttpClient.Builder()
                .addInterceptor(RequestLimitErrorInterceptor())
                .build()

        val countDownLatch = CountDownLatch(3)
        Thread({
            try {
                println(Thread.currentThread().name + " started")
                val response = client.newCall(Request.Builder().url(url).build()).execute()
                println(Thread.currentThread().name + " ended")
                assertResponseCode(200, response.code())
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                countDownLatch.countDown()
            }
        }, "Thread 1").start()

        Thread.sleep(1000)

        Thread({
            try {
                println(Thread.currentThread().name + " started")
                val response = client.newCall(Request.Builder().url(url).build()).execute()
                println(Thread.currentThread().name + " ended")
                assertResponseCode(200, response.code())
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                countDownLatch.countDown()
            }
        }, "Thread 2").start()

        Thread.sleep(10)

        Thread({
            try {
                println(Thread.currentThread().name + " started")
                val response = client.newCall(Request.Builder().url(url).build()).execute()
                println(Thread.currentThread().name + " ended")
                assertResponseCode(200, response.code())
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                countDownLatch.countDown()
            }
        }, "Thread 3").start()

        countDownLatch.await()

        Thread.sleep(10)

        if (facedException) throw AssertionError()
    }

    private fun assertResponseCode(expected: Int, actual: Int) {
        if (expected != actual) facedException = true
        Assert.assertEquals(expected, actual)
    }

}