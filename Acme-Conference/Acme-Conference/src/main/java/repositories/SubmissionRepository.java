
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("select s from Submission s where s.conference.id = ?1")
	Collection<Submission> findSubmissionsByConference(int id);

	@Query("select s from Submission s where s.conference.id = ?1 and s.status='ACCEPTED'")
	Collection<Submission> findAcceptedSubmissionsByConference(int id);

	@Query("select s from Submission s where s.conference.id = ?1 and s.status='REJECTED'")
	Collection<Submission> findRejectedSubmissionsByConference(int id);

	@Query("select avg(1.0*(select count(s) from Submission s where s.conference.id = c.id)),min(1.0*(select count(s) from Submission s where s.conference.id = c.id)),max(1.0*(select count(s) from Submission s where s.conference.id = c.id)),stddev(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c ")
	Collection<Double> statsSubmissionsPerConference();

	@Query("select s from Submission s where s.author.id = ?1")
	Collection<Submission> findMySubmissions(int authorId);

	@Query("select s from Submission s where s.status='UNDER-REVIEW'")
	Collection<Submission> findSubmissionUnderReview();

	@Query("select s from Submission s where s.status='ACCEPTED'")
	Collection<Submission> findSubmissionAccepted();

	@Query("select s from Submission s where s.status='REJECTED'")
	Collection<Submission> findSubmissionRejected();

	@Query("select r.submission from Reviewer r where r.submission.status='UNDER-REVIEW'")
	Collection<Submission> findAssigned();

	@Query("select r.submission from Report r where r.reviewer.id = ?1")
	Collection<Submission> findSubmissionsFromReport(int id);
}
