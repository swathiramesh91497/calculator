package calculator;

import io.confluent.rest.Application;
import io.confluent.rest.RestConfig;
import io.confluent.rest.RestConfigException;
import org.checkerframework.checker.units.qual.A;
import org.eclipse.jetty.security.*;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.util.security.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import javax.ws.rs.core.Configurable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CalculatorApplication extends Application<CalculatorConfig> {
    private static final Logger log = LoggerFactory.getLogger(CalculatorApplication.class);

    public CalculatorApplication(CalculatorConfig config) {
        super(config);
    }

    @Override
    public void setupResources(Configurable<?> config, CalculatorConfig calculatorConfig) {
        config.register(new CalculatorResource(calculatorConfig));
        config.register(new SwaggerFilesResource());
    }

    public static void main(String[] args) {
        try {
            HashMap<String, String> props = new HashMap<>();
            props.put(RestConfig.AUTHENTICATION_METHOD_CONFIG, RestConfig.AUTHENTICATION_METHOD_BASIC);
            CalculatorConfig config = new CalculatorConfig(props);
            CalculatorApplication app = new CalculatorApplication(config);

            app.start();
            log.info("Server started, listening for requests...");
            app.join();
        } catch (RestConfigException e) {
            log.error("Server configuration failed: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            log.error("Server died unexpectedly: " + e.toString());
        }
    }

    @Override
    protected ConstraintMapping createGlobalAuthConstraint(){
        Constraint constraint = new Constraint();
        constraint.setRoles(new String[]{"users"});
        constraint.setAuthenticate(true);
        constraint.setName(Constraint.__BASIC_AUTH);

        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setMethod("*");
        constraintMapping.setPathSpec("/calculator/audit");
        constraintMapping.setConstraint(constraint);

        return constraintMapping;
    }

    @Override
    protected LoginService createLoginService() {
        HashLoginService loginService = new HashLoginService("Swathi's Service");
        UserStore userStore = new UserStore();
        userStore.addUser("admin", Credential.getCredential("password"), new String[] { "users"});
        loginService.setUserStore(userStore);
        return loginService;
    }

    @Override
    protected IdentityService createIdentityService() {
        return null;
    }


}


