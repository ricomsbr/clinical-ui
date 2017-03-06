package br.com.ackta.clinical.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ackta.clinical.data.entity.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
