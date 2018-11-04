package pl.zankowski.fixparser.payment.paypal.util;

import com.google.gson.internal.StringMap;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.Plan;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.zankowski.fixparser.common.PathUtils;

import java.io.IOException;
import java.util.Arrays;

public class PayPalPlanInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayPalPlanInitializer.class);
    private static final String CONFIG_PATH = PathUtils.joinPath(".", "config", "paypal");

    private final PayPalJSONLoader jsonLoader;
    private final String accessToken;
    private final Patch planActivatorPatch;

    public PayPalPlanInitializer(String accessToken) {
        this.accessToken = accessToken;
        this.jsonLoader = new PayPalJSONLoader();
        this.planActivatorPatch = createActivatorPatch();
    }

    private Patch createActivatorPatch() {
        StringMap state = new StringMap();
        state.put("state", "ACTIVE");
        Patch patch = new Patch()
                .setPath("/")
                .setOp("replace");
        patch.setValue(state);
        return patch;
    }

    public Plan initializeEnterprisePlan() throws IOException, PayPalRESTException {
        Plan plan = jsonLoader.load(PathUtils.joinPath(CONFIG_PATH, "bilingplan_enterprise.json"), Plan.class);
        plan = plan.create(accessToken);
        LOGGER.error(Plan.getLastResponse());

        plan.update(accessToken, Arrays.asList(planActivatorPatch));
        LOGGER.error(Plan.getLastResponse());
        return plan;
    }

    public Plan initializeProPlan() throws IOException, PayPalRESTException {
        Plan plan = jsonLoader.load(PathUtils.joinPath(CONFIG_PATH, "bilingplan_pro.json"), Plan.class);
        plan = plan.create(accessToken);
        LOGGER.error(Plan.getLastResponse());

        plan.update(accessToken, Arrays.asList(planActivatorPatch));
        LOGGER.error(Plan.getLastResponse());
        return plan;
    }

}
