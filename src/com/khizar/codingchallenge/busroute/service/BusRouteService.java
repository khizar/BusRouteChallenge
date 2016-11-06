package com.khizar.codingchallenge.busroute.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by khizar on 06.11.16.
 */

public class BusRouteService {

    private static LinkedList<LinkedHashSet<Integer>> busRoutes;

    public boolean isDirectRouteAvailable(Integer departureStationId, Integer arrivalStationId) {
        if (!busRoutes.isEmpty() && (departureStationId != null && arrivalStationId != null)) {
            for (LinkedHashSet<Integer> route : busRoutes) {
                if (route.contains(departureStationId) && route.contains(arrivalStationId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void initializeRoutes(String fileLocation) {
        if(fileLocation == null || fileLocation.isEmpty()){
            return;
        }
        busRoutes = new LinkedList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileLocation), Charset.defaultCharset())) {
            stream.filter(s -> s.trim().length() > 1)
                    .forEach(stringRoute -> {
                        stringRoute = stringRoute.trim();
                        Set<Integer> busRoutesSet = new LinkedHashSet<>();
                        String[] busRoutesStringArray = stringRoute.substring(1).trim().split(" ");
                        Arrays.stream(busRoutesStringArray).forEach(s -> {
                            busRoutesSet.add(Integer.parseInt(s));
                        });

                        busRoutes.add((LinkedHashSet<Integer>) busRoutesSet);
                    });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Done reloading bus routes.");
    }

    public LinkedList<LinkedHashSet<Integer>> getBusRoutes() {
        return busRoutes;
    }
}
