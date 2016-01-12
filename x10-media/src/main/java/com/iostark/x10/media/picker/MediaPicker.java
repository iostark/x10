package com.iostark.x10.media.picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * Usage:
 * <pre>
 * {@code
 * MediaPicker<String> picker = new MediaPicker<String>(Activity instance);
 * in Activity.onActivityResult(...), call picker.onActivityResult(...)
 * in Activity.onRestoreInstanceState(...), call picker.onRestoreInstanceState(...)
 * in Activity.onSaveInstanceState(...), call picker.onSaveInstanceState(...)
 * picker.setSource(new DefaultCameraSource<String>());
 * MediaPickerListener<String> callback = new MediaPickerListener<String>() {
 *     void onMediaAvailable(@NonNull String media) {
 *         // media is the path to the media
 *     }
 *     ...
 * }
 * picker.pick(callback);
 * }
 * </pre>
 *
 * @since V0_5_0
 */
@KeepEntryPoint
public class MediaPicker<MediaType> {
    private MediaPickerListener<MediaType> callback;
    private final Activity activity;
    private MediaSource<MediaType> source;

    // FIXME javadoc
    @KeepEntryPoint
    public MediaPicker(final @NonNull Activity activity) {
        this.activity = activity;
    }

    /**
     * Set source to retrieve a media with.
     *
     * @param source Source to retrieve media from
     * @since V0_5_0
     */
    @KeepEntryPoint
    public void setSource(final @NonNull MediaSource<MediaType> source) {
        this.source = source;
    }

    /**
     * Pick a media using the source from {@link #setSource(MediaSource)}.
     *
     * @param callback Media picking callback
     * @since V0_5_0
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @KeepEntryPoint
    public void pick(final @NonNull MediaPickerListener<MediaType> callback) {
        this.callback = callback;

        try {
            this.source.beginPick(activity);
        } catch (Exception e) {
            this.callback.onError(e);
        }
    }

    /**
     * Function to be called when {@link Activity#onActivityResult(int, int, Intent) is called}.
     *
     * @param requestCode See {@link Activity#onActivityResult(int, int, Intent)} for details
     * @param resultCode  See {@link Activity#onActivityResult(int, int, Intent)} for details
     * @param data        See {@link Activity#onActivityResult(int, int, Intent)} for details
     * @since V0_5_0
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @KeepEntryPoint
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            callback.onCancel();
            return;
        }

        try {
            source.pickResult(data, requestCode, resultCode);
        } catch (Exception e) {
            callback.onError(e);
        } finally {
            source.endPick(new MediaSourceResult<MediaType>() {
                @Override
                public void onMediaAvailable(final MediaType media) {
                    callback.onMediaAvailable(media);
                }

                @Override
                public void onMediaUnavailable() {
                    callback.onCancel();
                }

                @Override
                public void onError(final Exception exception) {
                    callback.onError(exception);
                }
            });
        }
    }

    /**
     * Function to be called when {@link Activity#onSaveInstanceState(Bundle)} is called.
     *
     * @param outState See {@link Activity#onSaveInstanceState(Bundle)} for details
     * @since V0_5_0
     */
    @KeepEntryPoint
    public void onSaveInstanceState(final Bundle outState) {
        source.onSaveInstanceState(outState);
    }

    /**
     * Function to be called when {@link Activity#onSaveInstanceState(Bundle)} is called.
     *
     * @param savedInstanceState See {@link Activity#onSaveInstanceState(Bundle)} for details
     * @since V0_5_0
     */
    @KeepEntryPoint
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        source.onRestoreInstanceState(savedInstanceState);
    }
}
