package calculator;

import okhttp3.*;
import org.junit.BeforeClass;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

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

    @Test
    public void testAdd() throws IOException {
        List<Float> inputs = new ArrayList<>();
        inputs.add(2.0f);
        inputs.add(3.0f);
        assertEquals(5.0f, (float) calculatorApi.testAdd(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(0.0f);
        inputs.add(-3.0f);
        assertEquals(-3.0f, (float) calculatorApi.testAdd(inputs).execute().body(), 0);
    }

    @Test
    public void testSubtract() throws IOException {
        List<Float> inputs = new ArrayList<>();
        inputs.add(2.0f);
        inputs.add(3.0f);
        assertEquals(-1.0f, calculatorApi.testSubtract(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(6.0f);
        inputs.add(5.0f);
        assertEquals(1.0f, calculatorApi.testSubtract(inputs).execute().body(), 0);
    }

    @Test
    public void testMultiply() throws IOException {
        List<Float> inputs = new ArrayList<>();
        inputs.add(2.0f);
        inputs.add(3.0f);
        assertEquals(6.0f, calculatorApi.testMultiply(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(-6.0f);
        inputs.add(5.0f);
        assertEquals(-30.0f, calculatorApi.testMultiply(inputs).execute().body(), 0);
    }

    @Test
    public void testDivide() throws IOException {
        List<Float> inputs = new ArrayList<>();
        inputs.add(6.0f);
        inputs.add(3.0f);
        assertEquals(2.0f, calculatorApi.testDivide(inputs).execute().body(), 0);
        inputs.clear();
        inputs.add(6.0f);
        inputs.add(2.0f);
        assertEquals(3.0f, calculatorApi.testDivide(inputs).execute().body(), 0);
    }

}


