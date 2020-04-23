package com.panchao.imagelooktest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class SpaceImageDetailActivity extends AppCompatActivity
{
    private LinearLayout ll;
    private SmoothImageView imageView;
    private SmoothImageView.TransformListener listener;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_image_detail);

        mActivity = this;

        ll = findViewById(R.id.ll);
        imageView = findViewById(R.id.simg);

//        mDatas = (ArrayList<String>) getIntent().getSerializableExtra("images");
//        mPosition = getIntent().getIntExtra("position", 0);
        int mLocationX = getIntent().getIntExtra("locationX", 0);
        int mLocationY = getIntent().getIntExtra("locationY", 0);
        int mWidth = getIntent().getIntExtra("width", 0);
        final int mHeight = getIntent().getIntExtra("height", 0);

        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);


        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.dog, imageView);

        listener = new SmoothImageView.TransformListener()
        {
            @Override
            public void onTransformComplete(int mode)
            {
                if (mode == SmoothImageView.STATE_TRANSFORM_OUT)
                {
                    mActivity.finish();
                    overridePendingTransition(0, 0);
                }
            }
        };
        imageView.setOnTransformListener(listener);

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.i("SpaceImageDetailActivity", "点击了imageView");
                imageView.transformOut();

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        imageView.transformOut();

        super.onBackPressed();

        overridePendingTransition(0, 0);
    }
}
