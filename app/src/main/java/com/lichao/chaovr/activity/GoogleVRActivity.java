package com.lichao.chaovr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.lichao.chaovr.R;
import com.lichao.chaovr.activity.AboutActivity;
import com.lichao.chaovr.adapter.PanoramaImageAdapter;
import com.lichao.chaovr.model.ModelUtil;
import com.lichao.chaovr.model.PanoramaImageModel;
import com.lichao.chaovr.utils.ImageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class GoogleVRActivity extends AppCompatActivity {

    private ImageView ivMine;
    private TextView tvTitle;
    public VrPanoramaView vrPanoramaView;
    private RecyclerView recyclerView;

    private PanoramaImageAdapter mAdapter;
    private int currPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_vr);

        initView();
        initListener();
    }

    private void initView() {
        ivMine = findViewById(R.id.iv_mine);
        tvTitle = findViewById(R.id.tv_title);
        ImageUtil.colorImageViewDrawable(ivMine, R.color.transparent60_white);

        vrPanoramaView = findViewById(R.id.vrPanoramaView);
        vrPanoramaView.setTouchTrackingEnabled(true);
        vrPanoramaView.setFullscreenButtonEnabled(true); //显示全屏模式按钮
        vrPanoramaView.setInfoButtonEnabled(false); //设置隐藏最左边信息的按钮
        vrPanoramaView.setStereoModeButtonEnabled(false); //设置隐藏立体模型的按钮
        currPosition = new Random().nextInt(ModelUtil.getPanoramaImageList().size());
        PanoramaImageModel model = ModelUtil.getPanoramaImageList().get(currPosition);
        loadPanoramaImage(model);

        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new PanoramaImageAdapter(this, ModelUtil.getPanoramaImageList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        ivMine.setOnClickListener(v -> {
            //startActivity(new Intent(this, AboutActivity.class));
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (currPosition == position) return;
            currPosition = position;
            PanoramaImageModel model = mAdapter.getData().get(position);
            loadPanoramaImage(model);
        });
    }

    private void loadPanoramaImage(PanoramaImageModel model) {
        loadPanoramaImage(getBitmapFromAssets(model.assetName));
        tvTitle.setText(model.title);
    }

    private void loadPanoramaImage(Bitmap bitmap) {
        if (bitmap == null) return;
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        //输入图片类型
        options.inputType = VrPanoramaView.Options.TYPE_MONO;
        //加载本地的图片源
        vrPanoramaView.loadImageFromBitmap(bitmap, options);
        //设置网络图片源
        //vrPanoramaView.loadImageFromByteArray();
    }

    private Bitmap getBitmapFromAssets(String fileName) {
        if (TextUtils.isEmpty(fileName)) return null;
        try {
            InputStream inputStream = getAssets().open(fileName);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        vrPanoramaView.resumeRendering();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vrPanoramaView.pauseRendering();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vrPanoramaView.shutdown();
    }
}
