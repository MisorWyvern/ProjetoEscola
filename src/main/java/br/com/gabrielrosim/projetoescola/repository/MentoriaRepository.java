package br.com.gabrielrosim.projetoescola.repository;

import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.model.Mentoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentoriaRepository extends JpaRepository<Mentoria, Long> {
    Optional<List<Mentoria>> findByActive(Boolean active);
    Optional<Mentoria> findByAlunoAndMentorAndDisciplina(Aluno aluno, Mentor mentor, Disciplina disciplina);
}
