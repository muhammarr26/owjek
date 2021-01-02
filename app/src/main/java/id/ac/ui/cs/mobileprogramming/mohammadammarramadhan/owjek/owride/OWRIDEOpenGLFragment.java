package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride;

import android.content.Context;
import javax.microedition.khronos.egl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import javax.microedition.khronos.opengles.GL10;


public class OWRIDEOpenGLFragment extends Fragment {

    static {
        System.loadLibrary("owride");
    }

    public static native void on_surface_created();

    public static native void on_surface_changed(int width, int height);

    public static native void on_draw_frame();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = new OWRIDEOpenGL(this.getActivity());
        return view;

    }

    class OWRIDEOpenGL extends GLSurfaceView {

        public OWRIDEOpenGL(Context context) {
            super(context);
            setEGLContextClientVersion(2);
            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(new OWRIDERenderer());
        }
    }

    class OWRIDERenderer implements GLSurfaceView.Renderer {

        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            // Set the background frame color
            on_surface_created();
        }

        public void onDrawFrame(GL10 unused) {
            // Redraw background color
            on_draw_frame();
        }

        public void onSurfaceChanged(GL10 unused, int width, int height) {
            on_surface_changed(width, height);
        }
    }
}
