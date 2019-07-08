package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Actor sender;
	private Actor recipient;
	private Date moment;
	private String subject;
	private String body;
	private String topic;
	
	@NotNull
	@OneToOne(optional=false)
	public Actor getSender() {
		return sender;
	}
	
	public void setSender(Actor sender) {
		this.sender = sender;
	}
	
	@NotNull
	@OneToOne(optional=false)
	public Actor getRecipient() {
		return recipient;
	}
	
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return moment;
	}
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@NotBlank
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	@NotBlank
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
}
