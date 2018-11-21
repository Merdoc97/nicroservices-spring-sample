package pl.piomin.microservices.edge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchema;
import com.fasterxml.jackson.dataformat.protobuf.schemagen.ProtobufSchemaGenerator;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import pl.piomin.microservices.edge.model.RequestDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static pl.piomin.microservices.edge.filter.Constants.TEST_HEADER;
import static pl.piomin.microservices.edge.filter.Constants.TEST_RESULT;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApiGatewayApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ZuulTestExample {


    @Autowired
    protected WebApplicationContext context;
    protected MockMvc mvc;
    private final XmlMapper xmlMapper = new XmlMapper();
    @Autowired
    private ObjectMapper mapper;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(this.context)
                .build();
    }

    @Test
    public void testPostFilterExample() throws Exception {
        mvc.perform(get("/api/test/post-filter")
                .header(TEST_HEADER.toString(), "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(TEST_RESULT.toString()))
                .andExpect(header().string(TEST_RESULT.toString(), "passed"));
    }

    @Test
    public void testPreFilterExample() throws Exception {
        mvc.perform(get("/api/test/pre-filter"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRouteFilter() throws Exception {
        RequestDTO requestDTO=RequestDTO.builder()
                .message("converted")
                .build();

        mvc.perform(post("/api/convert/xml/route-filter")
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .content(xmlMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", Matchers.is("passed")));
    }

    @Test
    public void testProto() throws Exception {



        RequestDTO requestDTO=RequestDTO.builder()
                .message("converted")
                .build();

        ProtobufMapper mapper = new ProtobufMapper();

        ProtobufSchemaGenerator gen = new ProtobufSchemaGenerator();
        mapper.acceptJsonFormatVisitor(RequestDTO.class, gen);
        ProtobufSchema schema = gen.getGeneratedSchema();

        mvc.perform(get("/proto"))
//                .content(mapper.writer(schema).writeValueAsBytes(requestDTO)))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.content().contentType("application/x-protobuf"));
    }
}
