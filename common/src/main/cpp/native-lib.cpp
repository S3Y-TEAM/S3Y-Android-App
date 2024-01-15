#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_alexon_common_constants_SecretKeysUtils_baseUrl(JNIEnv *env, jobject thiz) {
    std::string base_url = "https://tavolo.alexon.work/api/";

    return env->NewStringUTF(base_url.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alexon_common_constants_SecretKeysUtils_googleApiKey(JNIEnv *env, jobject thiz) {
    std::string googleApiKey = "AIzaSyBlRyjrVDFE3Ry_wivw70bqbH6VYccL9n0";

    return env->NewStringUTF(googleApiKey.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alexon_common_constants_SecretKeysUtils_hyperPayApiKey(JNIEnv *env, jobject thiz) {
    std::string hyperPayApiKey = "OGFjN2E0Y2E4YmYzZjQ2YzAxOGJmNzQ3Y2ZiMTA1MzB8cjVIalJhYmZ3cTJLVEZHdA==";

    return env->NewStringUTF(hyperPayApiKey.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alexon_common_constants_SecretKeysUtils_hyperPayVisaMasterCardEntityId(JNIEnv *env, jobject thiz) {
    std::string hyperPayVisaMasterCardEntityId = "8ac7a4ca8bf3f46c018bf748649b0535";

    return env->NewStringUTF(hyperPayVisaMasterCardEntityId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alexon_common_constants_SecretKeysUtils_hyperPayMadaEntityId(JNIEnv *env, jobject thiz) {
    std::string hyperPayMadaEntityId = "8ac7a4ca8bf3f46c018bf7491330053b";

    return env->NewStringUTF(hyperPayMadaEntityId.c_str());
}

