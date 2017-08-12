/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.iostark.x10.base.annotation.api.API_Beta;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Decodes a bitmap from a various source types (file path, resource id, etc ...).
 *
 * @since V0_5_0
 */
@API_Beta
@KeepEntryPoint
public class BitmapDecoder {
  private enum Source {
    BYTE_ARRAY,
    PATH,
    RESOURCE_ID
  }

  private Source source = null;
  private String dataAsPath = null;
  private int dataAsResourceId;
  private byte[] dataAsByteArray = null;
  private int targetWidth;
  private int targetHeigth;
  private Resources resources;

  /**
   * Decodes a bitmap from an absolute path.
   *
   * @param path Absolute path of the image to decode
   * @param reqHeight Height to decode image to
   * @param reqWidth Width to decode image to
   * @return Bitmap with requested dimensions
   * @since V0_5_0
   */
  @KeepEntryPoint
  public Bitmap decodeSampledBitmap(final String path, final int reqWidth, final int reqHeight) {
    source = Source.PATH;
    this.dataAsPath = path;
    targetWidth = reqWidth;
    targetHeigth = reqHeight;
    return decodeSampledBitmap();
  }

  /**
   * Decodes a bitmap from a byte array.
   *
   * @param data Byte array contains the image to decode
   * @param reqHeight Height to decode image to
   * @param reqWidth Width to decode image to
   * @return Bitmap with requested dimensions
   * @since V0_5_0
   */
  @SuppressFBWarnings(
    value = "EI_EXPOSE_REP2",
    justification = "Use external representation because data can be really large"
  )
  @KeepEntryPoint
  public Bitmap decodeSampledBitmap(final byte[] data, final int reqWidth, final int reqHeight) {
    source = Source.BYTE_ARRAY;
    dataAsByteArray = data;
    targetWidth = reqWidth;
    targetHeigth = reqHeight;
    return decodeSampledBitmap();
  }

  /**
   * Decodes a bitmap from a resource.
   *
   * @param resourceId Resource ID contains the image to decode
   * @param reqHeight Height to decode image to
   * @param reqWidth Width to decode image to
   * @param resources Resources instance
   * @return Bitmap with requested dimensions
   * @since V0_5_0
   */
  @KeepEntryPoint
  public Bitmap decodeSampledBitmap(
      final int resourceId, final Resources resources, final int reqWidth, final int reqHeight) {
    source = Source.RESOURCE_ID;
    dataAsResourceId = resourceId;
    this.resources = resources;
    targetWidth = reqWidth;
    targetHeigth = reqHeight;
    return decodeSampledBitmap();
  }

  private Bitmap decodeSampledBitmap() {
    // 1. Decode with inJustDecodeBounds=true to check the actual bitmap's dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    options.inScaled = false;

    switch (source) {
      case PATH:
        BitmapFactory.decodeFile(dataAsPath, options);
        break;
      case BYTE_ARRAY:
        BitmapFactory.decodeByteArray(dataAsByteArray, 0, dataAsByteArray.length, options);
        break;
      case RESOURCE_ID:
        BitmapFactory.decodeResource(resources, dataAsResourceId, options);
        break;
      default:
        // Missing a case ?
        return null;
    }

    // 2. Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeigth);

    // 3. Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;

    switch (source) {
      case PATH:
        return BitmapFactory.decodeFile(dataAsPath, options);
      case BYTE_ARRAY:
        return BitmapFactory.decodeByteArray(dataAsByteArray, 0, dataAsByteArray.length, options);
      case RESOURCE_ID:
        return BitmapFactory.decodeResource(resources, dataAsResourceId, options);
      default:
        // Missing a case ?
        return null;
    }
  }

  /**
   * Calculate sub sampling size.
   *
   * @param options Source image dimensions
   * @param reqWidth Target width dimension
   * @param reqHeight Target height dimension
   * @return Sub sampling size, rounded to nearest power of 2 value
   * @since V0_5_0
   */
  @KeepEntryPoint
  public static int calculateInSampleSize(
      final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and keeps both
      // height and width larger than the requested height and width.
      while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
      }
    }

    return inSampleSize;
  }
}
