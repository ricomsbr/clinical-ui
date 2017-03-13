package br.com.ackta.clinical.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ackta.clinical.data.entity.PersonalData;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

	PersonalData findByNameOrCpfOrMail(String name, String cpf, String mail);

}
