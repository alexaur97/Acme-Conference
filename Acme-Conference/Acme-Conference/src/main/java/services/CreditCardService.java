
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private Validator				validator;


	public CreditCard create() {
		return new CreditCard();
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		final CreditCard result = this.creditCardRepository.save(creditCard);
		return result;
	}
	public void delete(final CreditCard creditCard) {
		this.creditCardRepository.delete(creditCard);
	}

	public CreditCard reconstruct(final CreditCard creditCard, final BindingResult binding) {
		final CreditCard res = creditCard;
		this.validator.validate(res, binding);
		return res;
	}
}
