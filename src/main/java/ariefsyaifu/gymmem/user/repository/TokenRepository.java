package ariefsyaifu.gymmem.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ariefsyaifu.gymmem.user.model.Token;

public interface TokenRepository extends JpaRepository<Token, String> {

    Token findByRefreshToken(String refreshToken);

    Optional<Token> findByUser_Id(String id);

}
