package Database;

public class DBRating {
    private String source;
    private String value;
    private String imdbId;
    public DBRating() {

    }
    public DBRating(String source, String rating, String imdbId) {
        this.source = source;
        this.value = rating;
        this.imdbId = imdbId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public String toString() {
        return "[Rating{" +
                "source='" + source + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
