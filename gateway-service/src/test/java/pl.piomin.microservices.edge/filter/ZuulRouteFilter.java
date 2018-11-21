package pl.piomin.microservices.edge.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

@Component
@Slf4j
public class ZuulRouteFilter extends ZuulFilter {


    @Autowired
    private ObjectMapper objectMapper;
    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {

        return getCurrentContext()
                .getRequest()
                .getRequestURI()
                .startsWith("/api/convert/xml");
    }

    @Override
    public Object run() throws ZuulException {
        try {
            InputStream requestEntity = getCurrentContext().getRequest().getInputStream();
            JsonNode xmlNode = xmlMapper.readTree(requestEntity);

            byte[] bytes = objectMapper.writeValueAsBytes(xmlNode);


            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(getCurrentContext().getRequest()) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(bytes);
                }

                @Override
                public int getContentLength() {
                    return bytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return bytes.length;

                }
            };
            getCurrentContext().setRequest(wrapper);
            getCurrentContext().addZuulRequestHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
