
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select c from Conference c where ((c.title like %?1%)or (c.summary like %?1%)or (c.venue like %?1%))")
	Collection<Conference> searchConferencesKeyWord(String keyword);

	@Query("select c from Conference c where ((c.submission >= ?1) and (c.submission <= ?2)) ")
	Collection<Conference> findSubmissionLastFiveDays(Date lastDate, Date actualDate);

	@Query("select c from Conference c where ((c.notification >= ?1) and (c.notification <= ?2)) ")
	Collection<Conference> findNotificationLessFiveDays(Date actualDate, Date nextDate);

	@Query("select c from Conference c where ((c.cameraReady >= ?1) and (c.cameraReady <= ?2)) ")
	Collection<Conference> findCameraReadyLessFiveDays(Date actualDate, Date nextDate);

	@Query("select c from Conference c where ((c.startDate >= ?1) and (c.startDate <= ?2)) ")
	Collection<Conference> findStartDateLessFiveDays(Date actualDate, Date nextDate);

}
