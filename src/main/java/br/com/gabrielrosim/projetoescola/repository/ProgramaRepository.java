package br.com.gabrielrosim.projetoescola.repository;

import br.com.gabrielrosim.projetoescola.model.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long> {
}
