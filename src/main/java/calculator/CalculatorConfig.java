package calculator;


import io.confluent.rest.RestConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class CalculatorConfig extends RestConfig {
    private static ConfigDef config;
//    private static String authentication.method;
    static {
        config = baseConfigDef();
    }

    public CalculatorConfig() {
        super(config);
    }

    public CalculatorConfig(Map<?, ?> props) {
        super(config, props);
    }

}
