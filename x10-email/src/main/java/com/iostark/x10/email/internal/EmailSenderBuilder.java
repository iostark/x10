/*
 * Copyright (c) $year IO Stark
 */

package com.iostark.x10.email.internal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.iostark.x10.base.annotation.api.API_Internal;

import java.util.Map;

@API_Internal
public class EmailSenderBuilder {

    public enum EmailProviderType {
        NONE,
        DEFAULT_EMAIL_APP
    }

    private EmailProviderType providerType = EmailProviderType.NONE;
    private Context context;
    private String selectProviderTitle;

    /**
     *
     * @param type The email provider type
     * @param params Custom params
     * @return A configured builder
     */
    public EmailSenderBuilder setProviderType(final EmailProviderType type, final Map<String, Object> params) {
        providerType = type;
        return this;
    }

    public EmailSenderBuilder setContext(final Context context) {
        this.context = context;
        return this;
    }

    public EmailSenderBuilder setSelectAnProviderTitle(final String text) {
        selectProviderTitle = text;
        return this;
    }

    public EmailSender build() {
        switch (providerType) {

            case DEFAULT_EMAIL_APP:
                return new DefaultAppEmailService();
            case NONE: // fall-through
            default:
                return new BaseEmailService();
        }
    }

    private static class BaseEmailService implements EmailSender {

        @Override
        public void send(final Email email) {
            send(email, SEND_LISTENER_NOP);
        }

        @Override
        public void send(final Email email, final SendListener listener) {

        }

        private final static SendListener SEND_LISTENER_NOP = new SendListener() {
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
            public void onError(final Exception exception) {

            }
        };
    }

    private class DefaultAppEmailService extends BaseEmailService {
        @Override
        @SuppressWarnings("PMD.LawOfDemeter")
        public void send(final Email email, final SendListener listener) {
            final String[] tos = email.getToRecipients().toArray(new String[email.getToRecipients().size()]);

            final Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            // intent.setType("text/plain");
            // intent.setType("message/rfc822");
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, tos);
            intent.putExtra(Intent.EXTRA_SUBJECT, email.getSubject());
            intent.putExtra(Intent.EXTRA_TEXT, email.getBody());

            try {
                final Intent intentChooser = Intent.createChooser(intent, selectProviderTitle);
                context.startActivity(intentChooser);
                listener.onProbablySent();
            } catch (android.content.ActivityNotFoundException exception) {
                listener.onError(exception);
            }
        }
    }

}
