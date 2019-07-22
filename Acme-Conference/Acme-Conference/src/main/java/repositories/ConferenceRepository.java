
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select c from Conference c where ((c.title like %?1%)or (c.summary like %?1%)or (c.venue like %?1%))")
	Collection<Conference> searchConferencesKeyWord(String keyword);

}
