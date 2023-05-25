package Team.server.repository;

import Team.server.domain.Review;
import Team.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    public Review deleteByRid(Long rid);

    public Review findByRid(Long rid);

}
