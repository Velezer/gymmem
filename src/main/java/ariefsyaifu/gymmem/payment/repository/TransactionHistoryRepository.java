package ariefsyaifu.gymmem.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ariefsyaifu.gymmem.payment.model.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String> {
    
}
