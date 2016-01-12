package com.iostark.x10.media.picker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.iostark.x10.base.X10BuildConfig;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;
import com.iostark.x10.base.id.internal.GlobalNumberIdentifierGenerator;
import com.iostark.x10.base.internal.X10Bundle;
import com.iostark.x10.base.logging.Logger;
import com.iostark.x10.base.logging.LoggingRegistry;
import com.iostark.x10.media.ModuleConfig;

import java.io.File;
import java.io.IOException;

/**
 * Source using a camera to obtain a media (eg: picture, video).
 *
 * @param <MediaType> A path to media to pick or the media itself
 * @since V0_5_0
 */
@KeepEntryPoint
public class DefaultCameraSource<MediaType> extends BaseMediaSource<MediaType> {
    private static final int REQUEST_CODE = GlobalNumberIdentifierGenerator.INT_IDENTIFIER_GENERATOR.next();
    private static final String REQUEST_CODE_KEY = "request_code";
    private final static String KEY_EXPECTED_MEDIA_PATH = X10BuildConfig.LIBRARY_ID + ".media_path_expected";

    private boolean isMediaAvailable;
    private String expectedMediaPath;
    private File storageDirectory = getDefaultDirectory();

    private int requestedCode;

    private Logger log;

    {
        log = LoggingRegistry.getLoggerOrCreate(ModuleConfig.MODULE_NAME);
    }

    @KeepEntryPoint
    public DefaultCameraSource(final int requestCode) {
        this.requestedCode = requestCode;
    }

    @KeepEntryPoint
    public DefaultCameraSource() {
        this(REQUEST_CODE);
    }

    /**
     * Set the directory to store the media picked to.
     *
     * @param storageDirectory Directory path.
     * @since V0_5_0
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @KeepEntryPoint
    public void setStorageDirectory(final @NonNull File storageDirectory) {
        if (storageDirectory == null) {
            final File directory = getDefaultDirectory();
            if (!directory.exists()) {
                final boolean isCreated = directory.mkdirs();
                log.d("Directory %s was %s created", directory.getAbsolutePath(), isCreated ? "" : " not ");

                this.storageDirectory = directory;
            }
        } else {
            this.storageDirectory = storageDirectory;
        }
    }

    private File getDefaultDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @KeepEntryPoint
    public void beginPick(final @NonNull Activity activity) throws Exception {
        pickPictureFromCamera(activity, requestedCode);
    }

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @KeepEntryPoint
    public void pickResult(final @Nullable Intent intent, final int requestCode, final int resultCode) throws Exception {
        if (requestCode != requestedCode) {
            return;
        }

        if (resultCode != Activity.RESULT_OK) {
            isMediaAvailable = false;
        } else {
            handleMediaFromCamera(expectedMediaPath);
        }
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @KeepEntryPoint
    public void endPick(final MediaSourceResult<MediaType> result) {
        if (!isMediaAvailable) {
            result.onMediaUnavailable();
        } else {
            try {
                final MediaType media = convertMediaPathToMediaType(expectedMediaPath);
                result.onMediaAvailable(media);
            } catch (Exception e) {
                result.onError(e);
            }
        }
    }

    @Override
    @SuppressWarnings("PMD.LawOfDemeter")
    @KeepEntryPoint
    public void onSaveInstanceState(final Bundle outState) {
        final X10Bundle x10Bundle = new X10Bundle(outState, ModuleConfig.MODULE_NAME, DefaultCameraSource.class.getSimpleName());
        x10Bundle.putInt(REQUEST_CODE_KEY, requestedCode);
        x10Bundle.putString(KEY_EXPECTED_MEDIA_PATH, expectedMediaPath);
    }

    @Override
    @SuppressWarnings("PMD.LawOfDemeter")
    @KeepEntryPoint
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        final X10Bundle x10Bundle = new X10Bundle(savedInstanceState, ModuleConfig.MODULE_NAME, DefaultCameraSource.class.getSimpleName());
        requestedCode = x10Bundle.getInt(REQUEST_CODE_KEY);

        if (savedInstanceState.containsKey(KEY_EXPECTED_MEDIA_PATH)) {
            expectedMediaPath = savedInstanceState.getString(KEY_EXPECTED_MEDIA_PATH);
        }
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private void pickPictureFromCamera(final @NonNull Activity activity, final int requestCode) throws IOException {
        // https://commonsware.com/blog/2015/06/08/action-image-capture-fallacy.html
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            final File imageFile = new File(this.storageDirectory, System.currentTimeMillis() + ".jpeg");
            this.expectedMediaPath = imageFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            activity.startActivityForResult(intent, requestCode);
        } else {
            log.e("[%d] No application can capture media", requestedCode);
        }
    }

    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void handleMediaFromCamera(final String mediaPath) {
        isMediaAvailable = true;
        // FIXME check media can be retrieved
    }

    @KeepEntryPoint
    protected MediaType convertMediaPathToMediaType(final String mediaPath) throws ClassCastException {
        try {
            return (MediaType) mediaPath;
        } catch (ClassCastException exception) {
            final ClassCastException classCastException = new ClassCastException("You should override DefaultCameraSource.convertMediaPathToMediaType(...)");
            classCastException.initCause(exception);
            throw classCastException;
        }
    }

}
