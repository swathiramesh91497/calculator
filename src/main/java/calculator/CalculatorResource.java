package calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@Path("/calculator")
@Produces({"application/json"})
public class CalculatorResource {
    CalculatorConfig config;
    List<MathProblem> mathProblems;
    private static final Logger log = LoggerFactory.getLogger(CalculatorResource.class);

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

        @JsonProperty
        public List<MathProblem> getMathProblems() {
            return mathProblems;
        }

    }

    @GET
    @Path("/add")
    public CalculatorResponse add(@QueryParam("inputs") List<Float> inputs) throws Exception {
        MathProblem mathProblem = new MathProblem("+", inputs);
        mathProblems.add(mathProblem);
        return new CalculatorResponse(mathProblem.getResult());
    }

    @GET
    @Path("/subtract")
    public CalculatorResponse subtract(@QueryParam("inputs") List<Float> inputs) throws Exception{
        MathProblem mathProblem = new MathProblem("-", inputs);
        mathProblems.add(mathProblem);
        return new CalculatorResponse(mathProblem.getResult());
    }

    @GET
    @Path("/divide")
    public CalculatorResponse divide(@QueryParam("inputs") List<Float> inputs) throws Exception{
        MathProblem mathProblem = new MathProblem("/", inputs);
        mathProblems.add(mathProblem);
        return new CalculatorResponse(mathProblem.getResult());
    }

    @GET
    @Path("/multiply")
    public CalculatorResponse multiply(@QueryParam("inputs") List<Float> inputs) throws Exception{
        MathProblem mathProblem = new MathProblem("*", inputs);
        mathProblems.add(mathProblem);
        return new CalculatorResponse(mathProblem.getResult());
    }

    @GET
    @Path("/audit")
    public CalculatorResponse audit(){
        return new CalculatorResponse(mathProblems);
    }


}
