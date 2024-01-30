package ariefsyaifu.gymmem.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ariefsyaifu.gymmem.subscription.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    Subscription findByUserIdAndProduct_Id(String userId, String productId);

    List<Subscription> findAllByUserId(String userId);

}
