package pl.piomin.microservices.customer.config;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.DefaultNewSpanParser;
import io.micrometer.tracing.annotation.ImperativeMethodInvocationProcessor;
import io.micrometer.tracing.annotation.MethodInvocationProcessor;
import io.micrometer.tracing.annotation.NewSpan;
import io.micrometer.tracing.annotation.NewSpanParser;
import io.micrometer.tracing.annotation.SpanAspect;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.observation.DefaultServerRequestObservationConvention;
import org.springframework.http.server.observation.ServerHttpObservationDocumentation;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.http.server.observation.ServerRequestObservationConvention;
import org.springframework.web.filter.ServerHttpObservationFilter;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@Slf4j
public class TraceConfiguration {
    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    @Bean
    public Capability capability(final MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }

    @Bean
    public ServerHttpObservationFilter observationFilter(ObservationRegistry observationRegistry,ServerRequestObservationConvention serverRequestObservationConvention) {
        return new ServerHttpObservationFilter(observationRegistry,serverRequestObservationConvention);
    }

    @Bean
    public ServerRequestObservationConvention serverTagsRequestObservationConvention() {
        return new DefaultServerRequestObservationConvention() {
            @Override
            public KeyValues getLowCardinalityKeyValues(ServerRequestObservationContext context) {
                try {
                    return super.getLowCardinalityKeyValues(context).and(
                            KeyValue.of("hostname", InetAddress.getLocalHost().getHostName()),
                            KeyValue.of("ip", InetAddress.getLocalHost().getHostAddress()));

                } catch (UnknownHostException e) {
                    log.error("Error getting host name and ip: {}", e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }


            @Override
            protected KeyValue httpUrl(ServerRequestObservationContext context) {
                return context.getCarrier() != null ? getFullUrl(context) : KeyValue.of(ServerHttpObservationDocumentation.HighCardinalityKeyNames.HTTP_URL, "UNKNOWN");
            }
        };
    }

    private KeyValue getFullUrl(ServerRequestObservationContext context) {
        var httpServletRequest = ((HttpServletRequest) context.getCarrier());
        var scheme = httpServletRequest.getScheme() + "://";
        var uri = httpServletRequest.getRequestURI();
        var serverName = httpServletRequest.getServerName();
        return KeyValue.of(ServerHttpObservationDocumentation.HighCardinalityKeyNames.HTTP_URL, scheme + serverName + uri);
    }

    @Bean
    NewSpanParser newSpanParser() {
        return new DefaultNewSpanParser() {
            @Override
            public void parse(MethodInvocation pjp, NewSpan newSpan, Span span) {
                Span spanWithTag = null;
                try {
                    spanWithTag = span.tag("hostname", InetAddress.getLocalHost().getHostName())
                            .tag("ip", InetAddress.getLocalHost().getHostAddress());
                    super.parse(pjp, newSpan, spanWithTag);
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }

            }
        };
    }

    @Bean
    MethodInvocationProcessor methodInvocationProcessor(NewSpanParser newSpanParser, Tracer tracer,
                                                        BeanFactory beanFactory) {
        return new ImperativeMethodInvocationProcessor(newSpanParser, tracer, beanFactory::getBean,
                beanFactory::getBean);
    }

    @Bean
    SpanAspect spanAspect(MethodInvocationProcessor methodInvocationProcessor) {
        return new SpanAspect(methodInvocationProcessor);
    }
}
