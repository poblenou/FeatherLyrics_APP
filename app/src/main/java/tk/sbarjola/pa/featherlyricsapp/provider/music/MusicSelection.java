package tk.sbarjola.pa.featherlyricsapp.provider.music;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import tk.sbarjola.pa.featherlyricsapp.provider.base.AbstractSelection;

/**
 * Selection for the {@code music} table.
 */
public class MusicSelection extends AbstractSelection<MusicSelection> {
    @Override
    protected Uri baseUri() {
        return MusicColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MusicCursor} object, which is positioned before the first entry, or null.
     */
    public MusicCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MusicCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MusicCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MusicCursor} object, which is positioned before the first entry, or null.
     */
    public MusicCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MusicCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MusicCursor query(Context context) {
        return query(context, null);
    }


    public MusicSelection id(long... value) {
        addEquals("music." + MusicColumns._ID, toObjectArray(value));
        return this;
    }

    public MusicSelection idNot(long... value) {
        addNotEquals("music." + MusicColumns._ID, toObjectArray(value));
        return this;
    }

    public MusicSelection orderById(boolean desc) {
        orderBy("music." + MusicColumns._ID, desc);
        return this;
    }

    public MusicSelection orderById() {
        return orderById(false);
    }

    public MusicSelection title(String... value) {
        addEquals(MusicColumns.TITLE, value);
        return this;
    }

    public MusicSelection titleNot(String... value) {
        addNotEquals(MusicColumns.TITLE, value);
        return this;
    }

    public MusicSelection titleLike(String... value) {
        addLike(MusicColumns.TITLE, value);
        return this;
    }

    public MusicSelection titleContains(String... value) {
        addContains(MusicColumns.TITLE, value);
        return this;
    }

    public MusicSelection titleStartsWith(String... value) {
        addStartsWith(MusicColumns.TITLE, value);
        return this;
    }

    public MusicSelection titleEndsWith(String... value) {
        addEndsWith(MusicColumns.TITLE, value);
        return this;
    }

    public MusicSelection orderByTitle(boolean desc) {
        orderBy(MusicColumns.TITLE, desc);
        return this;
    }

    public MusicSelection orderByTitle() {
        orderBy(MusicColumns.TITLE, false);
        return this;
    }

    public MusicSelection band(String... value) {
        addEquals(MusicColumns.BAND, value);
        return this;
    }

    public MusicSelection bandNot(String... value) {
        addNotEquals(MusicColumns.BAND, value);
        return this;
    }

    public MusicSelection bandLike(String... value) {
        addLike(MusicColumns.BAND, value);
        return this;
    }

    public MusicSelection bandContains(String... value) {
        addContains(MusicColumns.BAND, value);
        return this;
    }

    public MusicSelection bandStartsWith(String... value) {
        addStartsWith(MusicColumns.BAND, value);
        return this;
    }

    public MusicSelection bandEndsWith(String... value) {
        addEndsWith(MusicColumns.BAND, value);
        return this;
    }

    public MusicSelection orderByBand(boolean desc) {
        orderBy(MusicColumns.BAND, desc);
        return this;
    }

    public MusicSelection orderByBand() {
        orderBy(MusicColumns.BAND, false);
        return this;
    }
}
