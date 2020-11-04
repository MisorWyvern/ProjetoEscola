package br.com.gabrielrosim.projetoescola.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@Table(name = "tb_mentoria", uniqueConstraints = @UniqueConstraint(columnNames={"id_aluno","id_mentor"}))
@NoArgsConstructor
public class Mentoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mentoria")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_mentor")
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private Boolean active;
}
