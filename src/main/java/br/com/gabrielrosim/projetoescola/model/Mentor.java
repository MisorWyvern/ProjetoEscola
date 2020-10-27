package br.com.gabrielrosim.projetoescola.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_mentores")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mentor")
    private Long id;
    private String nome;
    private String cpf;
    @ManyToMany
    @JoinTable(name="tb_mentor_programa",
            joinColumns = {@JoinColumn(name="id_mentor")},
            inverseJoinColumns = {@JoinColumn(name="id_programa")})
    private List<Programa> programas;
}
