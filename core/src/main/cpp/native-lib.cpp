#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alexon_core_keys_SecretKeysUtils_baseUrl(JNIEnv *env, jobject thiz) {
    std::string base_url = "https://tavolo.alexon.work/api/";

    return env->NewStringUTF(base_url.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_graduation_core_keys_SecretKeysUtils_baseUrl(JNIEnv *env, jobject thiz) {

    std::string base_url = "https://s3y.onrender.com/api/v1/";

    return env->NewStringUTF(base_url.c_str());

}


