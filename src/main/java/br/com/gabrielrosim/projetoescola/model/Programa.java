package br.com.gabrielrosim.projetoescola.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_programas")
public class Programa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programa")
    private Long id;
    private String nome;
    private Timestamp dataInicio;
    private Timestamp dataTermino;
    @ManyToMany(mappedBy = "programas")
    private List<Mentor> mentores;
}
