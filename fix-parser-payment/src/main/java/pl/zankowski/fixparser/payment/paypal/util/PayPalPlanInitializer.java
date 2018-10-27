/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.zankowski.bfp.core.payments.paypal.util;

import pl.zankowski.bfp.common.PathUtils;
import pl.zankowski.bfp.core.security.util.SettingsManager;
import com.google.gson.internal.StringMap;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.Plan;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Wojciech Zankowski
 */
public class PayPalPlanInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayPalPlanInitializer.class);
    private static final String CONFIG_PATH = PathUtils.joinPath(SettingsManager.getInstance().getPathResolver().getAppDirectory(), "config", "paypal");

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
