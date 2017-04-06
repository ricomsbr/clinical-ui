package br.com.ackta.clinical.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ackta.clinical.data.entity.PersonalData;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

	Optional<PersonalData> findByCpf(String cpf);

	Optional<PersonalData> findByNameIgnoreCase(String name);

	Optional<PersonalData> findByMailIgnoreCase(String mail);

}
