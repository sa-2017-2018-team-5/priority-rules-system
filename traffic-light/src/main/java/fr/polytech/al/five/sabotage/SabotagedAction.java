package fr.polytech.al.five.sabotage;


import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class SabotagedAction {

    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(5);
    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = Logger.getLogger(SabotagedAction.class);
    private static final float DELAY_PROBABILITY = getFloatEnvironmentVariable("SABOTAGE_DELAY_PROBABILITY", 0.2f);
    private static final long MAXIMUM_DELAY = getLongEnvironmentVariable("SABOTAGE_MAXIMUM_DELAY", 5000);
    private static final float FAILURE_PROBABILITY = getFloatEnvironmentVariable("SABOTAGE_FAILURE_PROBABILITY", 0.1f);

    private final Runnable action;
    private final float delayProbability;
    private final long maximumDelay;
    private final float failureProbability;

    public SabotagedAction(Runnable action) {
        this(DELAY_PROBABILITY, MAXIMUM_DELAY, FAILURE_PROBABILITY, action);
    }

    public SabotagedAction(float delayProbability, long maximumDelay, float failureProbability, Runnable action) {
        this.action = action;
        this.delayProbability = delayProbability;
        this.failureProbability = failureProbability;
        this.maximumDelay = maximumDelay;
    }

    public void run() {
        LOGGER.info("[SABOTAGE] New action launched.");

        if (fails()) {
            LOGGER.info("[SABOTAGE] An action has not been send.");
            return;
        }

        LOGGER.info("[SABOTAGE] Action has not failed.");

        SERVICE.submit(() -> {
            long delay = getDelay();

            if (delay > 0) {
                try {
                    LOGGER.info("[SABOTAGE] An action is delayed by " + delay + " milliseconds.");
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                LOGGER.info("[SABOTAGE] Action has no delay.");
            }

            action.run();
        });
    }

    private boolean fails() {
        return RANDOM.nextFloat() < failureProbability;
    }

    private long getDelay() {
        if (RANDOM.nextFloat() > delayProbability) {
            return ThreadLocalRandom.current().nextLong(maximumDelay);
        } else {
            return 0;
        }
    }

    private static float getFloatEnvironmentVariable(String reference, float defaultValue) {
        String variable = System.getenv(reference);

        if (variable == null) {
            return defaultValue;
        }

        return Float.parseFloat(variable);
    }

    private static long getLongEnvironmentVariable(String reference, long defaultValue) {
        String variable = System.getenv(reference);

        if (variable == null) {
            return defaultValue;
        }

        return Long.parseLong(variable);
    }
}
