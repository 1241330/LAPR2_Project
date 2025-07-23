package pt.ipp.isep.dei.ui.console.utils;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.*;
import static guru.nidi.graphviz.model.Factory.mutNode;

/**
 * Utility class for building and visualizing adjacency matrices and railway networks.
 */
public class Utils_MDISC {

    /**
     * Builds the adjacency matrix representing connections between stations.
     * Each cell (i, j) is 1 if there is a direct connection between station i and station j, 0 otherwise.
     *
     * @param stationList    List of all stations.
     * @param availableLines List of available railway lines connecting stations.
     * @return Adjacency matrix as a 2D int array.
     */
    public static int[][] buildAdjacencyMatrix(List<Station> stationList, List<RailwayLine> availableLines) {
        int stationCount = stationList.size();
        int[][] adjacencyMatrix = new int[stationCount][stationCount];
        int[] positions;

        for (RailwayLine line : availableLines) {
            positions = getStationPositionsInList(stationList, line);
            adjacencyMatrix[positions[0]][positions[1]] = 1;
            adjacencyMatrix[positions[1]][positions[0]] = 1;
        }

        return adjacencyMatrix;
    }

    /**
     * Gets the positions (indices) of the two stations connected by a railway line in the station list.
     *
     * @param stationList List of all stations.
     * @param line        Railway line connecting two stations.
     * @return Array of two integers with the positions of the stations in the list.
     */
    public static int[] getStationPositionsInList(List<Station> stationList, RailwayLine line) {
        int[] positions = new int[2];
        List<Station> lineStations = line.getStations();

        for (int i = 0; i < 2; i++) {
            String stationName = lineStations.get(i).getName();
            for (int j = 0; j < stationList.size(); j++) {
                if (stationList.get(j).getName().equals(stationName)) {
                    positions[i] = j;
                    break;
                }
            }
        }

        return positions;
    }

    /**
     * Visualizes the railway network using GraphStream, displaying different types of stations and lines.
     *
     * @param dStations List of D-type stations (small, green).
     * @param sStations List of S-type stations (medium, blue).
     * @param tStations List of T-type stations (large, red).
     * @param lines     List of railway lines.
     */
    public static void visualizeRailwayNetworkStream(List<Station> dStations, List<Station> sStations, List<Station> tStations, List<RailwayLine> lines) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Railway Network");

        graph.setAttribute("ui.stylesheet",
                "node.stationT { fill-color: red; size: 40px; text-alignment: under; stroke-mode: plain; stroke-color: black; stroke-width: 3px; }" +
                "node.stationS { fill-color: blue; size: 30px; text-alignment: under; stroke-mode: plain; stroke-color: black; stroke-width: 2px; }" +
                "node.stationD { fill-color: green; size: 20px; text-alignment: under; stroke-mode: plain; stroke-color: black; stroke-width: 2px; }" +
                "edge.nonElectrified { fill-color: black; size: 2px; }" +
                "edge.electrified { fill-color: orange; size: 2px; }"
        );

        // Add T-type stations (large, red)
        for (Station station : tStations) {
            Node node = graph.addNode(station.getName());
            node.setAttribute("ui.label", station.getName());
            node.setAttribute("ui.class", "stationT");
        }

        // Add S-type stations (medium, blue)
        for (Station station : sStations) {
            if (graph.getNode(station.getName()) == null) {
                Node node = graph.addNode(station.getName());
                node.setAttribute("ui.label", station.getName());
                node.setAttribute("ui.class", "stationS");
            }
        }

        // Add D-type stations (small, green)
        for (Station station : dStations) {
            if (graph.getNode(station.getName()) == null) {
                Node node = graph.addNode(station.getName());
                node.setAttribute("ui.label", station.getName());
                node.setAttribute("ui.class", "stationD");
            }
        }

