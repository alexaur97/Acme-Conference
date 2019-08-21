
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PaperRepository;
import domain.Paper;

@Service
@Transactional
public class PaperService {

	@Autowired
	private PaperRepository	paperRepository;


	public Paper save(final Paper paper) {
		Assert.notNull(paper);

		final Paper res = this.paperRepository.save(paper);
		return res;
	}

}
