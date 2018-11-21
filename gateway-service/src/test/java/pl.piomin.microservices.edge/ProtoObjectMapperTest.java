package pl.piomin.microservices.edge;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper;
import com.fasterxml.jackson.dataformat.protobuf.schema.NativeProtobufSchema;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchema;
import com.fasterxml.jackson.dataformat.protobuf.schemagen.ProtobufSchemaGenerator;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piomin.microservices.edge.model.RequestDTO;

import java.io.IOException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Slf4j
public class ProtoObjectMapperTest {


    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testMapper() throws IOException, ClassNotFoundException {
        objectMapper.registerModule(new ProtobufModule());

        RequestDTO requestDTO = RequestDTO.builder()
                .message("converted")
                .build();

        ProtobufMapper mapper = new ProtobufMapper();

        ProtobufSchemaGenerator gen = new ProtobufSchemaGenerator();
        mapper.acceptJsonFormatVisitor(RequestDTO.class, gen);
        ProtobufSchema schema = gen.getGeneratedSchema();
        NativeProtobufSchema nativeProtobufSchema = schema.getSource();

//        convert to schema
        log.info("brotobuf schema is: {}", nativeProtobufSchema.toString());
//        to bytes
        byte[] pojo = mapper.writer(schema).writeValueAsBytes(requestDTO);
        log.info("proto size: {}", pojo.length);
        log.info("result is in proto format: {}", pojo);
        byte[] jsonPojo = objectMapper.writeValueAsBytes(requestDTO);
        log.info("json size: {}", jsonPojo.length);
        //        from proto to POJO
        log.info("convert proto bytes to pojo: {}", (Object) mapper.readerFor(RequestDTO.class).with(schema).readValue(pojo).toString());

        log.info("convert from proto to JSON: {}", new java.lang.String(pojo));
    }
}
