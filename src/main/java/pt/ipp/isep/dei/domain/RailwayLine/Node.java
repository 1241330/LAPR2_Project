package pt.ipp.isep.dei.domain.RailwayLine;

import java.io.Serializable;

/**
 * Represents a node in a grid or graph, typically used for pathfinding algorithms.
 * Each node contains its coordinates, cost, heuristic estimate, and a reference to its parent node.
 */
public class Node implements Serializable {

    /** The x-coordinate of the node. */
    private int x;

    /** The y-coordinate of the node. */
    private int y;

    /** The cost to reach this node from the start node. */
    private int cost;

    /** The estimated cost from this node to the goal node (heuristic). */
    private int estimate;

    /** The parent node in the path. */
    private Node parent;

    /**
     * Constructs a Node with the specified coordinates, cost, estimate, and parent.
     *
     * @param x        the x-coordinate of the node
     * @param y        the y-coordinate of the node
     * @param cost     the cost to reach this node from the start node
     * @param estimate the estimated cost from this node to the goal node
     * @param parent   the parent node in the path
     */
    public Node(int x, int y, int cost, int estimate, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.estimate = estimate;
        this.parent = parent;
    }

    /**
     * Gets the x-coordinate of the node.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the node.
     *
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the node.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the node.
     *
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the cost to reach this node from the start node.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost to reach this node from the start node.
     *
     * @param cost the new cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets the estimated cost from this node to the goal node.
     *
     * @return the estimate
     */
    public int getEstimate() {
        return estimate;
    }

    /**
     * Sets the estimated cost from this node to the goal node.
     *
     * @param estimate the new estimate
     */
    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

    /**
     * Gets the parent node in the path.
     *
     * @return the parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Sets the parent node in the path.
     *
     * @param parent the new parent node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }
}