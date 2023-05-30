package Team.server.repository;

import Team.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Modifying
    @Query("UPDATE User u SET u.mbti = :mbti WHERE u.id = :id")
    void updatePartialUser(@Param("id") Long id, @Param("mbti") String mbti);

    public User findByName(String name);
    // public User findById(long id);
}
