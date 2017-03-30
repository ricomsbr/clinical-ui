package br.com.ackta.clinical.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ackta.clinical.data.entity.PersonalData;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

	Optional<PersonalData> findFirstByCpf(String cpf);

	Optional<PersonalData> findFirstByMail(String mail);

	Optional<PersonalData> findFirstByName(String name);

}
