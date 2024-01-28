package ariefsyaifu.gymmem.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ariefsyaifu.gymmem.user.model.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {

    boolean existsByIdAndUser_Id(String creditCardId, String userId);

    List<CreditCard> findAllByUser_Id(String userId);

}
