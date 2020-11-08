package br.com.gabrielrosim.projetoescola.repository;

import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Avaliacao;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import br.com.gabrielrosim.projetoescola.model.TipoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao> findByDisciplinaAndTipoAvaliacaoAndAluno(Disciplina disciplina, TipoAvaliacao tipoAvaliacao, Aluno aluno);
}
