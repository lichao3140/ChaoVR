package com.lichao.chaovr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.lichao.chaovr.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_google)
    Button btGoogle;
    @BindView(R.id.bt_js)
    Button btJs;
    @BindView(R.id.bt_opengl)
    Button btOpengl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        new FirUpdater(this)
//                .apiToken("de43fcf07a3eb6d0fb63eaaad9e2cf71")
//                .appId(YOUR_FIR_APP_ID)
//                .checkVersion();

    }

    @OnClick({R.id.bt_google, R.id.bt_js, R.id.bt_opengl})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.bt_google:
                startActivity(new Intent(this, GoogleVRActivity.class));
                break;
            case R.id.bt_js:

                break;
            case R.id.bt_opengl:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
