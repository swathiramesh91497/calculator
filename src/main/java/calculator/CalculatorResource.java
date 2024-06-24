package calculator;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@Path("/calculator")
//@Produces("application/vnd.hello.v1+json")
public class CalculatorResource {
    CalculatorConfig config;
    List<MathProblem> mathProblems;

    public CalculatorResource(CalculatorConfig config) {
        this.config = config;
        this.mathProblems = new ArrayList<>();
    }

    public static class CalculatorResponse {
        @NotEmpty
        private float result;
        private List<MathProblem> mathProblems;

        public CalculatorResponse(float res) {
            this.result = res;
        }
        public CalculatorResponse(List<MathProblem> mathProblems) {
            this.mathProblems = mathProblems;
        }

        @JsonProperty
        public float getResult() {
            return result;
        }
    }

    @Path("/add")
    public CalculatorResponse add(@QueryParam("inputs") List<Float> inputs){
        MathProblem mathProblem = new MathProblem("+", inputs);
        float result = mathProblem.getResult();
        mathProblems.add(mathProblem);
        return new CalculatorResponse(result);
    }

    @Path("/subtract")
    public CalculatorResponse subtract(@QueryParam("inputs") List<Float> inputs){
        MathProblem mathProblem = new MathProblem("-", inputs);
        float result = mathProblem.getResult();
        mathProblems.add(mathProblem);
        return new CalculatorResponse(result);
    }

    @Path("/divide")
    public CalculatorResponse divide(@QueryParam("inputs") List<Float> inputs){
        MathProblem mathProblem = new MathProblem("/", inputs);
        float result = mathProblem.getResult();
        mathProblems.add(mathProblem);
        return new CalculatorResponse(result);
    }

    @Path("/multiply")
    public CalculatorResponse multiply(@QueryParam("inputs") List<Float> inputs){
        MathProblem mathProblem = new MathProblem("*", inputs);
        float result = mathProblem.getResult();
        mathProblems.add(mathProblem);
        return new CalculatorResponse(result);
    }

    @Path("/audit")
    public CalculatorResponse audit(){
        return new CalculatorResponse(mathProblems);
    }


}
