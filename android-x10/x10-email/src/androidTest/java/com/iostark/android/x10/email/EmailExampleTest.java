package com.iostark.android.x10.email;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class EmailExampleTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.iostark.android.x10.email.test", appContext.getPackageName());
    }

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
            public void onError(Exception ex) {

            }
        });
    }
}
