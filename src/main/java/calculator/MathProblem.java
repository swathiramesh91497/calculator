import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
// If the path field is empty it should not be included in the json serialization
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MathProblem {
    private final String operation;
    private final List<String> inputs;
    private final float result;

    public MathProblem(@JsonProperty String operation, @JsonProperty List<String> inputs, @JsonProperty float result){
        this.operation = operation;
        this.inputs = inputs;
        this.result = result;
    }

    public String getOperation() {
        return operation;
    }

    public List<String> getInputs() {
        return inputs;
    }

    public float getResult() {
        return result;
    }
}
