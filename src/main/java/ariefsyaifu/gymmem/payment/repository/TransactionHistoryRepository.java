package ariefsyaifu.gymmem.payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import ariefsyaifu.gymmem.payment.model.TransactionHistory;
import jakarta.persistence.LockModeType;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String> {

    List<TransactionHistory> findAllByUserId(String userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<TransactionHistory> findByIdEquals(String id);


}
