package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAluno(){
        return new ResponseEntity<List<AlunoDTO>>(alunoService.getAlunos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> criarAluno(@RequestBody AlunoDTO alunoDTO){
        AlunoDTO savedAluno = alunoService.criarAluno(alunoDTO);
        System.out.println("Saved Aluno [Controller]: " + savedAluno);
        URI uri = URI.create(String.format("/aluno/%d", savedAluno.getId()));
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarAluno(@PathVariable("id") Long id, @RequestBody AlunoDTO alunoDTO){

    }

}
