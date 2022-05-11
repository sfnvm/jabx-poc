package com.sfnvm.example.jabxpoc.jabx;

import com.sfnvm.example.jabxpoc.handler.IadTDiepHandler;
import com.sfnvm.example.jabxpoc.handler.OldTDiepHandler;
import com.sfnvm.example.jabxpoc.handler.TDiepHandler;
import com.sfnvm.example.jabxpoc.util.FunctionExHandler;
import com.sfnvm.example.jabxpoc.xml.TDiepXml;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JaxbTests {
    private final TDiepHandler tDiepHandler;
    private final OldTDiepHandler oldTDiepHandler;
    private final IadTDiepHandler iadTDiepHandler;

    private static final int TOTAL_DUPLICATED = 10000;
    private static String inputStr = "";
    private static final List<String> stringList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() throws IOException {
        // Get Input stream
        InputStream is = JaxbTests.class.getClassLoader().getResourceAsStream("samples/001.txt");

        // Read data
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = Objects.requireNonNull(is).read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }

        inputStr = result.toString("UTF-8").substring(1); // First char gone mad

        // Init input arr
        IntStream.range(0, TOTAL_DUPLICATED).forEach(value -> stringList.add(inputStr));
    }

    @Test
    @Order(1)
    void pooling_Test() {
        Instant startTime = Instant.now();
        String functionName = "Pooling_Test";

        // Unmarshalling
        List<TDiepXml> tDiepXmls = stringList.parallelStream()
                .map(FunctionExHandler.handling(tDiepHandler::fromXml))
                .collect(Collectors.toList());

        log.info("Sample data: {}", tDiepXmls.get(0));
        log.info("==================================================");
        log.info("TOTAL RECORDS: {}", TOTAL_DUPLICATED);
        Instant unmarshallingMark = Instant.now();
        log.info("{} UNMARSHALLING process done. Time taken: {}", functionName, Duration.between(startTime, unmarshallingMark));

        // Marshalling
        List<String> tDiepXmlStrs = tDiepXmls.parallelStream()
                .map(FunctionExHandler.handling(tDiepHandler::toXml))
                .collect(Collectors.toList());
        log.info("{} MARSHALLING process done. Time taken: {}", functionName, Duration.between(unmarshallingMark, Instant.now()));
        log.info("{} CORE process done. Time taken: {}", functionName, Duration.between(startTime, Instant.now()));

        // Assertions
        Assertions.assertEquals(tDiepXmlStrs.size(), tDiepXmls.size());
        Assertions.assertEquals(TOTAL_DUPLICATED, tDiepXmls.size());
        Assertions.assertEquals(TOTAL_DUPLICATED, tDiepXmlStrs.size());
        for (int i = 0; i < TOTAL_DUPLICATED; i++) {
            Assertions.assertEquals(tDiepXmls.get(0), tDiepXmls.get(i));
            Assertions.assertEquals(tDiepXmlStrs.get(0), tDiepXmlStrs.get(i));
        }
    }

    @Test
    @Order(2)
    void initAndDestroy_Test() {
        Instant startTime = Instant.now();
        String functionName = "InitAndDestroy_Test";

        // Unmarshalling
        List<TDiepXml> tDiepXmls = stringList.parallelStream()
                .map(FunctionExHandler.handling(iadTDiepHandler::fromXml))
                .collect(Collectors.toList());

        log.info("Sample data: {}", tDiepXmls.get(0));
        log.info("==================================================");
        log.info("TOTAL RECORDS: {}", TOTAL_DUPLICATED);
        Instant unmarshallingMark = Instant.now();
        log.info("{} UNMARSHALLING process done. Time taken: {}", functionName, Duration.between(startTime, unmarshallingMark));

        // Marshalling
        List<String> tDiepXmlStrs = tDiepXmls.parallelStream()
                .map(FunctionExHandler.handling(iadTDiepHandler::toXml))
                .collect(Collectors.toList());
        log.info("{} MARSHALLING process done. Time taken: {}", functionName, Duration.between(unmarshallingMark, Instant.now()));
        log.info("{} CORE process done. Time taken: {}", functionName, Duration.between(startTime, Instant.now()));

        // Assertions
        Assertions.assertEquals(tDiepXmlStrs.size(), tDiepXmls.size());
        Assertions.assertEquals(TOTAL_DUPLICATED, tDiepXmls.size());
        Assertions.assertEquals(TOTAL_DUPLICATED, tDiepXmlStrs.size());
        for (int i = 0; i < TOTAL_DUPLICATED; i++) {
            Assertions.assertEquals(tDiepXmls.get(0), tDiepXmls.get(i));
            Assertions.assertEquals(tDiepXmlStrs.get(0), tDiepXmlStrs.get(i));
        }
    }

    @Test
    @Order(3)
    void oldSharedBean_Test() {
        Instant startTime = Instant.now();
        String functionName = "OldSharedBean_Test";

        // Unmarshalling
        List<TDiepXml> tDiepXmls = stringList.parallelStream()
                .map(FunctionExHandler.handling(oldTDiepHandler::fromXml))
                .collect(Collectors.toList());

        log.info("Sample data: {}", tDiepXmls.get(0));
        log.info("==================================================");
        log.info("TOTAL RECORDS: {}", TOTAL_DUPLICATED);
        Instant unmarshallingMark = Instant.now();
        log.info("{} UNMARSHALLING process done. Time taken: {}", functionName, Duration.between(startTime, unmarshallingMark));

        // Marshalling
        List<String> tDiepXmlStrs = tDiepXmls.parallelStream()
                .map(FunctionExHandler.handling(oldTDiepHandler::toXml))
                .collect(Collectors.toList());
        log.info("{} MARSHALLING process done. Time taken: {}", functionName, Duration.between(unmarshallingMark, Instant.now()));
        log.info("{} CORE process done. Time taken: {}", functionName, Duration.between(startTime, Instant.now()));

        // Assertions
        Assertions.assertEquals(tDiepXmlStrs.size(), tDiepXmls.size());
        Assertions.assertEquals(TOTAL_DUPLICATED, tDiepXmls.size());
        Assertions.assertEquals(TOTAL_DUPLICATED, tDiepXmlStrs.size());
        for (int i = 0; i < TOTAL_DUPLICATED; i++) {
            Assertions.assertEquals(tDiepXmls.get(0), tDiepXmls.get(i));
            Assertions.assertEquals(tDiepXmlStrs.get(0), tDiepXmlStrs.get(i));
        }
    }
}
