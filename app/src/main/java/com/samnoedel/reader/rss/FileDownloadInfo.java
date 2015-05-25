package com.samnoedel.reader.rss;

import java.io.File;
import java.net.URL;

public class FileDownloadInfo {

    private File mDestinationFile;
    private URL mSourceUrl;

    public FileDownloadInfo(URL sourceUrl, File destinationFile) {
        mSourceUrl = sourceUrl;
        mDestinationFile = destinationFile;
    }

    public File getDestinationFile() {
        return mDestinationFile;
    }

    public URL getSourceUrl() {
        return mSourceUrl;
    }
}
