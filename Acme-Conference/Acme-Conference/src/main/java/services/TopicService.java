
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TopicRepository;
import domain.Topic;

@Service
@Transactional
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private Validator validator;

	// COnstructors -------------------------
	public TopicService() {
		super();
	}

	public Topic create() {
		Topic result;

		result = new Topic();

		return result;
	}

	public Collection<Topic> findAll() {
		Collection<Topic> result;

		result = this.topicRepository.findAll();

		return result;
	}

	public Topic findOne(final int topicId) {
		Topic result;

		result = this.topicRepository.findOne(topicId);

		return result;
	}

	public Topic save(final Topic topic) {
		Assert.notNull(topic);

		return this.topicRepository.save(topic);
	}

	public void delete(final Topic topic) {
		this.topicRepository.delete(topic);
	}

	public Topic reconstruct(final Topic topic, final BindingResult binding) {
		final Topic res = topic;
		this.validator.validate(res, binding);
		return res;
	}

	public Topic findOtherTopic() {
		return this.topicRepository.findOtherTopic();
	}
}
