package calculator;

import okhttp3.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();


    private static final String BASE_URL = "http://localhost:8080";
    private static Retrofit retrofit = null;
    private static ApiService calculatorApi = null;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        CalculatorConfig calculatorConfig = new CalculatorConfig();
        CalculatorApplication calculatorApplication = new CalculatorApplication(calculatorConfig);
        calculatorApplication.start();

        if (retrofit == null) {
            String authToken = Credentials.basic("admin", "password");

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .header("Authorization", authToken)
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        calculatorApi = retrofit.create(ApiService.class);
    }

    // TODO: these test cases test for two inputs mostly, we could add cases for 3+
    // Test basic audit endpoint (Has to run first).
    @Test
    public void testBasicAudit() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(6.0f);
        inputs.add(3.0f);
        MathProblem mathProblem = new MathProblem("+", inputs);
        calculatorApi.testAdd(inputs).execute();
        MathProblem auditMathProblem = calculatorApi.testAudit().execute().body().get(0);
        assertEquals(mathProblem.getOperation(), auditMathProblem.getOperation());
        assertEquals(mathProblem.getInputs(), auditMathProblem.getInputs());
    }

    // Tests basic addition cases, neg numbers
    @Test
    public void testAdd() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(2.0f);
        inputs.add(3.0f);
        assertEquals(5.0f, (float) calculatorApi.testAdd(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(0.0f);
        inputs.add(-3.0f);
        assertEquals(-3.0f, (float) calculatorApi.testAdd(inputs).execute().body(), 0);
    }

    // Tests basic subtract cases, neg numbers
    @Test
    public void testSubtract() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(2.0f);
        inputs.add(3.0f);
        assertEquals(-1.0f, calculatorApi.testSubtract(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(6.0f);
        inputs.add(5.0f);
        assertEquals(1.0f, calculatorApi.testSubtract(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(-6.0f);
        inputs.add(5.0f);
        assertEquals(-11.0f, calculatorApi.testSubtract(inputs).execute().body(), 0);
    }

    // Tests basic multiply cases, neg numbers
    @Test
    public void testMultiply() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(2.0f);
        inputs.add(3.0f);
        assertEquals(6.0f, calculatorApi.testMultiply(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(-6.0f);
        inputs.add(5.0f);
        assertEquals(-30.0f, calculatorApi.testMultiply(inputs).execute().body(), 0);
    }

    // Test basic divide cases, neg numbers
    @Test
    public void testDivide() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(6.0f);
        inputs.add(3.0f);
        assertEquals(2.0f, calculatorApi.testDivide(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(6.0f);
        inputs.add(-2.0f);
        assertEquals(-3.0f, calculatorApi.testDivide(inputs).execute().body(), 0);
    }

    // Test divide by zero, should throw an error
    @Test
    public void testDivideByZero() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(6.0f);
        inputs.add(0.0f);
        assertEquals(400, calculatorApi.testDivide(inputs).execute().code());
    }

    // Test max float values
    @Test
    public void testMaxFloatAdd() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(Float.MAX_VALUE + 1);
        inputs.add(Float.MAX_VALUE + 1);
        assertEquals(Float.POSITIVE_INFINITY, calculatorApi.testAdd(inputs).execute().body(), 0);
    }

    // Test max float values
    @Test
    public void testMaxFloatMultiply() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(Float.MAX_VALUE);
        inputs.add(Float.MAX_VALUE);
        assertEquals(Float.POSITIVE_INFINITY, calculatorApi.testMultiply(inputs).execute().body(), 0);
    }

    // Test max float values
    @Test
    public void testMaxFloatDivide() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(Float.MAX_VALUE*2+2);
        inputs.add(2.0f);
        assertEquals(Float.POSITIVE_INFINITY, calculatorApi.testDivide(inputs).execute().body(), 0);
    }

    // Test max float values
    @Test
    public void testMaxFloatSubtract() throws Exception {
        List<Float> inputs = new ArrayList<>();
        inputs.add(Float.MAX_VALUE*2);
        inputs.add(1.0f);
        assertEquals(Float.POSITIVE_INFINITY, calculatorApi.testSubtract(inputs).execute().body(), 0);
    }

    // Tests that no inputs will result in an error being thrown.
    @Test
    public void testEmptyInput() throws Exception {
        List<Float> inputs = new ArrayList<>();
        assertEquals(400, calculatorApi.testAdd(inputs).execute().code());
    }


}


