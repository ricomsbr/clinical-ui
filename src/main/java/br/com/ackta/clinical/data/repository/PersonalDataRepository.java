package br.com.ackta.clinical.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.PersonalData;

@Repository
@Transactional
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

	List<PersonalData> findByCpf(String cpf);

	List<PersonalData> findByMailIgnoreCase(String mail);

	List<PersonalData> findByNameIgnoreCase(String name);

	Optional<PersonalData> findFirstByCpf(String cpf);

	Optional<PersonalData> findFirstByMailIgnoreCase(String mail);

	Optional<PersonalData> findFirstByNameIgnoreCase(String name);

}
