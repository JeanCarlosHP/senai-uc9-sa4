package br.com.senai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senai.model.Despesas;

public interface DespesaRepository extends JpaRepository<Despesas, Long>{}
