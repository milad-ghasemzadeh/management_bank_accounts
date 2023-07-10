package org.management.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.*;
import java.util.List;

@Component
public class MemoryMonitor {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Scheduled(fixedRate = 30000) // Runs every 30 seconds
    public void printMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();

        long heapUsed = heapUsage.getUsed();
        long heapMax = heapUsage.getMax();
        long nonHeapUsed = nonHeapUsage.getUsed();
        long nonHeapMax = nonHeapUsage.getMax();

        logger.info("Heap Memory Used: " + heapUsed / (1024 * 1024) + " MB / " + heapMax / (1024 * 1024) + " MB");
        logger.info("Non-Heap Memory Used: " + nonHeapUsed / (1024 * 1024) + " MB / " + nonHeapMax / (1024 * 1024) + " MB");

        // Retrieve memory pool information
        List<MemoryPoolMXBean> memoryPools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pool : memoryPools) {
            logger.info("Memory Pool: " + pool.getName());
            logger.info("Usage: " + pool.getUsage());
            logger.info("Peak Usage: " + pool.getPeakUsage());
            logger.info("Collection Usage: " + pool.getCollectionUsage());
        }

        // Retrieve garbage collector information
        List<GarbageCollectorMXBean> garbageCollectors = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : garbageCollectors) {
            logger.info("Garbage Collector: " + gc.getName());
            logger.info("Collection Count: " + gc.getCollectionCount());
            logger.info("Collection Time: " + gc.getCollectionTime());
        }
    }

}

