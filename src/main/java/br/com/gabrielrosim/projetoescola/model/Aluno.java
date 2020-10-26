package br.com.gabrielrosim.projetoescola.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    @ManyToOne
    private Programa programa;

    @Deprecated
    public Aluno() {
    }

    public Aluno(String nome, String cpf, Programa programa) {
        this.nome = nome;
        this.cpf = cpf;
        this.programa = programa;
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
}
