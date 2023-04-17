import Database.DBActor;
import Database.DBHandler;
import Database.DBMovie;
import OMDB.OMDBHandler;
import OMDB.OMDBMovie;

public class MovieHandler {
    DBHandler dbHandler;
    OMDBHandler omdbHandler;
    MovieConverter movieConverter;
    public MovieHandler() {
        this.dbHandler = new DBHandler("movies");
        this.omdbHandler = new OMDBHandler();
        this.movieConverter = new MovieConverter();
    }
    public DBMovie[] getDBMovies(String title) {
        DBMovie[] movies = dbHandler.getMoviesByTitle(title);
        if(movies.length == 0) {
            OMDBMovie[] omdbMovies = omdbHandler.getMoviesByTitle(title);
            if(omdbMovies.length > 0) {
                movies = movieConverter.convertToDBMovies(omdbMovies);
                dbHandler.addMovies(movies);
            }
            else {
                return null;
            }
        }
        return movies;
    }
    public DBMovie getMovie(String imdbId) {
        DBMovie movie = dbHandler.getMovieByImdbId(imdbId);
        if(movie == null) {
            OMDBMovie omdbMovie = omdbHandler.getMovieByImdbId(imdbId);
            if(omdbMovie != null) {
                movie = movieConverter.convertToDBMovie(omdbMovie);
                dbHandler.addMovie(movie);
            }
            else {
                return null;
            }
        }
        return movie;
    }

    public DBActor getDBActors(String input, String role) {
        DBActor actor = dbHandler.getActorByName(input, role);
        return actor;
    }
    public DBMovie[] getActorsMovies(DBActor actor, String role) {
        DBMovie[] movies = dbHandler.getMoviesByActor(actor, role);
        return movies;
    }
}
