public class Main {
    public static void main(String[] args) {
        ConsoleInterface consoleInterface = new ConsoleInterface();

        while(consoleInterface.getRunning()) {
            consoleInterface.printMenu();
        }
        System.out.println("Goodbye!");
    }
}