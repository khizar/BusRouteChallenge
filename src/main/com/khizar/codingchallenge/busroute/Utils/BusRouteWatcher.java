package com.khizar.codingchallenge.busroute.Utils;

import com.khizar.codingchallenge.busroute.service.BusRouteService;

import java.io.File;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by khizar on 06.11.16.
 */
public class BusRouteWatcher extends Thread {

    private final File file;
    private AtomicBoolean stop = new AtomicBoolean(false);
    private BusRouteService routeService = new BusRouteService();

    public BusRouteWatcher(File file) {
        this.file = file;
    }

    private boolean isStopped() {
        return stop.get();
    }

    public void stopThread() {
        stop.set(true);
    }

    private void doOnChange() {
        System.out.println("File changed. Reloading routes");
        routeService.initializeRoutes(file.getPath());

    }

    @Override
    public void run() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            Path path = file.toPath().getParent();
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (!isStopped()) {
                WatchKey key;
                try {
                    key = watcher.poll(25, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    return;
                }
                if (key == null) {
                    Thread.yield();
                    continue;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        Thread.yield();
                        continue;
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY
                            && filename.toString().equals(file.getName())) {
                        //Assuming that no other file in directory
                        //not checking file name: problems with text editors
                        //some editors make a new copy instead of modifying. User vim please ;p
                        Thread.sleep(1000);
                        doOnChange();
                    }
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
                Thread.yield();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}