
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TutorialComment;

@Repository
public interface TutorialCommentRepository extends JpaRepository<TutorialComment, Integer> {

	@Query("select avg(1.0*(select count(a) from TutorialComment a where a.tutorial.id = x.id)),min(1.0*(select count(a) from TutorialComment a where a.tutorial.id = x.id)),max(1.0*(select count(a) from TutorialComment a where a.tutorial.id = x.id)),stddev(1.0*(select count(a) from TutorialComment a where a.tutorial.id = x.id)) from Tutorial x ")
	Collection<Double> statsCommentsPerTutorial();

	@Query("select c from TutorialComment c where c.tutorial.id =?1 order by moment desc")
	Collection<TutorialComment> listCommentsByTutorial(int tutorialId);

}
