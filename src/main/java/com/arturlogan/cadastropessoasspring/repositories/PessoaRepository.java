package com.arturlogan.cadastropessoasspring.repositories;

import com.arturlogan.cadastropessoasspring.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
