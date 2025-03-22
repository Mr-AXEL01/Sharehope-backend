package net.axel.sharehope.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    public PaymentIntent processPayment(Double amount) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (amount * 100))
                        .setCurrency("usd")
                        .addPaymentMethodType("card")
                        .build();

        return PaymentIntent.create(params);
    }
}
