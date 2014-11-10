#include "com_morkva_engine_Controller.h"

JNIEXPORT jstring JNICALL Java_com_morkva_engine_Controller_getContent
  (JNIEnv * env, jobject obj)
  {
    return (* env)->NewStringUTF(env, "Sup, buddy");
  }
