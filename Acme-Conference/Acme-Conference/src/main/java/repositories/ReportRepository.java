
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r where r.submission.id = ?1")
	Collection<Report> findReportsBySubmission(int id);

	@Query("select r from Report r where r.submission.id = ?1 and r.decision='ACCEPT'")
	Collection<Report> findAcceptReportsBySubmission(int id);

	@Query("select r from Report r where r.submission.id = ?1 and r.decision='REJECT'")
	Collection<Report> findRejectReportsBySubmission(int id);

	@Query("select r from Report r where r.submission.id = ?1 and r.submission.status<>'UNDER-REVIEW' and r.submission.author.id=?2")
	Collection<Report> findReportsInAcceptedSubmission(int id, int authorId);

	@Query("select r from Report r where r.reviewer.id = ?1")
	Collection<Report> findReportsByPrincipal(int id);
}
