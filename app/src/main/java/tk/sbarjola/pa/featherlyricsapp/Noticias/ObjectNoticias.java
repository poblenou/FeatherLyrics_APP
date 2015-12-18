package tk.sbarjola.pa.featherlyricsapp.Noticias;

/**
 * Created by sergi on 18/12/15.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectNoticias {

    private List<News> news = new ArrayList<News>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The news
     */
    public List<News> getNews() {
        return news;
    }

    /**
     *
     * @param news
     * The news
     */
    public void setNews(List<News> news) {
        this.news = news;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}