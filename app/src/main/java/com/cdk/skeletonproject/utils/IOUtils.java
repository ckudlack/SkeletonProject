package com.cdk.skeletonproject.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import timber.log.Timber;

/**
 * General IO stream manipulation utilities.
 * Modelled after Apache Commons IO
 */
public class IOUtils {
    // Standard storage location for digital camera files
    private static final String CAMERA_DIR = "/dcim/";

    private static final String TEMP_PREFIX = "TuroUpload";

    private static final int EOF = -1;

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static final String SUBDIRECTORY_TRIP = "trip";
    public static final String SUBDIRECTORY_VEHICLE = "vehicle";
    public static final String SUBDIRECTORY_PROFILE = "profile";
    public static final String SUBDIRECTORY_ID_VERIFICATION = "id_verification";

    private IOUtils() {
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        return copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static String toString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream), 1024 * 16);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();

        return builder.toString();
    }

    public static File getAlbumStorageDir(String albumName) {
        return new File(
                Environment.getExternalStorageDirectory()
                        + CAMERA_DIR
                        + albumName
        );
    }

    public synchronized static void deleteFiles(File directory) {
        if (directory == null) {
            Exception exception = new NullPointerException("directory is null");
            Timber.w(exception, "[Att] null directory");
        } else if (directory.list() == null || !directory.isDirectory()) {
            String message = directory.list() == null ? "directory.list() == null"
                    : "directory.isDirectory() is false.";
            Exception exception = new NullPointerException(message);
            Timber.w(exception, "directory length is: %d", directory.length());
        } else {
            for (String fileName : directory.list()) {
                if (!(new File(directory, fileName).delete())) {
                    Timber.i("[Warning] Unable to delete file %s", fileName);
                }
            }
        }
    }

    public static byte[] readFile(final Uri uri, final ContentResolver contentResolver) {
        final InputStream inputStream;
        try {
            inputStream = contentResolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(inputStream, bos);

        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Timber.w(e, "");
            }
        }
        return bos.toByteArray();
    }

    // http://hmkcode.com/android-display-selected-image-and-its-real-path/
    private static String getRealImagePath(final Context context, final Uri uri) {
        Timber.d("File path URI = %s", uri.toString());
        String imagePath = null;

        // Try to get photo pre kitkat way. Some devices, like Samsung need to do it this way and not the post kitkat way.
        final String[] proj = {MediaStore.Images.Media.DATA};
        final CursorLoader cursorLoader = new CursorLoader(context, uri, proj, null, null, null);
        final Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            final int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            imagePath = cursor.getString(columnIndex);
            cursor.close();
        }

        if (!TextUtils.isEmpty(imagePath)) {
            Timber.d("Cursor with Media.Data imagePath = %s", imagePath);
            return imagePath;
        }

        // If the path is null, then try to grab from the document post kitkat way.
        if (DocumentsContract.isDocumentUri(context, uri)) {
            final String wholeID = DocumentsContract.getDocumentId(uri);
            Cursor documentCursor = null;
            final String[] column = {MediaStore.Images.Media.DATA};
            if (wholeID != null && wholeID.length() > 1) {
                final String[] split = wholeID.split(":");
                if (split.length == 2) {
                    final String id = split[1];
                    final String sel = MediaStore.Images.Media._ID + "=?";
                    documentCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id}, null);
                } else {
                    return null;
                }
            }

            if (documentCursor == null) {
                imagePath = uri.getPath();
                Timber.d("Null Cursor with Media.EXTERNAL_CONTENT_URI imagePath = %s", imagePath);
            } else {
                final int columnIndex = documentCursor.getColumnIndex(column[0]);
                documentCursor.moveToFirst();
                imagePath = documentCursor.getString(columnIndex);
                documentCursor.close();
                Timber.d("Cursor with Media.EXTERNAL_CONTENT_URI imagePath = %s", imagePath);
            }
        } else {
            imagePath = uri.getPath();
            Timber.d("Not documentURI, using uriPath = %s.", imagePath);
        }


        return imagePath;
    }

    private static InputStream getInputStream(Context context, Uri uri) {
        if (uri.getScheme().equals("file") && uri.getPath().startsWith("/android_asset/")) {
            String path = uri.getPath().replace("/android_asset/", "");
            try {
                return context.getAssets().open(path);
            } catch (IOException e) {
                Timber.e("Error parsing uri: %s", uri);
            }
        } else {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                Timber.e("Error parsing uri: %s", uri);
            }
        }
        return null;
    }

    public static File getFileFromUri(final Context context, final Uri uri) {
        final String imagePath = getRealImagePath(context, uri);

        // https://buick.relayrides.com/issue/marvin-669
        // Try to create temporary file from input stream if imagePath is still null. This is the case
        // when trying to upload a file from Google Drive
        if (imagePath == null || uri.getScheme().equals("file") && uri.getPath().startsWith("/android_asset/")) {
            final InputStream in;
            try {
                in = getInputStream(context, uri);
                final File tempFile = File.createTempFile(TEMP_PREFIX, null);
                tempFile.deleteOnExit();
                try (FileOutputStream out = new FileOutputStream(tempFile)) {
                    IOUtils.copy(in, out);
                }
                return tempFile;

            } catch (IOException e) {
                Timber.e("Error parsing uri: %s", uri);
                return null;
            }

        } else {
            return new File(imagePath);
        }
    }
}
