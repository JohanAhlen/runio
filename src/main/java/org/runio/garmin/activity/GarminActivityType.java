package org.runio.garmin.activity;

public class GarminActivityType extends GarminField {

    public static final String TYPE_KEY_RUNNING = "running";
    public static final String TYPE_KEY_CROSS_COUNTRY_SKIING = "cross_country_skiing";
    public static final String TYPE_KEY_INDOOR_CYCLING = "indoor_cycling";

    private GarminField parent;

    public GarminField getParent() {
        return parent;
    }
}
