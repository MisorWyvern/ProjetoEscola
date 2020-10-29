package br.com.gabrielrosim.projetoescola.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
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
    @OneToMany(mappedBy = "mentor")
    private List<Mentoria> mentorias;
    private Boolean active;

    public Mentor(Long id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        programas = List.of();
        mentorias = List.of();
        active = false;
    }
}
