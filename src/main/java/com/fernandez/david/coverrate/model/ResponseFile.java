package com.fernandez.david.coverrate.model;

import java.io.File;

public class ResponseFile {

    private final File file;

    public ResponseFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
