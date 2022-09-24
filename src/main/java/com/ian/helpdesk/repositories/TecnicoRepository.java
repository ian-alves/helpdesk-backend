package com.ian.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ian.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
