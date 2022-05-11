package com.sfnvm.example.jabxpoc.jabx;

import com.sfnvm.example.jabxpoc.handler.TDiepHandler;
import com.sfnvm.example.jabxpoc.util.FunctionExHandler;
import com.sfnvm.example.jabxpoc.xml.TDiepXml;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
class JaxbTests {
    private final TDiepHandler tDiepHandler;

    private static final int TOTAL_DUPLICATED = 300000;

    @Test
    void test001() throws IOException {
        Instant startTime = Instant.now();

        String inputStr;
        List<String> stringList = new ArrayList<>();

        // Get Input stream
        InputStream is = getClass().getClassLoader().getResourceAsStream("samples/001.txt");

        // Read data
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = Objects.requireNonNull(is).read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        // First char gone mad
        inputStr = result.toString("UTF-8").substring(1);

        // Init input arr
        IntStream.range(0, TOTAL_DUPLICATED).forEach(value -> stringList.add(inputStr));

        List<TDiepXml> tDiepXmls = stringList.parallelStream()
                .map(FunctionExHandler.handling(tDiepHandler::fromXml))
                .collect(Collectors.toList());

        // Force testcase to fail
        // tDiepXmls.get(0).setDlieu(null);

        log.info("Sample data: {}", tDiepXmls.get(0));
        log.info("Test001 core process done. Time taken: {}", Duration.between(startTime, Instant.now()));

        // Assertions
        for (TDiepXml tDiepXml : tDiepXmls) {
            Assertions.assertEquals(tDiepXmls.get(0), tDiepXml);
        }
        Assertions.assertEquals(TOTAL_DUPLICATED, tDiepXmls.size());
    }
}
