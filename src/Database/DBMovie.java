package Database;
import java.time.LocalDate;
import java.util.Arrays;

public class DBMovie {
    private String title;
    private String year;
    private String rated;
    private LocalDate released;
    private int runtime;
    private DBGenre[] genres;
    private DBActor[] actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private DBRating[] ratings;
    private int metaScore;
    private double imdbRating;
    private long imdbVotes;
    private String imdbID;
    private String type;
    private LocalDate DVD;
    private String boxOffice;
    private String production;
    private String website;

    public DBMovie() {
    }

    public DBMovie(String title, String year, String rated, LocalDate released, int runtime, DBGenre[] genres, DBActor[] actors, String plot, String language, String country, String awards, String poster, DBRating[] ratings, int metaScore, double imdbRating, long imdbVotes, String imdbID, String type, LocalDate DVD, String boxOffice, String production, String website) {
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genres = genres;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.poster = poster;
        this.ratings = ratings;
        this.metaScore = metaScore;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.imdbID = imdbID;
        this.type = type;
        this.DVD = DVD;
        this.boxOffice = boxOffice;
        this.production = production;
        this.website = website;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public DBGenre[] getGenres() {
        return genres;
    }

    public void setGenres(DBGenre[] genres) {
        this.genres = genres;
    }

    public DBActor[] getActors() {
        return actors;
    }

    public void setActors(DBActor[] actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public DBRating[] getRatings() {
        return ratings;
    }

    public void setRatings(DBRating[] ratings) {
        this.ratings = ratings;
    }

    public int getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(int metaScore) {
        this.metaScore = metaScore;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public long getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(long imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDVD() {
        return DVD;
    }

    public void setDVD(LocalDate DVD) {
        this.DVD = DVD;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void addGenre(DBGenre dbGenre) {
        if(genres == null) {
            genres = new DBGenre[1];
            genres[0] = dbGenre;
            return;
        }
        DBGenre[] newGenres = new DBGenre[genres.length + 1];
        System.arraycopy(genres, 0, newGenres, 0, genres.length);
        newGenres[newGenres.length - 1] = dbGenre;
        genres = newGenres;
    }

    public void addActor(DBActor dbActor) {
        if(actors == null) {
            actors = new DBActor[1];
            actors[0] = dbActor;
            return;
        }
        DBActor[] newActors = new DBActor[actors.length + 1];
        System.arraycopy(actors, 0, newActors, 0, actors.length);
        newActors[newActors.length - 1] = dbActor;
        actors = newActors;
    }
    public void addRating(DBRating dbRating) {
        if(ratings == null) {
            ratings = new DBRating[1];
            ratings[0] = dbRating;
            return;
        }
        DBRating[] newRatings = new DBRating[ratings.length + 1];
        System.arraycopy(ratings, 0, newRatings, 0, ratings.length);
        newRatings[newRatings.length - 1] = dbRating;
        ratings = newRatings;
    }

    @Override
    public String toString() {
        return "DBMovie{" +
                "title=" + title + '\n' +
                "year=" + year + '\n' +
                "rated=" + rated + '\n' +
                "released=" + released + '\n' +
                "runtime=" + runtime + '\n' +
                "genres=" + Arrays.toString(genres) + '\n' +
                "actors=" + Arrays.toString(actors) + '\n' +
                "plot=" + plot + '\n' +
                "language=" + language + '\n' +
                "country=" + country + '\n' +
                "awards=" + awards + '\n' +
                "poster=" + poster + '\n' +
                "ratings=" + Arrays.toString(ratings) + '\n' +
                "metaScore=" + metaScore +  '\n' +
                "imdbRating=" + imdbRating + '\n' +
                "imdbVotes=" + imdbVotes + '\n' +
                "imdbID=" + imdbID + '\n' +
                "type=" + type + '\n' +
                "DVD=" + DVD + '\n' +
                "boxOffice=" + boxOffice + '\n' +
                "production=" + production + '\n' +
                "website=" + website +
                '}';
    }
}