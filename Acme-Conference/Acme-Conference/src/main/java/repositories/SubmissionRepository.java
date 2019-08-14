package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;
import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer>{

	@Query("select s from Submission s where s.conference.id = ?1")
	Collection<Submission> findSubmissionsByConference(int id);

	@Query("select s from Submission s where s.conference.id = ?1 and s.status='ACCEPTED'")
	Collection<Submission> findAcceptedSubmissionsByConference(int id);

	@Query("select s from Submission s where s.conference.id = ?1 and s.status='REJECTED'")
	Collection<Submission> findRejectedSubmissionsByConference(int id);
}
