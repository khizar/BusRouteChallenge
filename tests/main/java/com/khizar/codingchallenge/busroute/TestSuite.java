package main.java.com.khizar.codingchallenge.busroute;

import main.java.com.khizar.codingchallenge.busroute.Application.ApplicationTest;
import main.java.com.khizar.codingchallenge.busroute.Utils.BusRouteWatcherTest;
import main.java.com.khizar.codingchallenge.busroute.service.BusRouteServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/*
*
* Created by khizar on 07.11.16.
*/



@Suite.SuiteClasses({BusRouteWatcherTest.class, BusRouteServiceTest.class, ApplicationTest.class})
@RunWith(Suite.class)
public class TestSuite {
}
