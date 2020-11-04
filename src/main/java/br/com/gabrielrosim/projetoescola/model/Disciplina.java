package br.com.gabrielrosim.projetoescola.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_disciplina")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(50)")
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private Double nota;
    @OneToMany(mappedBy = "disciplina")
    private List<Mentoria> mentorias;
}
