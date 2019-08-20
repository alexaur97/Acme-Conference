
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	@Query("select r from Registration r where r.author.id=?1")
	Collection<Registration> findRegistrationByAuthor(int id);

	@Query("select avg(1.0*(select count(r) from Registration r where r.conference.id = c.id)),min(1.0*(select count(r) from Registration r where r.conference.id = c.id)),max(1.0*(select count(r) from Registration r where r.conference.id = c.id)),stddev(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c ")
	Collection<Double> statsRegistrationsPerConference();
}
