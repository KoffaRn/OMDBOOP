import Database.DBActor;
import Database.DBMovie;

import java.util.Scanner;

public class ConsoleInterface {
    boolean running = true;
    MovieHandler movieHandler;
    public ConsoleInterface() {
        this.movieHandler = new MovieHandler();
    }
    public boolean getRunning() {
        return running;
    }
    public void printMenu() {
        System.out.println("1. Search for a movie");
        System.out.println("2. Search for an actor");
        System.out.println("3. Search for a director");
        System.out.println("4. Search for a writer");
        System.out.println("5. Exit");
        switch (takeIntInput(1, 5)) {
            case 1 -> {
                System.out.println("Enter a movie title ");
                searchMovie();
            }
            case 2 -> getActor("ACTOR");
            case 3 -> getActor("DIRECTOR");
            case 4 -> getActor("WRITER");
            case 5 -> running = false;
        }
    }
    private void getActor(String role) {
        System.out.println("Enter " + role + " name");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        DBActor result = movieHandler.getDBActors(name, role);
        if(result == null) {
            System.out.println("No results found");
        } else {
            System.out.println("Name: " + result.getName());
            DBMovie[] movies = movieHandler.getActorsMovies(result, role);
            System.out.println("Movies: " + movies.length);
            for (DBMovie movie : movies) {
                System.out.println(movie.getTitle());
            }
        }
    }
    private void searchMovie() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        System.out.println("Search for movie: " + input);
        DBMovie[] result = movieHandler.getDBMovies(input);
        if(result == null) {
            System.out.println("No results found");
        } else {
            System.out.println("Found " + result.length + " results");
            System.out.println("Enter a number to see more info about the movie");
            for (int i = 1; i < result.length + 1; i++) {
                System.out.println(i + ". " + result[i - 1].getTitle());
            }
            int choice = takeIntInput(1, result.length);
            DBMovie movie = movieHandler.getMovie(result[choice].getImdbID());
            System.out.println(movie);
        }
    }
    private int takeIntInput(int min, int max) {
        Scanner sc = new Scanner(System.in);
        try {
            int input = sc.nextInt();
            if(input > max || input < min) {
                System.out.println("Invalid input");
                return takeIntInput(max, min);
            } else {
                return input;
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
            return takeIntInput(max, min);
        }
    }
}
