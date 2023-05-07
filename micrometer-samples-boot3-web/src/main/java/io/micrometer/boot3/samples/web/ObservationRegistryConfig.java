package io.micrometer.boot3.samples.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.context.annotation.Configuration;

import io.micrometer.context.ContextRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;

@Configuration(proxyBeanMethods = false)
public class ObservationRegistryConfig implements ObservationRegistryCustomizer<ObservationRegistry> {

    @Value("${region}")
    private String region;
    
    private Tracer tracer;
    
    private Propagator propagator;
    
    public ObservationRegistryConfig(Tracer tracer, Propagator propagator) {
    	this.tracer = tracer;
    	this.propagator = propagator;
    }

    @Override
    public void customize(ObservationRegistry registry) {
    	ContextRegistry.getInstance().registerThreadLocalAccessor(new ObservationThreadLocalAccessor());
    	
        registry.observationConfig()
        			.observationHandler(new MyTracingObservationHandler(tracer, propagator))
        			.observationFilter(new RegionObservationFilter(region));
    }
}