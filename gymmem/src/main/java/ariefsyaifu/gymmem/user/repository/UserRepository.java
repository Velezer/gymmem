package ariefsyaifu.gymmem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ariefsyaifu.gymmem.user.model.User;


public interface UserRepository extends JpaRepository<User, String> {

    public boolean existsByEmail(String email);

    public User findByEmail(String email);

}
