package calculator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
// If the path field is empty it should not be included in the json serialization
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MathProblem {
    private final String operation;
    private final List<Float> inputs;
    private float result;

    public MathProblem(){
        operation = "";
        inputs = new ArrayList<>();
        result = 0.0f;
    }

    public MathProblem(String operation, List<Float> inputs) throws Exception {
        this.operation = operation;
        this.inputs = inputs;
        this.result = computeResult();
    }

    @JsonCreator
    public MathProblem(@JsonProperty("operation") String operation, @JsonProperty("inputs") List<Float> inputs, @JsonProperty("result") float result) throws Exception {
        this.operation = operation;
        this.inputs = inputs;
        this.result = result;
    }

    @JsonProperty("operation")
    public String getOperation() {
        return operation;
    }

    @JsonProperty("inputs")
    public List<Float> getInputs() {
        return inputs;
    }

    @JsonProperty("result")
    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public float computeResult() throws Exception {
        float tot = inputs.get(0);
        for (int i = 1; i < inputs.size(); i++){
            float input = inputs.get(i);
            switch (operation) {
                case "+":
                    tot += input;
                    break;
                case "-":
                    tot -= input;
                    break;
                case "*":
                    tot *= input;
                    break;
                case "/":
                    tot = tot/input;
                    if (input == 0) {
                       throw new Exception("Divide by Zero Error");
                    } else {
                        break;
                    }
                default:
                    throw new Exception("Unsupported Math Operation: " + operation);
                }
            }
            return tot;
    }
}
