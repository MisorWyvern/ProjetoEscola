package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.DisciplinaDTO;
import br.com.gabrielrosim.projetoescola.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> getDisciplinas(){
        return new ResponseEntity<List<DisciplinaDTO>>(disciplinaService.getDisciplinas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> getDisciplinaByIndex(@PathVariable Long id){
        Optional<DisciplinaDTO> disciplinaDTO = disciplinaService.getDisciplinaByIndex(id);
        return disciplinaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> createDisciplina(@Validated @RequestBody DisciplinaDTO dto){
        DisciplinaDTO savedDisciplina = disciplinaService.criarDisciplina(dto);
        URI location = URI.create(String.format("/disciplina/%d", savedDisciplina.getId()));
        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateDisciplina(@PathVariable Long id, @Validated @RequestBody DisciplinaDTO dto){
        disciplinaService.atualizarDisciplina(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDisciplina(@PathVariable Long id){
        if(disciplinaService.deletarDisciplina(id)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
