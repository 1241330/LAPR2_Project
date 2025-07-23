package pt.ipp.isep.dei;

/**
 * Main class to start the application.
 */
public class Main {
    /**
     * Main method to bootstrap the application and run the main menu.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();
        try {
            MainMenuApp menu = new MainMenuApp();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}