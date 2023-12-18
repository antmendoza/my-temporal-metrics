package com.antmendoza.temporal.config;

public class FromEnv {
    public static String getWorkerPort() {
        return System.getenv("PQL_PORT");
    }

    public static String getActionsPerSecond() {
        return System.getenv("ACTIONS_PER_SECOND");
    }

    public static String getActivityLatency() {
        return System.getenv("SLEEP_ACTIVITY_IN_MS");

    }
}
