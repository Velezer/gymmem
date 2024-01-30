package ariefsyaifu.gymmem.payment.dto;

import java.math.BigDecimal;

import ariefsyaifu.gymmem.payment.model.TransactionHistory;

public class TransactionHistoryDto {

    public String id;
    public String subscriptionId;
    public BigDecimal amount;
    public TransactionHistory.Status status;

    public static TransactionHistoryDto valueOf(TransactionHistory th) {
        TransactionHistoryDto dto = new TransactionHistoryDto();
        dto.id = th.id;
        dto.subscriptionId = th.subscriptionId;
        dto.amount = th.amount;
        dto.status = th.status;
        return dto;
    }

}
