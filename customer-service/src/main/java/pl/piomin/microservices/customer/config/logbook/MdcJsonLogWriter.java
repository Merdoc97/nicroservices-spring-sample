package pl.piomin.microservices.customer.config.logbook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;

import java.util.Collections;
import java.util.Map;

public final class MdcJsonLogWriter implements HttpLogWriter {

    private final Logger log = LoggerFactory.getLogger(Logbook.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean isActive() {
        return this.log.isDebugEnabled();
    }

    public void write(final Precorrelation precorrelation, final String request) {
        addToMdc(readJson(request));
        log.debug("request http context");
    }

    public void write(final Correlation correlation, final String response) {
        addToMdc(readJson(response));
        log.debug("response http context");

    }

    private Map<String, Object> readJson(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("cant read json from string, please check is logbook configured as json !!!!", e);
        }
        return Collections.emptyMap();
    }

    private void addToMdc(Map<String, Object> map) {
        map.forEach((key, value) -> {
            MDC.put(key,value.toString());
        });
    }
}
