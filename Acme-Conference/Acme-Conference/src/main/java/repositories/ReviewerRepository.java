
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Reviewer;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {

	@Query("select a from Reviewer a where a.userAccount.id=?1")
	Reviewer findByUserId(int id);

	@Query("select r from Reviewer r where r.submission is empty")
	Collection<Reviewer> findWithoutSubmission();

	@Query("select r from Reviewer r where r.submission.id=?1")
	Collection<Reviewer> findBySubmissionID(int s);

}
