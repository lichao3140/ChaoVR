package com.lichao.chaovr.panorama;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.lichao.chaovr.R;

/**
 * Created by ChaoLi on 2018/5/10 0010 - 19:53
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public class GLPanorama extends RelativeLayout implements SensorEventListener {
    private Context mContext;
    private IViews mGlSurfaceView;
    private ImageView img;
    //记录xy坐标位置
    private float mPreviousY, mPreviousYs;
    private float mPreviousX, mPreviousXs;
    private float predegrees = 0;
    private Ball mBall;
    private SensorManager mSensorManager;
    private Sensor mGyroscopeSensor;
    private static final float NS2S = 1.0f / 1000000000.0f; // 将纳秒转化为秒
    private float timestamp;
    private float angle[] = new float[3];
    public GLPanorama(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public GLPanorama(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public GLPanorama(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        initView();

    }

    /*初始化组件*/
    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.panorama_layout, this);
        mGlSurfaceView = (IViews) findViewById(R.id.mIViews);
        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                zero();
            }
        });
    }

    /**
     * 初始化传感器
     */
    private void initSensor() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //注册陀螺仪传感器 输出的时间间隔类型：SENSOR_DELAY_FASTEST(0微秒) SENSOR_DELAY_GAME(20000微秒)
        mSensorManager.registerListener(this, mGyroscopeSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * 获得传感器数据
     * 从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；
     * 否则，为负值得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒 。
     * 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度，将弧度转化为角度。
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if (timestamp != 0) {
                final float dT = (sensorEvent.timestamp - timestamp) * NS2S;
                angle[0] += sensorEvent.values[0] * dT;
                angle[1] += sensorEvent.values[1] * dT;
                angle[2] += sensorEvent.values[2] * dT;
                float anglex = (float) Math.toDegrees(angle[0]);
                float angley = (float) Math.toDegrees(angle[1]);
                float anglez = (float) Math.toDegrees(angle[2]);
                Sensordt info = new Sensordt();
                info.setSensorX(angley);
                info.setSensorY(anglex);
                info.setSensorZ(anglez);
                Message msg = new Message();
                msg.what = 101;
                msg.obj = info;
                mHandler.sendMessage(msg);
            }
            timestamp = sensorEvent.timestamp;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    //设置填充球的Y，X的角度
                    Sensordt info = (Sensordt) msg.obj;
                    float y = info.getSensorY();
                    float x = info.getSensorX();
                    float dy = y - mPreviousY;// 计算触控笔Y位移
                    float dx = x - mPreviousX;// 计算触控笔X位移
                    //2.0也就是陀螺仪传过来的值乘以得出偏移的角度，数值越大，每次偏移更快
                    mBall.yAngle += dx * 2.0f;// 设置填充椭圆绕y轴旋转的角度
                    mBall.xAngle += dy * 0.5f;// 设置填充椭圆绕x轴旋转的角度
                    if (mBall.xAngle < -50f) {// 设置了上下只能偏移50f
                        mBall.xAngle = -50f;
                    } else if (mBall.xAngle > 50f) {
                        mBall.xAngle = 50f;
                    }
                    mPreviousY = y;// 记录触控笔位置
                    mPreviousX = x;// 记录触控笔位置
                    rotate();
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 加入手势操控，拖动图片转动
     * @param event 手势事件
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mSensorManager.unregisterListener(this);
        float y = event.getY();
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousYs;
                float dx = x - mPreviousXs;

                mBall.yAngle += dx * 0.3f;
                mBall.xAngle += dy * 0.3f;
                if (mBall.xAngle < -50f) {
                    mBall.xAngle = -50f;
                } else if (mBall.xAngle > 50f) {
                    mBall.xAngle = 50f;
                }
                rotate();
                break;
            case MotionEvent.ACTION_UP:
                // 当手指点击屏幕的时候要关闭陀螺仪传感器的监听不然会引起冲突。当手指离开屏幕，重新监听陀螺仪传感器
                mSensorManager.registerListener(this, mGyroscopeSensor,
                        SensorManager.SENSOR_DELAY_FASTEST);
                break;
        }
        mPreviousYs = y;
        mPreviousXs = x;
        return true;
    }
    /**
     * 传入图片路径
     * @param pimgid
     */
    public void setGLPanorama(int pimgid) {
        mGlSurfaceView.setEGLContextClientVersion(2);
        mBall = new Ball(mContext, pimgid);
        mGlSurfaceView.setRenderer(mBall);
        initSensor();

    }

    /**
     * 小图标旋转，跳跃
     */
    private void rotate() {
        // 为指示器加入动画跟随全景图一起转
        RotateAnimation anim = new RotateAnimation(predegrees, -mBall.yAngle,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(200);
        img.startAnimation(anim);
        predegrees = -mBall.yAngle;//记录这一次的起始角度作为下次旋转的初始角度
    }

    class Sensordt {
        float sensorX;
        float sensorY;
        float sensorZ;

        float getSensorX() {
            return sensorX;
        }

        void setSensorX(float sensorX) {
            this.sensorX = sensorX;
        }

        float getSensorY() {
            return sensorY;
        }

        void setSensorY(float sensorY) {
            this.sensorY = sensorY;
        }

        float getSensorZ() {
            return sensorZ;
        }

        void setSensorZ(float sensorZ) {
            this.sensorZ = sensorZ;
        }
    }

    private Handler mHandlers = new Handler();
    int yy = 0;

    /**
     * 点击指示器还原起始位置
     */
    private void zero() {
        yy = (int) ((mBall.yAngle - 90f) / 10f);
        mHandlers.post(new Runnable() {
            @Override
            public void run() {
                if (yy != 0) {
                    if (yy > 0) {
                        mBall.yAngle = mBall.yAngle - 10f;
                        mHandlers.postDelayed(this, 16);
                        yy--;
                    }
                    if (yy < 0) {
                        mBall.yAngle = mBall.yAngle + 10f;
                        mHandlers.postDelayed(this, 16);
                        yy++;
                    }
                } else {
                    mBall.yAngle = 90f;
                }
                mBall.xAngle = 0f;
            }
        });
    }
}
