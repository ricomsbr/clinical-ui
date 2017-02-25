package br.com.ackta.clinical.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ackta.clinical.data.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
