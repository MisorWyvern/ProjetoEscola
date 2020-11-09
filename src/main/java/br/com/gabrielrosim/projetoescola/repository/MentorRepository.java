package br.com.gabrielrosim.projetoescola.repository;

import br.com.gabrielrosim.projetoescola.model.Mentor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorRepository extends CrudRepository<Mentor,Long> {
    Optional<List<Mentor>> findByActive(Boolean active);
    Optional<Mentor> findByCpf(String cpf);
}
