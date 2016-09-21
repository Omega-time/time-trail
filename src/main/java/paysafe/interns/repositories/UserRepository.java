package paysafe.interns.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import paysafe.interns.models.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, String> {
    public UserInfo findOneByEmail(String email);
}
