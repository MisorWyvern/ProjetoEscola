package br.com.gabrielrosim.projetoescola.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_disciplina")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplina")
    private Long id;
    @Column(columnDefinition = "VARCHAR(50)")
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    @OneToMany(mappedBy = "disciplina")
    private List<Mentoria> mentorias;
    @OneToMany(mappedBy = "disciplina")
    private List<Avaliacao> avaliacoes;

}
