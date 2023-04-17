package OMDB;

public class OMDBRating {
    private String Source;
    private String Value;
    public OMDBRating(String source, String value) {
        Source = source;
        Value = value;
    }
    public String getSource() {
        return Source;
    }
    public void setSource(String source) {
        Source = source;
    }
    public String getValue() {
        return Value;
    }
    public void setValue(String value) {
        Value = value;
    }
}
