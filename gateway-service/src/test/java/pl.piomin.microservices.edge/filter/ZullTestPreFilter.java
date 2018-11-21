package pl.piomin.microservices.edge.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class ZullTestPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext
                .getCurrentContext()
                .getRequest()
                .getRequestURI()
                .contains("/test/pre-filter");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext
                .getCurrentContext()
                .getZuulRequestHeaders()
                .put("TEST_PRE_FILTER","test");
        return null;
    }
}
