package com.khizar.codingchallenge.busroute.Utils.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khizar on 06.11.16.
 */
public class RouteAvailableResponse {
    @SerializedName("dep_sid")
    private String departureStationId;

    @SerializedName("arr_sid")
    private String arrivalStationId;

    @SerializedName("direct_bus_route")
    private boolean isDirectRouteAvailable;

    public String getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(String departureStationId) {
        this.departureStationId = departureStationId;
    }

    public String getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(String arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public boolean isDirectRouteAvailable() {
        return isDirectRouteAvailable;
    }

    public void setDirectRouteAvailable(boolean directRouteAvailable) {
        isDirectRouteAvailable = directRouteAvailable;
    }


}
