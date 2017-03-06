package br.com.ackta.clinical.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.repository.AddressRepository;

@Service
@Transactional
public class AddressService implements IAddressService {

	private AddressRepository addressRepository;

	@Autowired
	public AddressService(AddressRepository addressRepository) {
		super();
		this.addressRepository = addressRepository;
	}

	@Override
	public Address insert(Address address) {
		Address result = addressRepository.save(address);
		return result;
	}

}
