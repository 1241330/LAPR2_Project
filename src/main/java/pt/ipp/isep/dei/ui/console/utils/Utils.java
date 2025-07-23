package pt.ipp.isep.dei.ui.console.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Map.MapElement;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;

import static guru.nidi.graphviz.model.Factory.*;

/**
 * Utility class providing various static methods for console input/output, menu display,
 * data conversion, validation, and map visualization for the application.
 */
public class Utils {
    /** Logger for the Utils class. */
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
    /** Minimum menu length for display formatting. */
    private final static int MINIMUM_LENGTH_MENU = 40;
    /** Right margin for menu display formatting. */
    private final static int RIGHT_AESTHETIC_MENU = 10;
    /** Hit size for menu options >= 10. */
    private final static int HIT_SIZE_BIGGER_NINE = 6;
    /** Hit size for menu options < 10. */
    private final static int HIT_SIZE_SMALLER_NINE = 5;

    /**
     * Reads a line from the console with a prompt.
     * @param prompt the prompt to display
     * @return the input string, or null if an error occurs
     */
    static public String readLineFromConsole(String prompt) {
        try {
            System.out.print(prompt);
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Reads an integer from the console with a prompt.
     * @param prompt the prompt to display
     * @return the integer value entered by the user
     */
    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                if (input == null) {
                    throw new IllegalArgumentException("Input cannot be null");
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                printMessage("< Invalid input format. Please enter a valid integer >");
            }
        } while (true);
    }

