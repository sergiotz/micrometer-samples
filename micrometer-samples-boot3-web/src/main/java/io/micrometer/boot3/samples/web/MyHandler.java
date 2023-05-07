package io.micrometer.boot3.samples.web;

import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.Observation.Event;

class MyHandler implements ObservationHandler<Observation.Context> {

    private static final Logger log = LoggerFactory.getLogger(MyHandler.class);

    @Override
    public void onStart(Observation.Context context) {
        //log.info("Before running the observation for context [{}], userType [{}]", context.getName(), getUserTypeFromContext(context));
        log.info("keys start: " + context.getAllKeyValues());
        MDC.put("http.url", context.get("http.url"));
    }

    @Override
    public void onStop(Observation.Context context) {
        //log.info("After running the observation for context [{}], userType [{}]", context.getName(), getUserTypeFromContext(context));
        MDC.remove("http.url");
        log.info("keys stop: " + context.getAllKeyValues());
    }

    @Override
    public void onEvent(Event event, Context context) {
        log.info("keys event: " + context.getAllKeyValues());
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }

    private String getUserTypeFromContext(Observation.Context context) {
        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(), false)
                .filter(keyValue -> "userType".equals(keyValue.getKey()))
                .map(KeyValue::getValue)
                .findFirst()
                .orElse("UNKNOWN");
    }
}
