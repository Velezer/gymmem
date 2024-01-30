package ariefsyaifu.gymmem.payment.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ariefsyaifu.gymmem.subscription.model.Product;
import ariefsyaifu.gymmem.subscription.model.Subscription;
import ariefsyaifu.gymmem.subscription.service.SubscriptionService;

@Component
public class SubscriptionClient {

    @Autowired
    public SubscriptionClient(
            SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    private SubscriptionService subscriptionService;

    public Subscription getSubscriptionById(String subscriptionId) {
        return subscriptionService.getSubscriptionById(subscriptionId);
    }

    public void paid(String subscriptionId) {
        subscriptionService.paid(subscriptionId);
    }
}
