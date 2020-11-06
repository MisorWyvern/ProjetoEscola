package br.com.gabrielrosim.projetoescola.repository;

import br.com.gabrielrosim.projetoescola.model.TipoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoAvaliacaoRepository extends JpaRepository<TipoAvaliacao, Long> {
    Optional<TipoAvaliacao> findByCodigo(String codigo);
}
