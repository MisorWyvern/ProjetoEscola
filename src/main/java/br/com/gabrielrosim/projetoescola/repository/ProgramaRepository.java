package br.com.gabrielrosim.projetoescola.repository;

import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.model.Programa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long> {
    @Query(
            value = "SELECT m FROM Programa p INNER JOIN p.mentores m WHERE p.id = :id_programa")
    Page<Mentor> findAllMentoresFromProgramaById(@Param("id_programa") Long id, Pageable pageable);
}

 /*value = "SELECT m.id_mentor, m.nome, m.cpf, m.active\n" +
                    "FROM (tb_programas p \n" +
                    "INNER JOIN tb_mentor_programa mp ON(p.id_programa = mp.id_programa)\n" +
                    "INNER JOIN tb_mentores m ON(mp.id_mentor = m.id_mentor))\n" +
                    "WHERE p.id_programa = :id_programa",
            nativeQuery = true)*/
