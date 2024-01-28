package ariefsyaifu.gymmem.subscription.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import ariefsyaifu.gymmem.subscription.dto.subscription.SubscribeRequestBody;
import ariefsyaifu.gymmem.subscription.dto.subscription.ViewSubscriptionDto;
import ariefsyaifu.gymmem.subscription.model.Product;
import ariefsyaifu.gymmem.subscription.model.Subscription;
import ariefsyaifu.gymmem.subscription.repository.ProductRepository;
import ariefsyaifu.gymmem.subscription.repository.SubscriptionRepository;
import io.jsonwebtoken.Claims;

@Component
public class SubscriptionService {

    @Autowired
    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            ProductRepository productRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.productRepository = productRepository;
    }

    private ProductRepository productRepository;
    private SubscriptionRepository subscriptionRepository;

    public void subscribe(SubscribeRequestBody params, Claims claims) {
        String userId = claims.get("id", String.class);
        Optional<Product> product = productRepository.findById(params.productId);
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PRODUCT_NOT_EXISTS");
        }
        Subscription s = subscriptionRepository.findByUserIdAndProduct_Id(userId, params.productId);
        if (s != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ALREADY_SUBSCRIBED");
        }

        Subscription newSubscription = new Subscription();
        newSubscription.userId = userId;
        newSubscription.product = product.get();
        newSubscription.amount = product.get().price;
        newSubscription.remainingTimesOfMeeting = product.get().timesOfMeeting;
        newSubscription.status = Subscription.Status.PENDING;
        subscriptionRepository.save(newSubscription);
    }

    public void unsubscribe(String id, Claims claims) {
        Subscription s = subscriptionRepository.findById(id).orElse(null);
        if (s == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SUBSCRIPTION_NOT_EXISTS");
        }
        String userId = claims.get("id", String.class);

        if (!s.userId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        subscriptionRepository.delete(s);
    }

    public List<ViewSubscriptionDto> getSubscription(Claims claims) {
        String userId = claims.get("id", String.class);

        return subscriptionRepository.findAllByUserId(userId)
                .stream()
                .map(s -> ViewSubscriptionDto.valueOf(s))
                .toList();
    }

    public void patchSubscription(String id, Claims claims) {
        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SUBSCRIPTION_NOT_EXISTS"));
        String userId = claims.get("id", String.class);

        if (!s.userId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        s.remainingTimesOfMeeting += s.product.timesOfMeeting;
        s.amount = s.amount.add(s.product.price);
        subscriptionRepository.save(s);
    }

    public Subscription getSubscriptionById(String subscriptionId) {
        return subscriptionRepository.findById(subscriptionId).orElse(null);
    }

    public void paid(String subscriptionId) {
        Subscription s = subscriptionRepository.findById(subscriptionId).orElse(null);
        if (s == null) {
            return;
        }
        s.status = Subscription.Status.ACTIVE;
        subscriptionRepository.save(s);
    }

}
