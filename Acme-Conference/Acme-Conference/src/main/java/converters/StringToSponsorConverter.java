package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Actor;
import domain.Reviewer;
import domain.Sponsor;
import repositories.ActorRepository;
import repositories.ReviewerRepository;
import repositories.SponsorRepository;

@Component
@Transactional
public class StringToSponsorConverter implements Converter<String, Actor> {

	@Autowired
	SponsorRepository	sponsorRepository;


	@Override
	public Sponsor convert(final String text) {
		Sponsor result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.sponsorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
