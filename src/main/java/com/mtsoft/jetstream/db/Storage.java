package com.mtsoft.jetstream.db;

import com.mtsoft.jetstream.pojo.RootData;
import one.jetstream.JetstreamInstance;

public class Storage extends JetstreamInstance<RootData> {

    private static Storage instance;

    public static final String DB_PATH = "jetstream_storage";

    private Storage() {
        super();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    @Override
    protected String createStorageDirectoryName() {
        return DB_PATH;
    }

    @Override
    public boolean shutdown() {
        if (super.shutdown()) {
            instance = null;
            return true;
        }
        return false;
    }
}