        // Add edges (railway lines)
        for (RailwayLine line : lines) {
            String id = line.getNameStation1() + "-" + line.getNameStation2();
            if (graph.getEdge(id) == null && graph.getEdge(line.getNameStation2() + "-" + line.getNameStation1()) == null) {
                Edge edge = graph.addEdge(id, line.getNameStation1(), line.getNameStation2());
                if (line.getTypeEnum().getType().toLowerCase().contains("non")) {
                    edge.setAttribute("ui.class", "nonElectrified");
                } else {
                    edge.setAttribute("ui.class", "electrified");
                }
            }
        }

        graph.display();
    }

    /**
     * Visualizes the railway network and exports it as a PNG file using Graphviz.
     *
     * @param stationList List of all stations.
     * @param lines       List of railway lines.
     * @param nameFile    Name of the output file (without extension).
     * @throws IOException If an error occurs during file writing.
     */
    public static void visualizeRailwayNetworkFile(List<Station> stationList, List<RailwayLine> lines, String nameFile) throws IOException {
        MutableGraph graph = mutGraph("RailwayNetwork").setDirected(false)
                .graphAttrs()
                .add(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT), GraphAttr.pad(0.5));

        // Add nodes (stations)
        for (Station station : stationList) {
            MutableNode node = mutNode(station.getName())
                    .add(Shape.CIRCLE)
                    .add(Style.FILLED)
                    .add(Color.rgb("3498db"))
                    .add(Font.name("Arial"))
                    .add(Font.size(10))
                    .add(Color.WHITE.font())
                    .add("width", "0.2")
                    .add("height", "0.2");
            graph.add(node);
        }

        // Add edges (railway lines)
        for (RailwayLine line : lines) {
            String station1 = line.getNameStation1();
            String station2 = line.getNameStation2();
            boolean isNonElectrified = line.getTypeEnum().getType().toLowerCase().contains("non");
            String lineColor = isNonElectrified ? "#7f8c8d" : "#2ecc71";

            graph.add(
                    mutNode(station1).addLink(
                            to(mutNode(station2))
                                    .with("color", lineColor)
                                    .with("style", "bold")
                                    .with("fontsize", "12")
                                    .with("weight", "20")
                    )
            );
        }

        File outputDir = new File("MDISC/US-SPRINT 2/Generated Graphs");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File outputFile = new File(outputDir, nameFile + ".png");

        Graphviz.fromGraph(graph)
                .render(Format.PNG)
                .toFile(outputFile);

        Utils.printMessage("Railway network rendered in '" + nameFile + "'");
    }

    /**
     * Visualizes the shortest route in the railway network using GraphStream, highlighting used lines.
     *
     * @param dStations List of D-type stations (small, green).
     * @param sStations List of S-type stations (medium, blue).
     * @param tStations List of T-type stations (large, red).
     * @param lines     List of all railway lines.
     * @param usedLines List of railway lines used in the shortest route.
     */
    public static void visualizeShortestRouteStream(List<Station> dStations, List<Station> sStations, List<Station> tStations,
                                                   List<RailwayLine> lines, List<RailwayLine> usedLines) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Railway Network");

        graph.setAttribute("ui.stylesheet",
                "node.stationT { fill-color: red; size: 40px; text-alignment: under; stroke-mode: plain; stroke-color: black; stroke-width: 3px; }" +
                "node.stationS { fill-color: blue; size: 30px; text-alignment: under; stroke-mode: plain; stroke-color: black; stroke-width: 2px; }" +
                "node.stationD { fill-color: green; size: 20px; text-alignment: under; stroke-mode: plain; stroke-color: black; stroke-width: 2px; }" +
                "edge.nonElectrified { fill-color: black; size: 2px; }" +
                "edge.electrified { fill-color: orange; size: 2px; }" +
                "edge.usedLine { fill-color: purple; size: 4px; }"
        );

        // Add T-type stations
        for (Station station : tStations) {
            Node node = graph.addNode(station.getName());
            node.setAttribute("ui.label", station.getName());
            node.setAttribute("ui.class", "stationT");
        }

        // Add S-type stations
        for (Station station : sStations) {
            if (graph.getNode(station.getName()) == null) {
                Node node = graph.addNode(station.getName());
                node.setAttribute("ui.label", station.getName());
                node.setAttribute("ui.class", "stationS");
            }
        }

        // Add D-type stations
        for (Station station : dStations) {
            if (graph.getNode(station.getName()) == null) {
                Node node = graph.addNode(station.getName());
                node.setAttribute("ui.label", station.getName());
                node.setAttribute("ui.class", "stationD");
            }
        }

        // Add edges (railway lines)
        for (RailwayLine line : lines) {
            String id = line.getNameStation1() + "-" + line.getNameStation2();
            String reverseId = line.getNameStation2() + "-" + line.getNameStation1();
            if (graph.getEdge(id) == null && graph.getEdge(reverseId) == null) {
                Edge edge = graph.addEdge(id, line.getNameStation1(), line.getNameStation2());

                if (usedLines.contains(line)) {
                    edge.setAttribute("ui.class", "usedLine");
                } else if (line.getTypeEnum().getType().toLowerCase().contains("non")) {
                    edge.setAttribute("ui.class", "nonElectrified");
                } else {
                    edge.setAttribute("ui.class", "electrified");
                }
            }
        }

        graph.display();
    }

    /**
     * Visualizes the shortest route in the railway network and exports it as a PNG file using Graphviz.
     * Used lines are highlighted with different colors.
     *
     * @param stationList List of all stations.
     * @param lines       List of all railway lines.
     * @param usedLines   List of railway lines used in the shortest route.
     * @param nameFile    Name of the output file (without extension).
     * @throws IOException If an error occurs during file writing.
     */
    public static void visualizeShortestRouteFile(List<Station> stationList, List<RailwayLine> lines, List<RailwayLine> usedLines, String nameFile) throws IOException {
        MutableGraph graph = mutGraph("RailwayNetwork").setDirected(false)
                .graphAttrs()
                .add(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT), GraphAttr.pad(0.5));

        // Add nodes (stations)
        for (Station station : stationList) {
            MutableNode node = mutNode(station.getName())
                    .add(Shape.CIRCLE)
                    .add(Style.FILLED)
                    .add(Color.rgb("3498db"))
                    .add(Font.name("Arial"))
                    .add(Font.size(10))
                    .add(Color.WHITE.font())
                    .add("width", "0.2")
                    .add("height", "0.2");
            graph.add(node);
        }

        // Add edges (railway lines)
        for (RailwayLine line : lines) {
            String station1 = line.getNameStation1();
            String station2 = line.getNameStation2();
            boolean isNonElectrified = line.getTypeEnum().getType().toLowerCase().contains("non");
            String lineColor = isNonElectrified ? "#7f8c8d" : "#2ecc71";

            graph.add(
                    mutNode(station1).addLink(
                            to(mutNode(station2))
                                    .with("color", lineColor)
                                    .with("style", "bold")
                                    .with("fontsize", "12")
                                    .with("weight", "20")
                    )
            );
        }

        // Highlight used lines with different colors
        int index = 0;
        String baseColor = "#e74c3c";
        for (RailwayLine line : usedLines) {
            String station1 = line.getNameStation1();
            String station2 = line.getNameStation2();
            String lineColor = (index % 2 == 0) ? "#c78148" : baseColor;

            graph.add(
                    mutNode(station1).addLink(
                            to(mutNode(station2))
                                    .with("color", lineColor)
                                    .with("style", "bold")
                                    .with("fontsize", "12")
                                    .with("weight", "20")
                    )
            );
            index++;
        }

        File outputDir = new File("MDISC/US-SPRINT 3/Generated Graphs");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File outputFile = new File(outputDir, nameFile + ".png");

        Graphviz.fromGraph(graph)
                .render(Format.PNG)
                .toFile(outputFile);

        Utils.printMessage("Railway network rendered in '" + nameFile + "'");
    }
}