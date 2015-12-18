package tk.sbarjola.pa.featherlyricsapp.Noticias;

/**
 * Created by sergi on 18/12/15.
 */
import java.util.HashMap;
import java.util.Map;

public class News {

    private String headline;
    private String kicker;
    private String url;
    private String inserted;
    private String modified;
    private String picSrc;
    private String picWidth;
    private String picHeight;
    private String picCaption;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     *
     * @param headline
     * The headline
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     *
     * @return
     * The kicker
     */
    public String getKicker() {
        return kicker;
    }

    /**
     *
     * @param kicker
     * The kicker
     */
    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The inserted
     */
    public String getInserted() {
        return inserted;
    }

    /**
     *
     * @param inserted
     * The inserted
     */
    public void setInserted(String inserted) {
        this.inserted = inserted;
    }

    /**
     *
     * @return
     * The modified
     */
    public String getModified() {
        return modified;
    }

    /**
     *
     * @param modified
     * The modified
     */
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     *
     * @return
     * The picSrc
     */
    public String getPicSrc() {
        return picSrc;
    }

    /**
     *
     * @param picSrc
     * The pic_src
     */
    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    /**
     *
     * @return
     * The picWidth
     */
    public String getPicWidth() {
        return picWidth;
    }

    /**
     *
     * @param picWidth
     * The pic_width
     */
    public void setPicWidth(String picWidth) {
        this.picWidth = picWidth;
    }

    /**
     *
     * @return
     * The picHeight
     */
    public String getPicHeight() {
        return picHeight;
    }

    /**
     *
     * @param picHeight
     * The pic_height
     */
    public void setPicHeight(String picHeight) {
        this.picHeight = picHeight;
    }

    /**
     *
     * @return
     * The picCaption
     */
    public String getPicCaption() {
        return picCaption;
    }

    /**
     *
     * @param picCaption
     * The pic_caption
     */
    public void setPicCaption(String picCaption) {
        this.picCaption = picCaption;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}