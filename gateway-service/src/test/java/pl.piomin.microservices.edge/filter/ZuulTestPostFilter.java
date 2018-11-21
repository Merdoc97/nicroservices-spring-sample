package pl.piomin.microservices.edge.filter;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;
import static pl.piomin.microservices.edge.filter.Constants.TEST_HEADER;
import static pl.piomin.microservices.edge.filter.Constants.TEST_RESULT;

@Component
@Slf4j
public class ZuulTestPostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;

    }

    @Override
    public boolean shouldFilter() {
        return RequestContext
                .getCurrentContext()
                .getRequest()
                .getHeader(TEST_HEADER.toString()) != null
                & RequestContext
                .getCurrentContext()
                .getResponse()
                .getStatus() == 200;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("test post filter: starting filtering");
        List<Pair<String, String>> filteredResponseHeaders = new ArrayList<>();
        filteredResponseHeaders.add(new Pair<>(TEST_RESULT.toString(), "passed"));
        log.info("delete all response headers and add news header");
        RequestContext.getCurrentContext().put("zuulResponseHeaders", filteredResponseHeaders);
        return null;
    }
}
