package com.khizar.codingchallenge.busroute;

import com.khizar.codingchallenge.busroute.Application.Application;
import com.khizar.codingchallenge.busroute.Application.ApplicationTest;
import com.khizar.codingchallenge.busroute.service.BusRouteServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by khizar on 07.11.16.
 */

@Suite.SuiteClasses({BusRouteServiceTest.class, ApplicationTest.class})
@RunWith(Suite.class)
public class TestSuite {
}
