package com.khizar.codingchallenge.busroute.service;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by khizar on 06.11.16.
 */
public class BusRouteServiceTest {


    @Test
    public void testInitializeRoutes() {
        BusRouteService routeService = new BusRouteService();

        // Should be null at start
        LinkedList<LinkedHashSet<Integer>> busRoutes = routeService.getBusRoutes();
        assertNull(busRoutes);

        //Initialized
        routeService.initializeRoutes("test/data/test-data.txt");
        busRoutes = routeService.getBusRoutes();

        assertTrue(busRoutes.size() == 3);
        assertTrue(busRoutes.get(0).size() == 5);
        assertTrue(busRoutes.get(2).size() == 3);

        //No change if null passed
        routeService.initializeRoutes(null);
        assertTrue(busRoutes.equals(routeService.getBusRoutes()));
    }

    @Test
    public void testIsDirectRouteAvailable() {
        BusRouteService routeService = new BusRouteService();

        //wrong inputs before init
        assertFalse(routeService.isDirectRouteAvailable(null, 2));
        assertFalse(routeService.isDirectRouteAvailable(2, null));
        assertFalse(routeService.isDirectRouteAvailable(null, null));


        routeService.initializeRoutes("test/data/test-data.txt");

        //wrong inputs after init
        assertFalse(routeService.isDirectRouteAvailable(null, 2));
        assertFalse(routeService.isDirectRouteAvailable(2, null));
        assertFalse(routeService.isDirectRouteAvailable(null, null));

        assertTrue(routeService.isDirectRouteAvailable(3, 6));
        assertFalse(routeService.isDirectRouteAvailable(280, 123));

    }
}
