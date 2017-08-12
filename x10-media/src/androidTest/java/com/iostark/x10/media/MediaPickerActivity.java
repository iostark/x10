/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.iostark.x10.media.picker.DefaultCameraSource;
import com.iostark.x10.media.picker.MediaPicker;
import com.iostark.x10.media.picker.MediaPickerListener;

/** Created by davidandreoletti on 10/01/2017. */
public class MediaPickerActivity extends Activity {

  MediaPicker<String> picker;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    picker = new MediaPicker<String>(this);
    picker.setSource(new DefaultCameraSource<String>());

    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    picker.onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    picker.onRestoreInstanceState(savedInstanceState);
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    picker.onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  public void pickMedia(MediaPickerListener<String> callback) {
    picker.pick(callback);
  }
}
