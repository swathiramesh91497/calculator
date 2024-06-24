package calculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;


public class MathProblemTest {

    private static ObjectMapper om;
    private static ObjectWriter prettyPrintWriter;

    @Test
    public void testSerializeMathProblem() throws IOException {
        List<Float> inputs = new ArrayList<>();
        inputs.add(2.0f);
        inputs.add(1.0f);
        MathProblem mathProblem = new MathProblem("+", inputs);
        String mathProblemJson = toPrettyJson(mathProblem);
        System.out.println(mathProblemJson);
        Assert.assertTrue(mathProblemJson.contains("inputs"));
    }

    public String toPrettyJson(Object o) throws JsonProcessingException {
        if (om == null) {
            om = new ObjectMapper();
            prettyPrintWriter = om.writerWithDefaultPrettyPrinter();
        }
        return prettyPrintWriter.writeValueAsString(o);
    }
}