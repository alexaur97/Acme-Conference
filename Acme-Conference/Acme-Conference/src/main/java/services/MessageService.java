
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;
import domain.SpamWord;
import forms.BroadcastConferenceForm;
import forms.BroadcastUsersForm;

@Service
@Transactional
public class MessageService {

	//Managed repository -------------------
	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SpamWordService			spamWordService;

	@Autowired
	private Validator				validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public MessageService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Message create() {
		Message result;

		result = new Message();

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();

		return result;
	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);

		return result;
	}

	public void save(final Message message) {
		Assert.notNull(message);
		this.messageRepository.save(message);
	}

	public void delete(final Message message) {
		this.messageRepository.delete(message);
	}

	//Other Methods--------------------

	public Collection<Message> findRecives(final int id) {
		final Collection<Message> result = this.messageRepository.findRecives(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findRecivesByTopic(final int id) {
		final Collection<Message> result = this.messageRepository.findRecivesByTopic(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findRecivesBySender(final int id) {
		final Collection<Message> result = this.messageRepository.findRecivesBySender(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findRecivesByRecipient(final int id) {
		final Collection<Message> result = this.messageRepository.findRecivesByRecipient(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findSend(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSend(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findSendByRecipient(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSendByRecipient(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findSendBySender(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSendBySender(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findSendByTopic(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSendByTopic(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findSpam(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSpam(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findSpamByTopic(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSpamByTopic(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findSpamBySender(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSpamBySender(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findSpamByRecipient(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSpamByRecipient(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findSender(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSender(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findSenderSpam(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSenderSpam(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findDeleted(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findDeleted(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findDeletedByTopic(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findDeletedByTopic(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findDeletedBySender(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findDeletedBySender(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findDeletedByRecipient(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findDeletedByRecipient(id);
		Assert.notNull(result);
		return result;
	}

	public Message reconstruct(final Message msg, final BindingResult binding) {
		final Message res = msg;
		final Actor a = this.actorService.findByPrincipal();
		msg.setSender(a);
		msg.setOwner(a);
		final Date moment = new Date();
		msg.setMoment(moment);
		msg.setSpam(false);
		msg.setDeleted(false);
		msg.setCopy(false);
		final Collection<String> aux = msg.getTags();
		final ArrayList<String> tags = new ArrayList<>();
		for (final String t : aux)
			if (!t.trim().isEmpty())
				tags.add(t);
		msg.setTags(tags);
		System.out.println(binding);
		this.validator.validate(res, binding);
		return res;
	}

	public List<String> spamwords(final Collection<SpamWord> sw) {

		Collection<SpamWord> spamwords = new ArrayList<>();
		spamwords = this.spamWordService.findAll();
		final List<SpamWord> list = new ArrayList<>(spamwords);
		final List<String> res = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			final String palabra = list.get(i).getWord().toUpperCase();
			res.add(palabra);
		}

		return res;

	}

	public void isSpam(final Message message) {
		Collection<SpamWord> spamwords = new ArrayList<>();
		spamwords = this.spamWordService.findAll();
		final String[] mensaje = message.getBody().trim().split(" ");
		List<String> lista = new ArrayList<>();
		lista = Arrays.asList(mensaje);

		final String[] titulo = message.getSubject().trim().split(" ");
		List<String> list = new ArrayList<>();
		list = Arrays.asList(titulo);
		Boolean isSpam = false;

		final List<String> sw = this.spamwords(spamwords);

		for (final String l : list)
			if (sw.contains(l.toUpperCase())) {

				message.setSpam(true);
				final Collection<String> tags = message.getTags();
				tags.add("SPAM");
				message.setTags(tags);
				isSpam = true;
				break;
			} else
				message.setSpam(false);

		if (isSpam == false)
			for (final String j : lista)
				if (sw.contains(j.toUpperCase())) {
					message.setSpam(true);
					final Collection<String> tags = message.getTags();
					tags.add("SPAM");
					message.setTags(tags);
					break;
				} else
					message.setSpam(false);
	}

	public Message recontructForBroadcast(final BroadcastUsersForm broadcastUsersForm, final Actor actor) {
		Assert.notNull(actor);
		Assert.notNull(broadcastUsersForm);
		final Message result = new Message();
		result.setCopy(false);
		result.setDeleted(false);
		result.setMoment(new Date());
		result.setOwner(this.actorService.findByPrincipal());
		result.setRecipient(actor);
		result.setSender(this.actorService.findByPrincipal());
		result.setSpam(false);
		result.setSubject(broadcastUsersForm.getSubject());
		result.setBody(broadcastUsersForm.getBody());
		result.setTags(broadcastUsersForm.getTags());
		result.setTopic(broadcastUsersForm.getTopic());
		Assert.notNull(result);
		return result;
	}

	public Message createMessageCopy(final Message message) {
		final Message result = message;
		result.setCopy(true);
		result.setOwner(message.getRecipient());
		return result;
	}

	public Message recontructForBroadcastToConference(final BroadcastConferenceForm broadcastConferenceForm, final Actor actor) {
		Assert.notNull(actor);
		Assert.notNull(broadcastConferenceForm);
		final Message result = new Message();
		result.setCopy(false);
		result.setDeleted(false);
		result.setMoment(new Date());
		result.setOwner(this.actorService.findByPrincipal());
		result.setRecipient(actor);
		result.setSender(this.actorService.findByPrincipal());
		result.setSpam(false);
		result.setSubject(broadcastConferenceForm.getSubject());
		result.setBody(broadcastConferenceForm.getBody());
		result.setTags(broadcastConferenceForm.getTags());
		result.setTopic(broadcastConferenceForm.getTopic());
		Assert.notNull(result);
		return result;
	}

}
