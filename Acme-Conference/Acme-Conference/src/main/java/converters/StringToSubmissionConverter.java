package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Actor;
import domain.Reviewer;
import domain.Sponsor;
import domain.Submission;
import repositories.ActorRepository;
import repositories.ReviewerRepository;
import repositories.SponsorRepository;
import repositories.SubmissionRepository;

@Component
@Transactional
public class StringToSubmissionConverter implements Converter<String, Submission> {

	@Autowired
	SubmissionRepository	submissionRepository;


	@Override
	public Submission convert(final String text) {
		Submission result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.submissionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
