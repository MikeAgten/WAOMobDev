package android.wao.com.data;

import android.provider.BaseColumns;

public final class StoresContract {

    private StoresContract(){} //om te voorkomen dat ze deze klasse implementeren

    public static class StoreEntry implements BaseColumns{
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_PHONENUMBER = "phoneNumber";
        public static final String COLUMN_NAME_WEBSITE = "website";
        public static final String COLUMN_NAME_OPEN = "open";
    }
}
