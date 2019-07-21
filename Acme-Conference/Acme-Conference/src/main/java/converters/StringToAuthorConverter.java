package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Actor;
import domain.Author;
import repositories.AuthorRepository;

@Component
@Transactional
public class StringToAuthorConverter implements Converter<String, Author> {

	@Autowired
	AuthorRepository	authorRepository;


	@Override
	public Author convert(final String text) {
		Author result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.authorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
