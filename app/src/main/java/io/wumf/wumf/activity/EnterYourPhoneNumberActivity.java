package io.wumf.wumf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.wumf.wumf.R;
import io.wumf.wumf.memory.Memory;

/**
 * Created by max on 05.07.16.
 */
public class EnterYourPhoneNumberActivity extends Activity {

    private EditText phoneNumber;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_enter_your_phone_number);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        if ( TextUtils.isEmpty(Memory.INSTANCE.getPhone()) ) {
            //do nothing
        } else {
            goNext(); //skip this activity
        }
    }

    public void onClickNext(View view) {

        String localPhone = phoneNumber.getText().toString();

        if (TextUtils.isEmpty(localPhone)) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_LONG).show();
            return;
        }

        String phone = "+380" + localPhone;
        Memory.INSTANCE.setPhone(phone);
        goNext();
    }

    private void goNext() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
