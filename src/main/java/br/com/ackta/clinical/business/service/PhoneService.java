package br.com.ackta.clinical.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Phone;
import br.com.ackta.clinical.data.repository.PhoneRepository;

@Service
@Transactional
public class PhoneService implements IPhoneService {

	private PhoneRepository phoneRepository;

	@Autowired
	public PhoneService(PhoneRepository phoneRepository) {
		super();
		this.phoneRepository = phoneRepository;
	}

	@Override
	public Phone insert(Phone phone) {
		Phone result = phoneRepository.save(phone);
		return result;
	}

}
