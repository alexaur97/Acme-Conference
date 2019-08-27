
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PresentationComment;

@Repository
public interface PresentationCommentRepository extends JpaRepository<PresentationComment, Integer> {

	@Query("select avg(1.0*(select count(a) from PresentationComment a where a.presentation.id = x.id)),min(1.0*(select count(a) from PresentationComment a where a.presentation.id = x.id)),max(1.0*(select count(a) from PresentationComment a where a.presentation.id = x.id)),stddev(1.0*(select count(a) from PresentationComment a where a.presentation.id = x.id)) from Presentation x ")
	Collection<Double> statsCommentsPerPresentation();

	@Query("select c from PresentationComment c where c.presentation.id =?1 order by moment desc")
	Collection<PresentationComment> listCommentPresentation(int presentationId);

}
