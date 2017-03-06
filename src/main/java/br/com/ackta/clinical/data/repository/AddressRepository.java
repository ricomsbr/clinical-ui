package br.com.ackta.clinical.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ackta.clinical.data.entity.Address;
public interface AddressRepository extends JpaRepository<Address, Long> {

}
