package ariefsyaifu.gymmem.payment.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import ariefsyaifu.gymmem.client.OtpClient;
import ariefsyaifu.gymmem.otp.model.Otp;
import ariefsyaifu.gymmem.payment.client.SubscriptionClient;
import ariefsyaifu.gymmem.payment.client.UserClient;
import ariefsyaifu.gymmem.payment.dto.CreatePaymentRequestBody;
import ariefsyaifu.gymmem.payment.dto.CreatePaymentResponse;
import ariefsyaifu.gymmem.payment.dto.PatchPaymentRequestBody;
import ariefsyaifu.gymmem.payment.model.TransactionHistory;
import ariefsyaifu.gymmem.payment.repository.TransactionHistoryRepository;
import ariefsyaifu.gymmem.subscription.model.Product;
import ariefsyaifu.gymmem.subscription.model.Subscription;
import io.jsonwebtoken.Claims;

@Component
public class TransactionHistoryService {

    public TransactionHistoryService(
            TransactionHistoryRepository transactionHistoryRepository,
            UserClient userClient,
            SubscriptionClient subscriptionClient,
            OtpClient otpClient) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.userClient = userClient;
        this.subscriptionClient = subscriptionClient;
        this.otpClient = otpClient;
    }

    private OtpClient otpClient;
    private SubscriptionClient subscriptionClient;
    private UserClient userClient;
    private TransactionHistoryRepository transactionHistoryRepository;

    public CreatePaymentResponse createPayment(CreatePaymentRequestBody params, Claims claims) {
        String userId = claims.get("id", String.class);

        Subscription subscription = subscriptionClient.getSubscriptionById(params.subscriptionId);
        if (subscription == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SUBSCRIPTION_NOT_EXISTS");
        }
        if (!subscription.status.equals(Subscription.Status.PENDING)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SUBSCRIPTION_NOT_PENDING");
        }

        boolean isValid = userClient.validateCreditCard(userId, params.creditCardId);
        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        TransactionHistory th = new TransactionHistory();
        th.subscriptionId = params.subscriptionId;
        th.creditCardId = params.creditCardId;
        th.userId = userId;
        th.amount = subscription.product.price;
        th.status = TransactionHistory.Status.PENDING;
        transactionHistoryRepository.save(th);

        return CreatePaymentResponse.valueOf(th.id);
    }

    public void patchPayment(String id, PatchPaymentRequestBody params, Claims claims) {
        String email = claims.get("email", String.class);
        TransactionHistory th = transactionHistoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PENDING_PAYMENT_NOT_FOUND"));

        boolean isValid = otpClient.validateOtp(params.otpId, email, params.otpValue, Otp.Type.PAYMENT);
        if (!isValid) {
            th.status = TransactionHistory.Status.FAILED;
            transactionHistoryRepository.save(th);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP_INVALID");
        }

        th.status = TransactionHistory.Status.PAID;
        transactionHistoryRepository.save(th);

        subscriptionClient.paid(th.subscriptionId);
    }

}