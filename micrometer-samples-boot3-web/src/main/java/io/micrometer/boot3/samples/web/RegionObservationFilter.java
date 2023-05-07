package io.micrometer.boot3.samples.web;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationFilter;

public class RegionObservationFilter implements ObservationFilter {

    private final String region;

    public RegionObservationFilter(String region) {
        this.region = region;
    }

    @Override
    public Observation.Context map(Observation.Context context) {
        return context
                .put("region", region)
                .addLowCardinalityKeyValue(KeyValue.of("region", region));
    }
}
