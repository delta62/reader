package com.samnoedel.reader.rss;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

public class GetFileTask extends AsyncTask<FileDownloadInfo, Void, File> {

    private static final int END_OF_STREAM = -1;
    private static final int BUFFER_SIZE = 1024;
    private static final String TAG = GetFileTask.class.getName();

    @Override
    protected File doInBackground(FileDownloadInfo... params) {
        FileDownloadInfo dlInfo = params[0];
        InputStream in = null;

        try {
            URLConnection conn = dlInfo.getSourceUrl().openConnection();
            in = new BufferedInputStream(conn.getInputStream());
            writeFile(in, dlInfo.getDestinationFile());
            return dlInfo.getDestinationFile();
        } catch (IOException ex) {
            Log.e(TAG, "Error while downloading index file", ex);
            return null;
        } finally {
            if (in != null) {
                tryCloseStream(in);
            }
        }
    }

    private static void writeFile(InputStream inputStream, File outputFile) throws IOException {
        OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(outputFile));
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            while (inputStream.read(buffer) != END_OF_STREAM) {
                fileOut.write(buffer);
            }
        } finally {
            tryCloseStream(fileOut);
        }
    }

    private static void tryCloseStream(Closeable stream) {
        try {
            stream.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to close input stream", ex);
        }
    }
}
