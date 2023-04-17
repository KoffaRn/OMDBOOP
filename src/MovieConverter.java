import Database.DBActor;
import Database.DBGenre;
import Database.DBMovie;
import Database.DBRating;
import OMDB.OMDBMovie;
import OMDB.OMDBRating;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class MovieConverter {
    public MovieConverter() {

    }
    public DBMovie convertToDBMovie(OMDBMovie omdbMovie) {
        DBMovie dbMovie = new DBMovie();
        dbMovie.setDVD(convertToLocalDate(omdbMovie.getDVD()));
        dbMovie.setAwards(omdbMovie.getAwards());
        dbMovie.setCountry(omdbMovie.getCountry());
        dbMovie.setActors(getAllActorsFromMovie(omdbMovie));
        dbMovie.setBoxOffice(omdbMovie.getBoxOffice());
        dbMovie.setGenres(convertToDBGenre(omdbMovie.getGenre(), omdbMovie.getImdbID()));
        dbMovie.setImdbID(omdbMovie.getImdbID());
        dbMovie.setImdbVotes(Long.parseLong(omdbMovie.getImdbVotes().replaceAll(",", "")));
        dbMovie.setImdbRating(Double.parseDouble(omdbMovie.getImdbRating()));
        dbMovie.setLanguage(omdbMovie.getLanguage());
        String metScoreNumbers = omdbMovie.getMetascore().replaceAll("[^0-9]", "");
        if(metScoreNumbers.equals("")) {
            dbMovie.setMetaScore(0);
        } else {
            dbMovie.setMetaScore(Integer.parseInt(metScoreNumbers));
        }
        dbMovie.setPlot(omdbMovie.getPlot());
        dbMovie.setPoster(omdbMovie.getPoster());
        dbMovie.setProduction(omdbMovie.getProduction());
        dbMovie.setRated(omdbMovie.getRated());
        dbMovie.setRatings(convertToDBRating(omdbMovie.getRatings(), omdbMovie.getImdbID()));
        dbMovie.setReleased(convertToLocalDate(omdbMovie.getReleased()));
        if(omdbMovie.getRuntime() == null || omdbMovie.getRuntime().equals("N/A")) {
            dbMovie.setRuntime(0);
        } else {
            dbMovie.setRuntime(Integer.parseInt(omdbMovie.getRuntime().replaceAll("[^0-9]", "")));
        }
        dbMovie.setTitle(omdbMovie.getTitle());
        dbMovie.setType(omdbMovie.getType());
        dbMovie.setWebsite(omdbMovie.getWebsite());
        dbMovie.setYear(omdbMovie.getYear());
        return dbMovie;
    }

    private DBActor[] getAllActorsFromMovie(OMDBMovie omdbMovie) {
        DBActor[] actors = convertToDBActor(omdbMovie.getActors(), omdbMovie.getImdbID(), "ACTOR");
        DBActor[] directors = convertToDBActor(omdbMovie.getDirector(), omdbMovie.getImdbID(), "DIRECTOR");
        DBActor[] writers = convertToDBActor(omdbMovie.getWriter(), omdbMovie.getImdbID(), "WRITER");
        DBActor[] allActors = new DBActor[actors.length + directors.length + writers.length];
        System.arraycopy(actors, 0, allActors, 0, actors.length);
        System.arraycopy(directors, 0, allActors, actors.length, directors.length);
        System.arraycopy(writers, 0, allActors, actors.length + directors.length, writers.length);
        return allActors;
    }

    private LocalDate convertToLocalDate(String dateString) {
        if(dateString == null || dateString.equals("N/A")) {
            return null;
        }
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd MMM yyyy")
                .toFormatter(Locale.ENGLISH);
        return LocalDate.parse(dateString, formatter);
    }
    private DBRating[] convertToDBRating(OMDBRating[] omdbRatings, String imdbId) {
        DBRating[] dbRatings = new DBRating[omdbRatings.length];
        for(int i = 0; i < omdbRatings.length; i++) {
            dbRatings[i] = new DBRating(omdbRatings[i].getSource(), omdbRatings[i].getValue(), imdbId);
        }
        return dbRatings;
    }
    private DBGenre[] convertToDBGenre(String OMDBGenres, String imdbID) {
        String[] genreStrings = OMDBGenres.split(",", 0);
        DBGenre[] dbGenres = new DBGenre[genreStrings.length];
        for(int i = 0; i < genreStrings.length; i++) {
            dbGenres[i] = new DBGenre(genreStrings[i], imdbID);
        }
        return dbGenres;
    }
    private DBActor[] convertToDBActor(String OMDBActors, String imdbID, String role) {
        String[] actorList = OMDBActors.split(",",0);
        DBActor[] dbActors = new DBActor[actorList.length];
        for(int i = 0; i < actorList.length; i++) {
            dbActors[i] = new DBActor(actorList[i],imdbID,role);
        }
        return dbActors;
    }

    public DBMovie[] convertToDBMovies(OMDBMovie[] omdbMovies) {
        DBMovie[] dbMovies = new DBMovie[omdbMovies.length];
        for(int i = 0; i < omdbMovies.length; i++) {
            dbMovies[i] = convertToDBMovie(omdbMovies[i]);
        }
        return dbMovies;
    }
}
