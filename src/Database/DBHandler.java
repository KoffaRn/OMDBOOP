package Database;

import java.sql.*;
import java.time.LocalDate;

import static java.lang.String.valueOf;

public class DBHandler {
    Connection conn;
    String dbName;

    public DBHandler(String dbName) {
        this.dbName = dbName;
        Statement statement;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS movies (imdbID VARCHAR(20) PRIMARY KEY, title TEXT, year TEXT, rated TEXT, released TEXT, runtime INTEGER, plot TEXT, language TEXT, country TEXT, awards TEXT, poster TEXT, metascore INTEGER, imdbRating DOUBLE, imdbVotes DOUBLE, type TEXT, dvd TEXT, boxOffice TEXT, production TEXT, website TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS genres (imdbID VARCHAR(20), genre TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS actors (imdbID VARCHAR(20), name TEXT, role TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS ratings (imdbID VARCHAR(20), source TEXT, value TEXT)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public boolean addMovie(DBMovie dbMovie) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO movies (imdbID, title, year, rated, released, runtime, plot, language, country, awards, poster, metascore, imdbRating, imdbVotes, type, dvd, boxOffice, production, website) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, dbMovie.getImdbID());
            ps.setString(2, dbMovie.getTitle());
            ps.setString(3, dbMovie.getYear());
            ps.setString(4, dbMovie.getRated());
            if(dbMovie.getReleased() == null) {
                ps.setString(5, null);
            } else {
                ps.setString(5, dbMovie.getReleased().toString());
            }
            ps.setInt(6, dbMovie.getRuntime());
            ps.setString(7, dbMovie.getPlot());
            ps.setString(8, dbMovie.getLanguage());
            ps.setString(9, dbMovie.getCountry());
            ps.setString(10, dbMovie.getAwards());
            ps.setString(11, dbMovie.getPoster());
            ps.setInt(12, dbMovie.getMetaScore());
            ps.setDouble(13, dbMovie.getImdbRating());
            ps.setDouble(14, dbMovie.getImdbVotes());
            ps.setString(15, dbMovie.getType());
            ps.setString(16, valueOf(dbMovie.getDVD()));
            ps.setString(17, dbMovie.getBoxOffice());
            ps.setString(18, dbMovie.getProduction());
            ps.setString(19, dbMovie.getWebsite());
            ps.executeUpdate();
            for(DBGenre dbGenre : dbMovie.getGenres()) {
                boolean success = addGenre(dbGenre);
                if(!success) {
                    return false;
                }
            }
            for(DBActor dbActor : dbMovie.getActors()) {
                boolean success = addActor(dbActor);
                if(!success) {
                    return false;
                }
            }
            for(DBRating dbRating : dbMovie.getRatings()) {
                boolean success = addRating(dbRating);
                if(!success) {
                    return false;
                }
            }
            System.out.println(dbMovie.getTitle() + " added to database");
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    private boolean addRating(DBRating dbRating) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ratings (imdbID, source, value) VALUES (?, ?, ?)");
            ps.setString(1, dbRating.getImdbId());
            ps.setString(2, dbRating.getSource());
            ps.setString(3, dbRating.getValue());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    private boolean addActor(DBActor dbActor) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO actors (imdbID, name, role) VALUES (?, ?, ?)");
            ps.setString(1, dbActor.getImdbId());
            ps.setString(2, dbActor.getName());
            ps.setString(3, valueOf(dbActor.getDbActorRole()));
            ps.executeUpdate();
            System.out.println(dbActor.getName() + " added to database");
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    private boolean addGenre(DBGenre dbGenre) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO genres (imdbID, genre) VALUES (?, ?)");
            ps.setString(1, dbGenre.getImdbId());
            ps.setString(2, dbGenre.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public DBMovie[] getMoviesByTitle(String qtitle) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM movies WHERE title LIKE ?");
            ps.setString(1, "%" + qtitle + "%");
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while(rs.next()) {
                count++;
            }
            DBMovie[] dbMovies = new DBMovie[count];
            rs = ps.executeQuery();
            int i = 0;
            while(rs.next()) {
                DBMovie dbMovie = getMovieByImdbId(rs.getString("imdbID"));
                dbMovies[i] = dbMovie;
                i++;
            }
            return dbMovies;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private DBRating[] getRatingsByImdbId(String imdbID) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ratings WHERE imdbID = ?");
            ps.setString(1, imdbID);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while(rs.next()) {
                count++;
            }
            DBRating[] dbRatings = new DBRating[count];
            rs = ps.executeQuery();
            int i = 0;
            while(rs.next()) {
                DBRating dbRating = new DBRating();
                dbRating.setSource(rs.getString("source"));
                dbRating.setValue(rs.getString("value"));
                dbRatings[i] = dbRating;
                i++;
            }
            return dbRatings;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private DBActor[] getActorsByImdbId(String imdbID) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM actors WHERE imdbID = ?");
            ps.setString(1, imdbID);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while(rs.next()) {
                count++;
            }
            DBActor[] dbActors = new DBActor[count];
            rs = ps.executeQuery();
            int i = 0;
            while(rs.next()) {
                DBActor dbActor = new DBActor();
                dbActor.setName(rs.getString("name"));
                dbActor.setDbActorRole(Roles.valueOf(rs.getString("role")));
                dbActors[i] = dbActor;
                i++;
            }
            return dbActors;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private DBGenre[] getGenresByImdbId(String imdbID) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM genres WHERE imdbID = ?");
            ps.setString(1, imdbID);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while(rs.next()) {
                count++;
            }
            DBGenre[] dbGenres = new DBGenre[count];
            rs = ps.executeQuery();
            int i = 0;
            while(rs.next()) {
                DBGenre dbGenre = new DBGenre();
                dbGenre.setName(rs.getString("genre"));
                dbGenres[i] = dbGenre;
                i++;
            }
            return dbGenres;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    public DBMovie getMovieByImdbId(String imdbId) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM movies WHERE imdbID = ?");
            ps.setString(1, imdbId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                DBMovie dbMovie = new DBMovie();
                dbMovie.setYear(rs.getString("year"));
                dbMovie.setType(rs.getString("type"));
                dbMovie.setTitle(rs.getString("title"));
                dbMovie.setRuntime(rs.getInt("runtime"));
                if(rs.getString("released") == null) {
                    dbMovie.setReleased(null);
                }
                else {
                    dbMovie.setReleased(LocalDate.parse(rs.getString("released")));
                }
                dbMovie.setPlot(rs.getString("plot"));
                dbMovie.setWebsite(rs.getString("website"));
                dbMovie.setProduction(rs.getString("production"));
                dbMovie.setPoster(rs.getString("poster"));
                dbMovie.setImdbVotes(rs.getLong("imdbVotes"));
                dbMovie.setImdbRating(rs.getDouble("imdbRating"));
                dbMovie.setImdbID(rs.getString("imdbID"));
                dbMovie.setLanguage(rs.getString("language"));
                if(!rs.getString("dvd").equals("null")) {
                    dbMovie.setDVD(LocalDate.parse(rs.getString("dvd")));
                }
                else {
                    dbMovie.setDVD(null);
                }
                dbMovie.setCountry(rs.getString("country"));
                dbMovie.setBoxOffice(rs.getString("boxOffice"));
                dbMovie.setAwards(rs.getString("awards"));
                dbMovie.setRated(rs.getString("rated"));
                dbMovie.setMetaScore(rs.getInt("metaScore"));
                PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM genres WHERE imdbID = ?");
                ps2.setString(1, imdbId);
                ResultSet rs2 = ps2.executeQuery();
                while(rs2.next()) {
                    dbMovie.addGenre(new DBGenre(rs2.getString("genre"), rs2.getString("imdbID")));
                }
                PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM actors WHERE imdbID = ?");
                ps3.setString(1, imdbId);
                ResultSet rs3 = ps3.executeQuery();
                while(rs3.next()) {
                    dbMovie.addActor(new DBActor(rs3.getString("name"), rs3.getString("imdbID"), valueOf(rs3.getString("role"))));
                }
                PreparedStatement ps4 = conn.prepareStatement("SELECT * FROM ratings WHERE imdbID = ?");
                ps4.setString(1, imdbId);
                ResultSet rs4 = ps4.executeQuery();
                while(rs4.next()) {
                    dbMovie.addRating(new DBRating(rs4.getString("source"), rs4.getString("value"), rs4.getString("imdbID")));
                }
                return dbMovie;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void addMovies(DBMovie[] movies) {
        for(DBMovie movie : movies) {
            addMovie(movie);
        }
    }
    public DBActor getActorByName(String input, String role) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT name, role FROM actors WHERE name LIKE ? AND role = ?");
            ps.setString(1, "%" + input + "%");
            ps.setString(2, role);
            ResultSet rs = ps.executeQuery();
            DBActor dbActor = new DBActor();
            dbActor.setName(rs.getString("name"));
            dbActor.setDbActorRole(Roles.valueOf(rs.getString("role")));
            return dbActor;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    public DBMovie[] getMoviesByActor(DBActor actor, String role) {
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("SELECT * FROM actors WHERE name LIKE ? AND role = ?");
            ps.setString(1, actor.getName());
            ps.setString(2, role);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while(rs.next()) {
                count++;
            }
            DBMovie[] dbMovies = new DBMovie[count];
            rs = ps.executeQuery();
            int i = 0;
            while(rs.next()) {
                dbMovies[i] = getMovieByImdbId(rs.getString("imdbID"));
                i++;
            }
            return dbMovies;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}