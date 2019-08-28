
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//@Query("") 
	//Method 

	@Query("select f from Finder f where f.author.id = ?1")
	Finder getFinderFromAuthor(int authorId);
}