    /**
     * Reads a double from the console with a prompt.
     * @param prompt the prompt to display
     * @return the double value entered by the user
     */
    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                if (input == null) {
                    throw new IllegalArgumentException("Input cannot be null");
                }
                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                printMessage("< Invalid input format. Please enter a valid decimal number >");
            }
        } while (true);
    }

    /**
     * Reads a date from the console in DD-MM-YYYY format or returns null if canceled.
     * @param prompt the prompt to display
     * @return a TimeDate object or null if canceled
     */
    public static TimeDate readDateFromConsole(String prompt) {
        while (true) {
            String input = readLineFromConsole(prompt + " (DD-MM-YYYY or 0 to cancel): ");

            if (input.equals("0")) {
                return null;
            }

            try {
                String[] parts = input.split("-");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid format");
                }

                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);
                final int MINIMUM_MONTH_YEAR = 1;
                final int MAXIMUM_MONTH_YEAR = 12;
                if (month < MINIMUM_MONTH_YEAR || month > MAXIMUM_MONTH_YEAR) {
                    throw new IllegalArgumentException("Month must be between 1 and 12");
                }

                return new TimeDate(year, month, day);

            } catch (Exception e) {
                Utils.printMessage("< Invalid input format. Please enter a valid date in DD-MM-YYYY format >");
            }
        }
    }

    /**
     * Shows a list and allows the user to select one object.
     * @param list the list of objects
     * @param header the header to display
     * @return the selected object or null if canceled
     */
    static public Object showAndSelectOne(List list, String header) {
        if (list == null || header == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        showList(list, header);
        return selectsObject(list);
    }

    /**
     * Converts a number of days to a TimeDate object.
     * @param totalDays the total number of days
     * @return the corresponding TimeDate object
     */
    static public TimeDate convertToDate(int totalDays){
        if (totalDays < 0) {
            throw new IllegalArgumentException("Total days cannot be negative");
        }
        int[] DAYS_PER_MONTH = {0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int day = 1;
        int month = 1;
        int year = 1;
        for (int i = 0; i < totalDays; i++) {
            if (isLeapYear(year)) {
                DAYS_PER_MONTH[2] = 29; // Leap year
            } else {
                DAYS_PER_MONTH[2] = 28; // Non-leap year
            }
            day++;
            if (day > DAYS_PER_MONTH[month]) {
                day = 1;
                month++;
                if (month > 12) {
                    month = 1;
                    year++;
                }
            }
        }
        return new TimeDate(year, month, day);
    }

    /**
     * Checks if a year is a leap year.
     * @param year the year to check
     * @return true if leap year, false otherwise
     */
    static public boolean isLeapYear(int year) {
        return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
    }

    /**
     * Shows a list and allows the user to select an index.
     * @param list the list of objects
     * @param header the header to display
     * @return the selected index or -1 if canceled
     */
    static public int showAndSelectIndex(List list, String header) {
        if (list == null || header == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        showList(list, header);
        return selectsIndex(list);
    }

    /**
     * Shows a list with a header.
     * @param list the list of objects
     * @param header the header to display
     */
    static public void showList(List list, String header) {
        if (list == null || header == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;
            System.out.println("  " + index + " - " + o.toString());
        }
        System.out.println("  0 - Cancel");
    }

    /**
     * Allows the user to select an object from a list.
     * @param list the list of objects
     * @return the selected object or null if canceled
     */
    static public Object selectsObject(List list) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            try {
                value = Integer.parseInt(input);
                if (value < 0 || value > list.size()) {
                    System.out.println("Please enter a number between 0 and " + list.size());
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                value = -1;
            }
        } while (value < 0 || value > list.size());

        return value == 0 ? null : list.get(value - 1);
    }

    /**
     * Allows the user to select an index from a list.
     * @param list the list of objects
     * @return the selected index or -1 if canceled
     */
    static public int selectsIndex(List list) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            try {
                value = Integer.parseInt(input);
                if (value < 0 || value > list.size()) {
                    System.out.println("Please enter a number between 0 and " + list.size());
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                value = -1;
            }
        } while (value < 0 || value > list.size());

        return value - 1;
    }

    /**
     * Reads an integer from the console within a specified range.
     * @param prompt the prompt to display
     * @param min the minimum value
     * @param max the maximum value
     * @return the integer value within the range
     */
    static public int readIntegerInRange(String prompt, int min, int max) {
        if (prompt == null) {
            throw new IllegalArgumentException("Prompt cannot be null");
        }
        if (min > max) {
            throw new IllegalArgumentException("Min value cannot be greater than max value");
        }
        int value;
        do {
            value = Utils.readIntegerFromConsole(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            printMessage("< Value must be between " + min + " and " + max + " >");
        } while (true);
    }

    /**
     * Reads a single word (no spaces) from the console.
     * @param prompt the prompt to display
     * @return the word entered by the user
     */
    static public String readSimpleWord(String prompt) {
        if (prompt == null) {
            throw new IllegalArgumentException("Prompt cannot be null");
        }
        do {
            String text = readLineFromConsole(prompt);
            if (text == null) {
                printMessage("< Input cannot be empty >");
                continue;
            }
            text = text.trim();
            if (text.contains(" ")) {
                printMessage("< Only one word, no spaces >");
                continue;
            }
            boolean isValid = true;
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (!(Character.isLetterOrDigit(c) || c == '_') || (c >= 0x00C0 && c <= 0x00FF)) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                return text;
            }
            printMessage("< Invalid formatting >");
        } while (true);
    }

    /**
     * Reads and validates a name (letters and spaces only) from the console.
     * @param prompt the prompt to display
     * @return the validated name
     */
    static public String validName(String prompt) {
        String text;
        boolean valida = false;
        do {
            text = Utils.readLineFromConsole(prompt);
            if (text == null || text.isEmpty()) {
                printMessage("< Name can't be empty >");
                continue;
            }
            if (text.equals("0")) {
                return text;
            }
            valida = true;
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                // Permitir letras e espaços
                if (!Character.isLetter(c) && c != ' ') {
                    valida = false;
                    break;
                }
            }
            if (!valida) {
                Utils.printMessage("< Invalid input. Please use letters and spaces only, no numbers or symbols >");
            } else {
                // Verificar se tem pelo menos uma letra (não só espaços)
                if (text.trim().isEmpty()) {
                    Utils.printMessage("< Name can't be only spaces >");
                    valida = false;
                }
            }
        } while (!valida);
        text = text.trim();
        return text;
    }

    /**
     * Converts a list of objects to a list of their string descriptions.
     * @param objects the list of objects
     * @return a list of string descriptions
     */
    public static List<String> convertObjectsToDescriptions(List<?> objects) {
        if (objects == null) {
            throw new IllegalArgumentException("Objects list cannot be null");
        }
        List<String> descriptions = new ArrayList<>();
        for (Object obj : objects) {
            descriptions.add(obj.toString());
        }
        return descriptions;
    }

    /**
     * Prints a menu and allows the user to choose an option, with a manual return option.
     * @param tittle the menu title
     * @param options the list of options
     * @param nameReturn the name for the return option
     * @param textAnswer the prompt for the answer
     * @return the selected option index
     */
    static public int chooseOptionPrintMenuAndManualReturn(String tittle, List<String> options, String nameReturn, String textAnswer) {
        if (tittle == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        // Determinar o maior comprimento
        int maxLineLength = tittle.length();
        if (options != null) {
            for (String option : options) {
                if (option != null && option.length() > maxLineLength) {
                    maxLineLength = option.length();
                }
            }
        }
        if (nameReturn != null && nameReturn.length() > maxLineLength) {
            maxLineLength = nameReturn.length();
        }

        int length = Math.max(MINIMUM_LENGTH_MENU, maxLineLength + RIGHT_AESTHETIC_MENU); // Garante mínimo de 40

        // Cabeçalho
        System.out.println("\n╔" + "═".repeat(length) + "╗");
        int spaces = (length - tittle.length()) / 2;
        System.out.println("║" + " ".repeat(spaces) + tittle + " ".repeat(length - spaces - tittle.length()) + "║");
        System.out.println("╠" + "═".repeat(length) + "╣");

        int minOption = 1;
        int maxOption = 0;
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                int space;
                if (i >= 9) {
                    space = length - HIT_SIZE_BIGGER_NINE;
                } else {
                    space = length - HIT_SIZE_SMALLER_NINE;
                }
                String line = String.format("  %d. %-"+(space)+"s", i + 1, options.get(i)); // Ajuste
                System.out.println("║" + line + "║");
            }
            maxOption = options.size();
        }

        if (nameReturn != null) {
            String lineReturn = String.format("  0. %-"+(length-5)+"s", nameReturn);
            System.out.println("║" + lineReturn + "║");
            minOption = 0;
        }

        System.out.println("╚" + "═".repeat(length) + "╝");

        if (minOption > maxOption) {
            return 0;
        }

        if (textAnswer != null) {
            return readIntegerInRange(textAnswer + ": ", minOption, maxOption);
        }

        return 0;
    }

    /**
     * Prints a menu with budget and time, and allows the user to choose an option.
     * @param tittle the menu title
     * @param options the list of options
     * @param nameReturn the name for the return option
     * @param textAnswer the prompt for the answer
     * @param dinheiro the budget
     * @param tempoAtual the current time
     * @return the selected option index
     */
    static public int resumeMenuReturn(String tittle, List<String> options, String nameReturn, String textAnswer, int dinheiro, TimeDate tempoAtual) {
        if (tittle == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        // Determinar o maior comprimento
        int maxLineLength = tittle.length();
        if (options != null) {
            for (String option : options) {
                if (option != null && option.length() > maxLineLength) {
                    maxLineLength = option.length();
                }
            }
        }
        if (nameReturn != null && nameReturn.length() > maxLineLength) {
            maxLineLength = nameReturn.length();
        }

        // Formatar saldo e tempo
        String saldoTempo = "Budget: " + dinheiro + " €   |  " + tempoAtual;
        if (saldoTempo.length() > maxLineLength) {
            maxLineLength = saldoTempo.length();
        }

        int length = Math.max(MINIMUM_LENGTH_MENU, maxLineLength + RIGHT_AESTHETIC_MENU); // Garante mínimo de 40

        // Cabeçalho
        System.out.println("╔" + "═".repeat(length) + "╗");

        // Linha de saldo e tempo atual
        int espacosSaldo = (length - saldoTempo.length()) / 2;
        System.out.println("║" + " ".repeat(espacosSaldo) + saldoTempo + " ".repeat(length - espacosSaldo - saldoTempo.length()) + "║");

        // Separador
        System.out.println("╠" + "═".repeat(length) + "╣");

        // Título
        int spaces = (length - tittle.length()) / 2;
        System.out.println("║" + " ".repeat(spaces) + tittle + " ".repeat(length - spaces - tittle.length()) + "║");
        System.out.println("╠" + "═".repeat(length) + "╣");

        int minOption = 1;
        int maxOption = 0;
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                int space;
                if (i >= 9) {
                    space = length - HIT_SIZE_BIGGER_NINE;
                } else {
                    space = length - HIT_SIZE_SMALLER_NINE;
                }
                String line = String.format("  %d. %-"+(space)+"s", i + 1, options.get(i));
                System.out.println("║" + line + "║");
            }
            maxOption = options.size();
        }

        if (nameReturn != null) {
            String lineReturn = String.format("  0. %-"+(length-5)+"s", nameReturn);
            System.out.println("║" + lineReturn + "║");
            minOption = 0;
        }

        System.out.println("╚" + "═".repeat(length) + "╝");

        if (minOption > maxOption) {
            return 0;
        }

        if (textAnswer != null) {
            return readIntegerInRange(textAnswer + ": ", minOption, maxOption);
        }

        return 0;
    }

    /**
     * Prints a formatted menu with a title and lines.
     * @param tittle the menu title
     * @param lines the lines to display
     */
    static public void printMenu(String tittle, List<String> lines) {
        if (tittle == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        // Determinar o maior comprimento
        int maxLineLength = tittle.length();
        if (lines != null) {
            for (String line : lines) {
                if (line != null && line.length() > maxLineLength) {
                    maxLineLength = line.length();
                }
            }
        }

        int length = Math.max(MINIMUM_LENGTH_MENU, maxLineLength + RIGHT_AESTHETIC_MENU); // Garante mínimo de 40

        // Cabeçalho
        System.out.println("\n╔" + "═".repeat(length) + "╗");
        int spaces = (length - tittle.length()) / 2;
        System.out.println("║" + " ".repeat(spaces) + tittle + " ".repeat(length - spaces - tittle.length()) + "║");
        System.out.println("╠" + "═".repeat(length) + "╣");

        // Linhas do menu
        if (lines != null) {
            for (String line : lines) {
                String formattedLine = String.format("  %-"+(length-2)+"s", line);
                System.out.println("║" + formattedLine + "║");
            }
        }

        System.out.println("╚" + "═".repeat(length) + "╝");
    }

    /**
     * Prints a formatted message.
     * @param message the message to display
     */
    static public void printMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        int length = message.length();
        System.out.print("\n╔");
        System.out.print("═".repeat(length + 2));
        System.out.println("╗");
        System.out.println("║ " + message + " ║");
        System.out.print("╚");
        System.out.print("═".repeat(length + 2));
        System.out.println("╝");
    }

    /**
     * Prints a visual representation of the map.
     * @param map the map to display
     */
    static public void printMap(Map map) {
        Random random = new Random();
        int height = map.getPixelSize().getHeight();
        int width = map.getPixelSize().getWidth();
        String[][] mapa = new String[height][width];
        final double PROBABILITY_GENERATING_TREE = 0.08;
        final double PROBABILITY_GENERATING_ROCK = 0.11;
        final double PROBABILITY_GENERATING_CAMEL = 0.12;

        final int KM_PER_PIXEL = 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double a = random.nextDouble(); // mover aqui!
                mapa[y][x] = "⬜";
                if (a < PROBABILITY_GENERATING_TREE) {
                    //mapa[y][x] = "\uD83C\uDF33"; // árvore
                } else if (a < PROBABILITY_GENERATING_ROCK) {
                    // mapa[y][x] = "\uD83E\uDEA8"; // pedra
                } else if (a < PROBABILITY_GENERATING_CAMEL) {
                    //mapa[y][x] = "\uD83D\uDC2B"; // camelo
                }
            }
        }
        List<MapElement> listMapElements = map.getMapElementsUsed();
        for (MapElement element : listMapElements) {
            List<Position> listPositions = element.getOccupiedPositions();
            for (Position position : listPositions) {
                int x = position.getX();
                int y = position.getY();
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    if (element instanceof City) {
                        mapa[y][x] = "🌇";
                    }
                    if (element instanceof Industry) {
                        mapa[y][x] = "\uD83C\uDFED";
                    }
                    if (element instanceof Station) {
                        mapa[y][x] = "\uD83D\uDE89";
                    }
                    if (element instanceof RailwayLine) {
                        mapa[y][x] = "\uD83D\uDEE4\uFE0F";
                    }
                }
            }

        }

        String scale = String.format("Scale: 1 pixel = %d km", KM_PER_PIXEL);
        String mapDimensions = String.format("Map dimensions: %d km x %d km", width, height);
        System.out.println("╔═" + "═".repeat(mapDimensions.length()) + "═╗");
        System.out.printf("║ %s" + " ".repeat(mapDimensions.length() - scale.length()) + " ║\n", scale);
        System.out.printf("║ %s ║\n", mapDimensions);
        System.out.println("╚═" + "═".repeat(mapDimensions.length()) + "═╝");

        System.out.print("╔═══╦══");
        printHorizontalScaleMap(width);
        System.out.println();
        for (int y = 0; y < height; y++) {
            System.out.printf("║%3d║ ", y + 1);
            for (int x = 0; x < width; x++) {
                System.out.print(mapa[y][x]);
            }
            System.out.println(" ║");
        }
        System.out.print("╚═══╩══");
        printHorizontalScaleMap(width);
    }

    /**
     * Prints the horizontal scale for the map.
     * @param width the width of the map
     */
    static public void printHorizontalScaleMap(int width) {
        for (int x = 0; x < width; x++) {
            if ((x + 1) % 5 == 0) {
                if (x + 1 < 10) {
                    System.out.print("═══════ 0" + (x + 1) + " ");
                }  else /* if((x+1) % 20 != 0) */ {
                    System.out.print("═══════ " + (x + 1) + " ");
                } /* else{
                    System.out.print("════════ " + (x + 1) + " ");
                } */
            }
        }
    }

    /**
     * Checks if a position is occupied in a list of positions.
     * @param positionToCheck the position to check
     * @param occupiedPositions the list of occupied positions
     * @return true if occupied, false otherwise
     */
    static public boolean positionOccupiedList(Position positionToCheck, List<Position> occupiedPositions) {
        if (positionToCheck == null) {
            return true;
        }
        for (Position p2 : occupiedPositions) {
            if (positionToCheck.equalsPosition(p2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the positions of all house blocks in a list.
     * @param houseBlockList the list of house blocks
     * @return a list of positions
     */
    static public List<Position> getPositionsHouseBlock(List<HouseBlock> houseBlockList) {
        List<Position> positions = new ArrayList<>();
        for (HouseBlock houseBlock : houseBlockList) {
            positions.add(houseBlock.getPosition());
        }
        return positions;
    }

    /**
     * Calculates the Euclidean distance between two positions.
     * @param p1 the first position
     * @param p2 the second position
     * @return the Euclidean distance
     */
    static public double getEuclideanDistance(Position p1, Position p2) {
        int dx = p1.getX() - p2.getX();
        int dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Gets the connection names of a list of railway lines.
     * @param lines the list of railway lines
     * @return a list of connection names
     */
    public static List<String> getRailwayLineConnections(List<RailwayLine> lines) {
        List<String> names = new ArrayList<>();
        for (RailwayLine line : lines) {
            names.add(line.getConnectionName());
        }
        return names;
    }

    /**
     * Extracts the file name (without extension) from a path.
     * @param path the file path
     * @return the file name without extension
     */
    public static String getFileName(String path) {
        int slashIndex = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        String fileNameWithExt = path.substring(slashIndex + 1);
        int dotIndex = fileNameWithExt.lastIndexOf('.');
        return (dotIndex > 0) ? fileNameWithExt.substring(0, dotIndex) : fileNameWithExt;
    }

    /**
     * Checks if an event exists by name in a list of events.
     * @param eventName the event name
     * @param generationEventList the list of events
     * @return true if exists, false otherwise
     */
    static public boolean eventExistsByName(String eventName, List<Event> generationEventList) {
        for (Event event : generationEventList) {
            if (event.getName().equals(eventName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a file exists in a folder.
     * @param folderPath the folder path
     * @param fileName the file name
     * @return true if exists, false otherwise
     */
    static public boolean fileExistsInFolder(String folderPath, String fileName) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return false;
        }
        File[] files = folder.listFiles();
        if (files == null) {
            return false;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lists all file names in a directory.
     * @param directoryPath the directory path
     * @return a list of file names
     */
    public static List<String> listFilesInDirectory(String directoryPath) {
        List<String> fileNames = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        return fileNames;
    }

    /**
     * Converts between pixels and kilometers.
     * @param value the value to convert
     * @param toKms true to convert to kilometers, false to convert to pixels
     * @return the converted value
     */
    public static double convertBetweenPixeisAndKms(int value, boolean toKms) {
        final int PIXELS_PER_KM = 50;
        if (toKms) {
            return (double) value / PIXELS_PER_KM;
        } else {
            return value * PIXELS_PER_KM;
        }
    }

    /**
     * Checks if a file is a valid CSV file.
     * @param path the file path
     * @return true if valid CSV, false otherwise
     */
    public static boolean isValidCSVFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && path.toLowerCase().endsWith(".csv");
    }

}

