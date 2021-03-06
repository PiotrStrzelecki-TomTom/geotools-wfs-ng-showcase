package com.tomtom.wfsngshowcase;

public class FeatureDto {

    private final String id;
    private final String cat;
    private final String geometry;

    public FeatureDto(final String id, final String cat, final String geometry) {
        this.id = id;
        this.cat = cat;
        this.geometry = geometry;
    }
}
