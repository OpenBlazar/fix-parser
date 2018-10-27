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
package pl.zankowski.bfp.services.payment;

import pl.zankowski.bfp.core.payments.SubscriptionPlan;
import pl.zankowski.bfp.core.payments.exception.PaymentException;
import pl.zankowski.bfp.data.user.UserAddress;

/**
 * @author Wojciech Zankowski
 */
public interface PaymentService {

    String subscribe(SubscriptionPlan subscriptionPlan, UserAddress userAddress) throws PaymentException;

    SubscriptionPlan confirmPayment(String paymentToken) throws PaymentException;

}
