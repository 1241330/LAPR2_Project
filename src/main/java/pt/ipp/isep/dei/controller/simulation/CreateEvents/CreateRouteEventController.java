package pt.ipp.isep.dei.controller.simulation.CreateEvents;

import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.RouteEvent;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for creating {@link RouteEvent} objects based on the routes
 * present in the current {@link Simulation} and {@link Scenario}.
 * <p>
 * Ensures that route events are not duplicated by name.
 */
public class CreateRouteEventController {

    private Simulation simulation;
    private Scenario scenario;
    private List<Route> routeList;
    private List<Event> routeEventList = new ArrayList<>();

    /**
     * Constructs a controller for creating route events.
     *
     * @param simulation the current simulation context
     * @param scenario   the scenario context
     */
    public CreateRouteEventController(Simulation simulation, Scenario scenario) {
        this.simulation = simulation;
        this.scenario = scenario;
        this.routeList = simulation.getRoutes();
    }

    /**
     * Sets the list of route events. Ignores null assignments.
     *
     * @param routeEventList the new list of route events
     */
    public void setRouteEventList(List<Event> routeEventList) {
        if (routeEventList != null) {
            this.routeEventList = routeEventList;
        }
    }

    /**
     * Iterates through all routes in the simulation and creates corresponding {@link RouteEvent}s.
     * Ensures that events are not duplicated by name.
     */
    public void addRouteEventsToList() {
        for (Route route : routeList) {
            String eventName = route.getName();
            RouteEvent routeEvent = new RouteEvent(
                    eventName,
                    1,
                    simulation.getCurrentTime(),
                    route,
                    simulation,
                    scenario
            );

            if (!Utils.eventExistsByName(eventName, routeEventList)) {
                routeEventList.add(routeEvent);
            }
        }
    }

    /**
     * Returns the list of {@link RouteEvent}s created by this controller.
     *
     * @return a list of route events
     */
    public List<Event> getRouteEventList() {
        return routeEventList;
    }
}