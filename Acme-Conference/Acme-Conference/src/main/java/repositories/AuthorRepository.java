
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("select a from Author a where a.userAccount.id=?1")
	Author findByUserId(int id);

	@Query("select r.author from Registration r where r.conference.id=?1")
	Collection<Author> getAuthorsRegisterInConference(int conferenceId);

	@Query("select s.author from Submission s where s.conference.id=?1")
	Collection<Author> getAuthorsSubmittedToConference(int conferenceId);

}
