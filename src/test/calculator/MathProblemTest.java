package calculator;

import org.junit.Test;

import java.io.IOException;

public class MathProblemTest {
    @Test
    public void testScopeDoesNotContainPathWhenEmpty() throws IOException {
        MathProblem mathProblem = new MathProblem("+", )

        String scopeJson = toPrettyJson(testScope);

        Assert.assertFalse(scopeJson.contains("path"));
    }
}