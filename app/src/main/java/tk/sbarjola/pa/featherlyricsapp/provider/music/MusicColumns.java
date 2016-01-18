package tk.sbarjola.pa.featherlyricsapp.provider.music;

import android.net.Uri;
import android.provider.BaseColumns;

import tk.sbarjola.pa.featherlyricsapp.provider.MusicProvider;
import tk.sbarjola.pa.featherlyricsapp.provider.music.MusicColumns;

/**
 * A human being which is part of a team.
 */
public class MusicColumns implements BaseColumns {
    public static final String TABLE_NAME = "music";
    public static final Uri CONTENT_URI = Uri.parse(MusicProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String TITLE = "title";

    public static final String BAND = "band";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            BAND
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(BAND) || c.contains("." + BAND)) return true;
        }
        return false;
    }

}
