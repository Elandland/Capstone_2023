package Team.server.repository;

import Team.server.domain.User;
import Team.server.service.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);

}
