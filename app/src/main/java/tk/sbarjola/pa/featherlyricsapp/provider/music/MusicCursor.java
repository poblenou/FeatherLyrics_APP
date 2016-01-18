package tk.sbarjola.pa.featherlyricsapp.provider.music;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import tk.sbarjola.pa.featherlyricsapp.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code music} table.
 */
public class MusicCursor extends AbstractCursor implements MusicModel {
    public MusicCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MusicColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(MusicColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code band} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBand() {
        String res = getStringOrNull(MusicColumns.BAND);
        return res;
    }
}
