package br.com.ackta.clinical.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.PersonalData;

@Repository
@Transactional
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

}
