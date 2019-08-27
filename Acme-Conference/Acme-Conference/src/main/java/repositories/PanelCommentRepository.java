
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PanelComment;

@Repository
public interface PanelCommentRepository extends JpaRepository<PanelComment, Integer> {

	@Query("select avg(1.0*(select count(a) from PanelComment a where a.panel.id = x.id)),min(1.0*(select count(a) from PanelComment a where a.panel.id = x.id)),max(1.0*(select count(a) from PanelComment a where a.panel.id = x.id)),stddev(1.0*(select count(a) from PanelComment a where a.panel.id = x.id)) from Panel x ")
	Collection<Double> statsCommentsPerPanel();

	@Query("select c from PanelComment c where c.panel.id =?1 order by moment desc")
	Collection<PanelComment> listCommentsByPanel(int panelId);
}
