package calculator;

import io.confluent.rest.Application;
import io.confluent.rest.RestConfigException;
import org.apache.kafka.common.config.ConfigDef;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Configurable;


public class CalculatorApplication extends Application<CalculatorConfig> {
    private static final Logger log = LoggerFactory.getLogger(CalculatorApplication.class);

    public CalculatorApplication(CalculatorConfig config) {
        super(config);
    }

    @Override
    public void setupResources(Configurable<?> config, CalculatorConfig calculatorConfig) {
        config.register(new CalculatorResource(calculatorConfig));
        config.property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/(static/.*|.*\\.html|)");
        config.register(new SwaggerFilesResource());
    }

    public static void main(String[] args) {
        try {
            // This simple configuration is driven by the command line. Run with an argument to specify
            // the format of the message returned by the API, e.g.
            // java -jar rest-utils-examples.jar \
            //    io.confluent.rest.examples.helloworld.HelloWorldApplication 'Goodbye, %s'
            CalculatorConfig config = new CalculatorConfig();
            CalculatorApplication app = new CalculatorApplication(config);
            app.start();
            System.out.println("HERE");
            log.info("Server started, listening for requests...");
            app.join();
        } catch (RestConfigException e) {
            log.error("Server configuration failed: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            log.error("Server died unexpectedly: " + e.toString());
        }
    }

}


