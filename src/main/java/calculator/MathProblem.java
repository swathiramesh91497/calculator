package calculator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
// If the path field is empty it should not be included in the json serialization
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MathProblem {
    private final String operation;
    private final List<Float> inputs;

    public MathProblem(@JsonProperty String operation, @JsonProperty List<Float> inputs){
        this.operation = operation;
        //TODO: case of inputs length being < 2
        this.inputs = inputs;
    }

    public String getOperation() {
        return operation;
    }

    public List<Float> getInputs() {
        return inputs;
    }

    public float getResult() {
        // Temporary
        float tot = 0f;
        for (Float input: inputs){
            switch (operation) {
                case "+":
                    tot += input;
                case "-":
                    tot -= input;
                case "*":
                    tot *= input;
                case "/":
                    tot /= input;
                default:
                    // TODO: Fix
                    throw new RuntimeException();
                }
            }
            return tot;
    }
}
