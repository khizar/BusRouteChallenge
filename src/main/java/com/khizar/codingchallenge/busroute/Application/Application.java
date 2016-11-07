package com.khizar.codingchallenge.busroute.Application;

import com.khizar.codingchallenge.busroute.Utils.BusRouteWatcher;
import com.khizar.codingchallenge.busroute.Utils.ResponseError;
import com.khizar.codingchallenge.busroute.Utils.response.RouteAvailableResponse;
import com.khizar.codingchallenge.busroute.service.BusRouteService;
import com.khizar.codingchallenge.busroute.Utils.JsonUtil;

import java.io.File;

import static spark.Spark.*;

/**
 * Created by khizar on 06.11.16.
 */
public class Application {

    private static BusRouteService routeService = new BusRouteService();

    public static void main(String[] args) {
        String filePath = "";
        if(args.length < 1){
            System.out.println("Please give the file path for the routes.");
            return;
        } else {
            filePath = args[0];
        }

        File routesFile = new File(filePath);
        routeService.initializeRoutes(filePath);
        new BusRouteWatcher(routesFile).start();

        //setting port got the service
        port(8088);

        //API - GET
        get("/api/direct", (req, res) -> {
            Integer depId= Integer.parseInt(req.queryParams("dep_sid"));
            Integer arrId= Integer.parseInt(req.queryParams("arr_sid"));
            return createRouteAvailableResponse(depId, arrId);
        }, JsonUtil.json());

        //Setting Response Type
        after((request, response) -> {
            response.type("application/json");
        });

        //Illegal argument exception handler
        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(JsonUtil.toJson(new ResponseError(e)));
        });
    }

    private static RouteAvailableResponse createRouteAvailableResponse(Integer depId, Integer arrId) {
        RouteAvailableResponse response = new RouteAvailableResponse();
        response.setDepartureStationId(depId.toString());
        response.setArrivalStationId(arrId.toString());
        response.setDirectRouteAvailable(routeService.isDirectRouteAvailable(depId,arrId));
        return response;
    }

}
