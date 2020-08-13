/*
 * Copyright 2017 Yan Zhenjie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.album.sample.app;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.sample.R;

/**
 * Created by YanZhenjie on 2017/8/17.
 */
public class CameraActivity extends AppCompatActivity {

    TextView mTextView;
    private SimpleDraweeView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mTextView = findViewById(R.id.tv_message);
        mImageView = findViewById(R.id.image_view);
    }

    private void takePicture() {
        Album.camera(this)
                .image()
//                .filePath()
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result,long totalSize) {
                        mTextView.setText(result);

                        Album.getAlbumConfig()
                                .getAlbumLoader()
                                .load(mImageView, result);
                    }
                })
                .start();
    }

    private void recordVideo() {
        Album.camera(this)
                .video()
//                .filePath()
                .quality(1)
                .limitDuration(Integer.MAX_VALUE)
                .limitBytes(Integer.MAX_VALUE)
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result,long totalSize) {
                        mTextView.setText(result);

                        Album.getAlbumConfig()
                                .getAlbumLoader()
                                .load(mImageView, result);
                    }
                })
                .start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_album_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.menu_image_capture: {
                takePicture();
                break;
            }
            case R.id.menu_video_capture: {
                recordVideo();
                break;
            }
        }
        return true;
    }
}
