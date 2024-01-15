package com.alexon.core.constants

object SecretKeysUtils {

    init {
        System.loadLibrary("myapplication")
    }

    //remote server
    external fun baseUrl(): String

    //google maps
    external fun googleApiKey(): String

    //hyperPay
    external fun hyperPayApiKey(): String
    external fun hyperPayVisaMasterCardEntityId(): String
    external fun hyperPayMadaEntityId(): String

}
