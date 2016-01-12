package com.iostark.x10.media.picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * A source capable of providing a media.
 * @since V0_5_0
 */
@KeepEntryPoint
public interface MediaSource<MediaType> {
    /**
     * Begin requesting for a media.
     * @param activity Activity instance
     * @throws Exception If the operation fails
     * @since V0_5_0
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @KeepEntryPoint
    void beginPick(@NonNull Activity activity) throws Exception;

    /**
     * The request for a media has completed.
     * @param intent Intnet passed by an activity
     * @param requestCode The request code when initiating the media request
     * @param resultCode The request's result code
     * @throws Exception if the operation fails
     * @since V0_5_0
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @KeepEntryPoint
    void pickResult(@Nullable Intent intent, int requestCode, int resultCode) throws Exception;

    /**
     * End requesting for a media.
     * @param callback Callback
     * @since V0_5_0
     */
    @KeepEntryPoint
    void endPick(MediaSourceResult<MediaType> callback);

    /**
     * Called when the media request must be serialized, such when an activity is paused.
     *
     * @param outState Bundle to save the media source's state to
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onSaveInstanceState(Bundle outState);

    /**
     * Called when the media request must be deserialized, such when an activity is resumed.
     *
     * @param savedInstanceState Bundle used to save the instance state
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onRestoreInstanceState(Bundle savedInstanceState);
}
