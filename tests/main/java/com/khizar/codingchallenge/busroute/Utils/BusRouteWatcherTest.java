package main.java.com.khizar.codingchallenge.busroute.Utils;

import com.khizar.codingchallenge.busroute.Utils.BusRouteWatcher;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by khizar on 07.11.16.
 */
public class BusRouteWatcherTest {


    @Test
    public void testFileWatcher() throws IOException, InterruptedException {
        File file = new File("tests/data/watch.txt");

        final AtomicBoolean hasCalledOnChange = new AtomicBoolean();

        //Only testing if on change method call because the testInitializeRoutes tests the rest of the process
        BusRouteWatcher watcher = new BusRouteWatcher(file) {

            @Override
            protected void doOnChange() {
                System.out.println("File changed. Test should pass.");
                hasCalledOnChange.set(true);
            }
        };

        assertFalse(hasCalledOnChange.get());

        watcher.start();

        Thread.sleep(3000);

        FileWriter fw = new FileWriter(file);
        fw.write("blah");
        fw.close();

        Thread.sleep(3000);

        assertTrue(hasCalledOnChange.get());

    }

}
