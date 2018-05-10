package com.lichao.chaovr.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.lichao.chaovr.Panorama.GLPanorama;
import com.lichao.chaovr.R;

/**
 * OpenGL 显示全景图片
 */
public class OpenGLActivity extends AppCompatActivity {

    private GLPanorama mGLPanorama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gl);

        mGLPanorama = findViewById(R.id.mGLPanorama);
        //传入全景图片
        mGLPanorama.setGLPanorama(R.drawable.imggugong);
    }
}
