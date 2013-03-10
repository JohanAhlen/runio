package com.johanahlen.runsync.garmin.activity;

public class SummaryMeasurement {
    private String fieldDisplayName;
    private String display;
    private String displayUnit;
    private String value;
    private String unitAbbr;
    private String withUnit;
    private String withUnitAbbr;
    private String uom;

    public String getFieldDisplayName() {
        return fieldDisplayName;
    }

    public String getDisplay() {
        return display;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public String getValue() {
        return value;
    }

    public String getUnitAbbr() {
        return unitAbbr;
    }

    public String getWithUnit() {
        return withUnit;
    }

    public String getWithUnitAbbr() {
        return withUnitAbbr;
    }

    public String getUom() {
        return uom;
    }
}
