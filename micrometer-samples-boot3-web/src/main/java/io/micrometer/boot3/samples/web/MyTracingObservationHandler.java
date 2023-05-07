package io.micrometer.boot3.samples.web;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import io.micrometer.observation.Observation;
import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.transport.ReceiverContext;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.handler.TracingObservationHandler;
import io.micrometer.tracing.propagation.Propagator;

// PropagatingReceiverTracingObservationHandler

public class MyTracingObservationHandler implements TracingObservationHandler<Observation.Context> {
	
    private static final Logger log = LoggerFactory.getLogger(MyTracingObservationHandler.class);
	
	private final Tracer tracer;
	
	private Propagator propagator;
	
	public MyTracingObservationHandler(Tracer tracer, Propagator propagator) {
		this.tracer = tracer;
		this.propagator = propagator;
	}
	
    @Override
    public void onStart(Observation.Context context) {
        log.info("propagator -> " + propagator.fields());
        log.info("propagator -> " + propagator.fields());
    }

	@Override
	public Tracer getTracer() {
		return tracer;
	}
}
