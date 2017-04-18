package br.com.ackta.clinical.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ackta.clinical.data.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	List<Patient> findByPersonalData_NameIgnoreCase(String name);

	List<Patient> findByPersonalData_MailIgnoreCase(String mail);

	List<Patient> findByPersonalData_Cpf(String cpf);
}
