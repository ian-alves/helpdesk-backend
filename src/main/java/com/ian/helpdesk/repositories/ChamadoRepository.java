package com.ian.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ian.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
