package tk.sbarjola.pa.featherlyricsapp.provider.music;

import tk.sbarjola.pa.featherlyricsapp.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A human being which is part of a team.
 */
public interface MusicModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code band} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBand();
}
