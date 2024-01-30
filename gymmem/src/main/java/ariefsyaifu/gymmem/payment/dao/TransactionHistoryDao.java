package ariefsyaifu.gymmem.payment.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ariefsyaifu.gymmem.payment.model.TransactionHistory;
import ariefsyaifu.gymmem.payment.repository.TransactionHistoryRepository;

@Component
public class TransactionHistoryDao {

    @Autowired
    public TransactionHistoryDao(
            TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    private TransactionHistoryRepository transactionHistoryRepository;

    @Transactional
    public void paid(String id) {
        TransactionHistory th = transactionHistoryRepository.findByIdEquals(id).orElse(null);
        if (th == null) {
            return;
        }
        th.status = TransactionHistory.Status.PAID;
        transactionHistoryRepository.save(th);
    }
    
    @Transactional
    public void fail(String id) {
        TransactionHistory th = transactionHistoryRepository.findByIdEquals(id).orElse(null);
        if (th == null) {
            return;
        }
        th.status = TransactionHistory.Status.FAILED;
        transactionHistoryRepository.save(th);
    }

}
