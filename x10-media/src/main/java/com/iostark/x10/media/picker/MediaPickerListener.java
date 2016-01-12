package com.iostark.x10.media.picker;

import android.support.annotation.NonNull;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * @since V0_5_0
 */
@KeepEntryPoint
public interface MediaPickerListener<MediaType> {
    /**
     * Called when the media picked is available.
     * @param media Media picked
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onMediaAvailable(final @NonNull MediaType media);

    /**
     * Called when no media was picked.
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onCancel();

    /**
     * Called when an error occurred.
     * @param exception Raised exception
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onError(final Exception exception);
}
