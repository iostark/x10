package com.iostark.x10.inputdevice.keyboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.iostark.android.x10.inputdevice.test.R;


public class SoftKeyboardActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_softkeyboard_activity);

        super.onCreate(savedInstanceState);
    }

    public EditText getTextView() {
        return (EditText) findViewById(R.id.text);
    }
}
