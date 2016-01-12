package com.iostark.x10.email;

import android.support.test.runner.AndroidJUnit4;

import com.iostark.x10.base.androidtest.ContextInstrumentedTest;
import com.iostark.x10.email.internal.Email;
import com.iostark.x10.email.internal.EmailSender;
import com.iostark.x10.email.internal.EmailSenderBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmailExampleTest extends ContextInstrumentedTest {

    @Test
    public void sendEmail() {
        Email email = new Email();
        email.setToRecipient("me@me.com");
        email.setSubject("Subject");
        email.setBody("Body");

        EmailSenderBuilder builder = new EmailSenderBuilder();
        EmailSender service = builder.build();
        service.send(email, new EmailSender.SendListener() {
            @Override
            public void onCancelled() {

            }

            @Override
            public void onProbablySent() {

            }

            @Override
            public void onSent() {

            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }
}
