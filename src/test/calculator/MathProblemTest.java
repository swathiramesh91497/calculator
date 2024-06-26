package calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MathProblemTest {

    private static ObjectMapper om;

    @Test
    public void testSerializeMathProblem() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(2.0f);
        inputs.add(1.0f);
        MathProblem mathProblem = new MathProblem("+", inputs);
        String mathProblemJson = getObjectMapper().writeValueAsString(mathProblem);
        Assert.assertTrue(mathProblemJson.contains("inputs"));

    }

    @Test
    public void testDeserializeMathProblem() throws IOException {
        String mathProblemJson = "{\"operation\":\"+\",\"inputs\":[2.0,1.0], \"result\": 2.0}";
        MathProblem deserializedMathProblem = getObjectMapper().readValue(mathProblemJson, MathProblem.class);
        Assert.assertEquals(deserializedMathProblem.getOperation(), "+");
        Assert.assertEquals(deserializedMathProblem.getInputs().get(0), 2.0f, 0);
        Assert.assertEquals(deserializedMathProblem.getInputs().get(1), 1.0f, 0);
        Assert.assertEquals(deserializedMathProblem.getResult(), 2.0f, 0);
    }

    public static ObjectMapper getObjectMapper() {
        if (om == null) {
            om = new ObjectMapper();
        }
        return om;
    }
}