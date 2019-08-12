
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ActivityComment;

@Repository
public interface ActivityCommentRepository extends JpaRepository<ActivityComment, Integer> {

	@Query("select avg(1.0*(select count(a) from ActivityComment a where a.activity.id = x.id)),min(1.0*(select count(a) from ActivityComment a where a.activity.id = x.id)),max(1.0*(select count(a) from ActivityComment a where a.activity.id = x.id)),stddev(1.0*(select count(a) from ActivityComment a where a.activity.id = x.id)) from Activity x ")
	Collection<Double> statsCommentsPerActivity();
}
