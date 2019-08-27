
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ConferenceComment;

@Repository
public interface ConferenceCommentRepository extends JpaRepository<ConferenceComment, Integer> {

	@Query("select avg(1.0*(select count(c) from ConferenceComment c where c.conference.id = a.id)),min(1.0*(select count(c) from ConferenceComment c where c.conference.id = a.id)),max(1.0*(select count(c) from ConferenceComment c where c.conference.id = a.id)),stddev(1.0*(select count(c) from ConferenceComment c where c.conference.id = a.id)) from Conference a ")
	Collection<Double> statsCommentsPerConference();

	@Query("select c from ConferenceComment c where c.conference.id =?1 order by moment desc")
	Collection<ConferenceComment> listCommentsByConference(int conferenceId);

}
