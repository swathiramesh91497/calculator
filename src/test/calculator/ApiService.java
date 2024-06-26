package calculator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.List;

public interface ApiService {
    @GET("/calculator/add")
    Call<Float> testAdd(@Query("inputs") List<Float> inputs);

    @GET("/calculator/subtract")
    Call<Float> testSubtract(@Query("inputs") List<Float> inputs);

    @GET("/calculator/divide")
    Call<Float> testDivide(@Query("inputs") List<Float> inputs);

    @GET("/calculator/multiply")
    Call<Float> testMultiply(@Query("inputs") List<Float> inputs);

    @GET("/calculator/audit")
    Call<ArrayList<MathProblem>> testAudit();

}
