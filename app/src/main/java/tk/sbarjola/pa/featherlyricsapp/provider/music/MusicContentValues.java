package tk.sbarjola.pa.featherlyricsapp.provider.music;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import tk.sbarjola.pa.featherlyricsapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code music} table.
 */
public class MusicContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MusicColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MusicSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MusicSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MusicContentValues putTitle(@Nullable String value) {
        mContentValues.put(MusicColumns.TITLE, value);
        return this;
    }

    public MusicContentValues putTitleNull() {
        mContentValues.putNull(MusicColumns.TITLE);
        return this;
    }

    public MusicContentValues putBand(@Nullable String value) {
        mContentValues.put(MusicColumns.BAND, value);
        return this;
    }

    public MusicContentValues putBandNull() {
        mContentValues.putNull(MusicColumns.BAND);
        return this;
    }
}
