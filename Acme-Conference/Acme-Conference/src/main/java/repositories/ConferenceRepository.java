
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select c from Conference c where c.mode='FINAL' and ((c.title like %?1%)or (c.summary like %?1%)or (c.venue like %?1%))")
	Collection<Conference> searchConferencesKeyWord(String keyword);

	@Query("select c from Conference c where ((c.submissionDeadline >= ?1) and (c.submissionDeadline <= ?2)) ")
	Collection<Conference> findSubmissionLastFiveDays(Date lastDate, Date actualDate);

	@Query("select c from Conference c where ((c.notification >= ?1) and (c.notification <= ?2)) ")
	Collection<Conference> findNotificationLessFiveDays(Date actualDate, Date nextDate);

	@Query("select c from Conference c where ((c.cameraReady >= ?1) and (c.cameraReady <= ?2)) ")
	Collection<Conference> findCameraReadyLessFiveDays(Date actualDate, Date nextDate);

	@Query("select c from Conference c where ((c.startDate >= ?1) and (c.startDate <= ?2)) ")
	Collection<Conference> findStartDateLessFiveDays(Date actualDate, Date nextDate);

	@Query("select c from Conference c where c.category.id=?1")
	Collection<Conference> findByCateogry(int id);

	@Query("select avg(1.0*(select count(c) from Conference c where c.category.id = a.id)),min(1.0*(select count(c) from Conference c where c.category.id = a.id)),max(1.0*(select count(c) from Conference c where c.category.id = a.id)),stddev(1.0*(select count(c) from Conference c where c.category.id = a.id)) from Category a ")
	Collection<Double> statsConferencesPerCategory();

	@Query("select avg(1.0*(c.fee)),min(1.0*(c.fee)),max(1.0*(c.fee)),stddev(c.fee) from Conference c ")
	Collection<Double> statsConferencesFee();

	@Query("select c from Conference c where c.endDate < ?1 and c.mode='FINAL' order by c.category.name")
	Collection<Conference> findPastConference(Date actualDate);

	@Query("select c from Conference c where c.startDate < ?1 and c.endDate > ?1 and c.mode='FINAL' order by c.category.name")
	Collection<Conference> findRunningConference(Date actualDate);

	@Query("select c from Conference c where c.submissionDeadline >= ?1 ")
	Collection<Conference> findOpenConferences(Date actualDate);

	@Query("select avg(1.0*(DATEDIFF(c.endDate,c.startDate))),min(1.0*(DATEDIFF(c.endDate,c.startDate))),max(1.0*(DATEDIFF(c.endDate,c.startDate))),stddev(DATEDIFF(c.endDate,c.startDate)) from Conference c ")
	Collection<Double> statsConferencesDays();

	@Query("select c from Conference c where c.startDate >= ?1 and c.mode='FINAL' order by c.category.name")
	Collection<Conference> findNextConferences(Date date);

	@Query("select c from Conference c where  c.mode='DRAFT'")
	Collection<Conference> conferencesDraft();

	@Query("select c from Conference c where  c.mode='FINAL'")
	Collection<Conference> findConferencesFinal();

	@Query("select c from Conference c where c.fee <= ?1")
	Collection<Conference> findConferencesByMaxFee(Double maxFee);

}
