package ariefsyaifu.gymmem.otp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ariefsyaifu.gymmem.otp.model.Otp;

public interface OtpRepository extends JpaRepository<Otp, String> {

    Otp findByKey(String email);
    
}
