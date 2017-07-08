package com.gnuftw.bestandworst;

import android.provider.BaseColumns;

public final class DbContract {
    private DbContract() {}

    public static class DbEntry implements BaseColumns {
        public static final String TABLE_NAME  = "entries";
        /*public static final String _ID = "_id";*/
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_BEST = "best";
        public static final String COLUMN_WORST = "worst";
    }
}
