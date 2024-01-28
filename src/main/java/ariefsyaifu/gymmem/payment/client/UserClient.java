package ariefsyaifu.gymmem.payment.client;

import org.springframework.stereotype.Component;

import ariefsyaifu.gymmem.user.service.UserService;

@Component
public class UserClient {

    public UserClient(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    public boolean validateCreditCard(String userId, String creditCardId) {
        return userService.validateCreditCard(userId, creditCardId);
    }

}
