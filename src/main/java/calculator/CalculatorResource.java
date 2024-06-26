package calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @GET
    @Path("/add")
    public float add(@QueryParam("inputs") List<Float> inputs) throws Exception {
        MathProblem mathProblem = new MathProblem("+", inputs);
        mathProblems.add(mathProblem);
        return mathProblem.getResult();
    }

    @GET
    @Path("/subtract")
    public float subtract(@QueryParam("inputs") List<Float> inputs) throws Exception{
        MathProblem mathProblem = new MathProblem("-", inputs);
        mathProblems.add(mathProblem);
        return mathProblem.getResult();
    }

    @GET
    @Path("/divide")
    public float divide(@QueryParam("inputs") List<Float> inputs) throws Exception{
        MathProblem mathProblem = new MathProblem("/", inputs);
        mathProblems.add(mathProblem);
        return mathProblem.getResult();
    }

    @GET
    @Path("/multiply")
    public float multiply(@QueryParam("inputs") List<Float> inputs) throws Exception{
        MathProblem mathProblem = new MathProblem("*", inputs);
        mathProblems.add(mathProblem);
        return mathProblem.getResult();
    }

    @GET
    @Path("/audit")
    public List<MathProblem> audit(){
        return mathProblems;
    }


}
