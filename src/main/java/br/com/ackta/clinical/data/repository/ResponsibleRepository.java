package br.com.ackta.clinical.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Responsible;

@Repository
@Transactional
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

}
