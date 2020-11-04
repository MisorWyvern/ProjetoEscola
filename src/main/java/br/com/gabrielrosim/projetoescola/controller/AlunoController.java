package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAlunos(){
        return new ResponseEntity<List<AlunoDTO>>(alunoService.getAlunos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAlunos(@PathVariable("id") Long id){
        Optional<AlunoDTO> alunoDTO = alunoService.getAlunoByIndex(id);
        return alunoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> criarAluno(@Validated @RequestBody AlunoDTO alunoDTO){
        Optional<AlunoDTO> savedAluno = alunoService.criarAluno(alunoDTO);
        if(savedAluno.isPresent()){
            URI location = URI.create(String.format("/aluno/%d", savedAluno.get().getId()));
            return ResponseEntity.created(location).build();
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAluno(@PathVariable("id") Long id, @Validated @RequestBody AlunoDTO alunoDTO){
        alunoService.atualizarAluno(id, alunoDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteAluno(@PathVariable("id") Long id){
        return alunoService.deletarAluno(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
