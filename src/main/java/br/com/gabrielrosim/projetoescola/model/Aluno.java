package br.com.gabrielrosim.projetoescola.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long id;
    @Column(columnDefinition = "VARCHAR(50)")
    private String nome;
    @Column(unique = true, columnDefinition = "VARCHAR(14)")
    private String cpf;
    @ManyToOne
    @JoinColumn(name="id_programa")
    private Programa programa;
    @OneToMany(mappedBy = "aluno")
    private List<Mentoria> mentorias;
    @OneToMany(mappedBy = "aluno")
    private List<Avaliacao> avaliacoes;
    @Column(columnDefinition = "TINYINT(1) default 0")
    private Boolean active;


    @Deprecated
    public Aluno() {
    }

    public Aluno(String nome, String cpf, Programa programa) {
        this.nome = nome;
        this.cpf = cpf;
        this.programa = programa;
        this.active = false;
        this.mentorias = List.of();
        this.avaliacoes = List.of();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Boolean getActive() {
        return active;
    }

    public List<Mentoria> getMentorias() {
        return mentorias;
    }

    public void setMentorias(List<Mentoria> mentorias) {
        this.mentorias = mentorias;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", programa=" + programa +
                ", active=" + active +
                '}';
    }
}
