package com.graduation.core.keys

object SecretKeysUtils {

    init {
        System.loadLibrary("myapplication")
    }

    //remote server
    external fun baseUrl(): String



}
