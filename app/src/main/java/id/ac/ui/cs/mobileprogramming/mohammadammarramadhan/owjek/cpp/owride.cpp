#include <jni.h>
#include <GLES2/gl2.h>

void on_surface_created() {
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
}

void on_surface_changed(jint width, jint height) {
    glViewport(0, 0, width, height);
}

void on_draw_frame() {
    glClear(GL_COLOR_BUFFER_BIT);
}

extern "C" JNIEXPORT void JNICALL Java_id_ac_ui_cs_mobileprogramming_mohammadammarramadhan_owjek_owride_OWRIDEOpenGLFragment_on_1draw_1frame(
        JNIEnv * env,
        jclass clazz
) {
  on_draw_frame();
}

extern "C" JNIEXPORT void JNICALL Java_id_ac_ui_cs_mobileprogramming_mohammadammarramadhan_owjek_owride_OWRIDEOpenGLFragment_on_1surface_1changed(
        JNIEnv * env,
        jclass clazz,
        jint width,
        jint height
) {
    on_surface_changed(width, height);
}

extern "C" JNIEXPORT void JNICALL Java_id_ac_ui_cs_mobileprogramming_mohammadammarramadhan_owjek_owride_OWRIDEOpenGLFragment_on_1surface_1created(
        JNIEnv * env,
        jclass clazz
) {
    on_surface_created();
}