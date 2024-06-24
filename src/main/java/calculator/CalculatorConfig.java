package calculator;


import io.confluent.rest.RestConfig;
import org.apache.kafka.common.config.ConfigDef;

public class CalculatorConfig extends RestConfig {
    private static ConfigDef config;

    static {
        config = baseConfigDef();
    }

    public CalculatorConfig() {
        super(config);
    }

}
