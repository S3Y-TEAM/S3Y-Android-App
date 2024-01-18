package com.alexon.data.remote.interceptors

import com.alexon.core.utils.sharedPrefernces.EncryptedSharedPreference
import com.alexon.core.utils.sharedPrefernces.SharedPreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class HeadersSetupInterceptor @Inject constructor(
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val encryptedSharedPreference: EncryptedSharedPreference
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachAuthHeader = invocation
            .method()
            .annotations
            .any { it.annotationClass == Authenticated::class }

        val shouldAttachLatLng = invocation
            .method()
            .annotations
            .any { it.annotationClass == LatLng::class }

        return chain.proceed(
            chain.request()
                .newBuilder().apply {
                    addHeader("Accept-Language", sharedPreferenceHelper.appLanguage)
                    addHeader("Accept", "application/json")
                    //token
                    if (shouldAttachAuthHeader) {
                        if (encryptedSharedPreference.token.isNotEmpty()) {
                            addHeader("Authorization", encryptedSharedPreference.brearToken)
                        }
                    }
                    //latLng
                    if (shouldAttachLatLng) {
                        if (sharedPreferenceHelper.userLat != 0f && sharedPreferenceHelper.userLng != 0f) {
                            addHeader("latitude", sharedPreferenceHelper.userLat.toString())
                            addHeader("longitude", sharedPreferenceHelper.userLng.toString())
                        }
                    }
                }
                .build())
    }

}

@Target(AnnotationTarget.FUNCTION)
annotation class Authenticated

@Target(AnnotationTarget.FUNCTION)
annotation class LatLng