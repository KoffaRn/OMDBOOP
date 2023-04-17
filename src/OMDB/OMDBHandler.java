package OMDB;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class OMDBHandler {
    String apiKey;
    String apiUrl;
    public OMDBHandler() {
        // Hämta sökvägen till användarens dokumentmapp
        String userHome = System.getProperty("user.home");

        // Läs API-nyckeln från OMDB.txt-filen i användarens dokumentmapp
        Properties props = new Properties(); // Läser ini filer
        try {
            FileInputStream input = new FileInputStream(userHome + "/Documents/APIKeys/OMDB.txt");
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        apiKey = props.getProperty("apiKey");
        apiUrl = "http://www.omdbapi.com/?apikey=" + apiKey;
    }
    private OMDBMovie getMovieFromJson(String searchResult) {
        Gson gson = new Gson();
        return gson.fromJson(searchResult, OMDBMovie.class);
    }
    public OMDBMovie[] getMoviesByTitle(String qtitle) {
        String searchUrl = apiUrl + "&s=" + qtitle;
        String searchResult = makeAPICall(searchUrl);
        searchResult = searchResult.substring(searchResult.indexOf('['));
        searchResult = searchResult.substring(0, searchResult.indexOf(']') +1);
        return getMovieArrayList(searchResult);
    }
    private String makeAPICall(String searchUrl) {
        try {
            URL url = new URL(searchUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject searchResult = getJsonObject(response.toString());
            if(searchResult.get("Response").equals("True")) {
                return searchResult.toString();
            }
            else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private JSONObject getJsonObject(String str) {
        JSONParser jsonParser = new JSONParser();
        JSONObject searchResult = null;
        try {
            searchResult = (JSONObject) jsonParser.parse(str);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return searchResult;
    }

    private OMDBMovie[] getMovieArrayList(String searchArray) {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) jsonParser.parse(searchArray);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        OMDBMovie[] movieArray = new OMDBMovie[jsonArray.size()];
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject movieObject = (JSONObject) jsonArray.get(i);
            OMDBMovie movie = getMovieFromImdbId(movieObject.get("imdbID").toString());
            movieArray[i] = movie;
        }
        return movieArray;
    }

    private OMDBMovie getMovieFromImdbId(String imdbID) {
        String searchUrl = apiUrl + "&i=" + imdbID;
        String searchResult = makeAPICall(searchUrl);
        return getMovieFromJson(searchResult);
    }

    public OMDBMovie getMovieByImdbId(String imdbId) {
        String searchUrl = apiUrl + "&i=" + imdbId;
        String searchResult = makeAPICall(searchUrl);
        return getMovieFromJson(searchResult);
    }
}