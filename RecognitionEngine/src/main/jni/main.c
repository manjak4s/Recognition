#include "main.h"

JNIEXPORT jstring JNICALL Java_com_mayer_solution_processor_OCROperator_getContent(JNIEnv * env, jobject obj)
{
return (* env)->NewStringUTF(env, "Sup, buddy");
}
