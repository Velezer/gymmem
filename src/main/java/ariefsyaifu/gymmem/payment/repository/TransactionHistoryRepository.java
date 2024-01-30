package ariefsyaifu.gymmem.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ariefsyaifu.gymmem.payment.model.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String> {

    List<TransactionHistory> findAllByUserId(String userId);

}
