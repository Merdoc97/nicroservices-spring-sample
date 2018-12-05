package pl.piomin.microservices.edge.filter;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchema;
import com.fasterxml.jackson.dataformat.protobuf.schemagen.ProtobufSchemaGenerator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

@Component
@Slf4j
public class ProtoBuffConverterZuulFilter extends ZuulFilter {

    private final ProtobufMapper mapper;
    private final ProtobufSchemaGenerator gen;
    private final ProtobufSchema schema;

    public ProtoBuffConverterZuulFilter() throws JsonMappingException {
        this.mapper = new ProtobufMapper();
        this.gen = new ProtobufSchemaGenerator();
        this.schema = gen.getGeneratedSchema();
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER + 2;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
