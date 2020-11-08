package br.com.gabrielrosim.projetoescola.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_avaliacao", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_disciplina","id_tipo_avaliacao","id_aluno"})})
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;
    @ManyToOne
    @JoinColumn(name = "id_tipo_avaliacao")
    private TipoAvaliacao tipoAvaliacao;
    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;
    private Double nota;
    private LocalDate dataAplicacao;
}
