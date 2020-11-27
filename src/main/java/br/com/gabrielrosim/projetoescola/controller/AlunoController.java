package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<Page<AlunoDTO>> getAlunos(@RequestParam Optional<Boolean> active,
                                                    @PageableDefault(sort="nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<Page<AlunoDTO>>(alunoService.getAlunos(active, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAlunos(@PathVariable("id") Long id) {
        Optional<AlunoDTO> alunoDTO = alunoService.getAlunoByIndex(id);
        return alunoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> criarAluno(@Validated @RequestBody AlunoDTO alunoDTO) {
        Optional<AlunoDTO> savedAluno = alunoService.criarAluno(alunoDTO);
        if (savedAluno.isPresent()) {
            URI location = URI.create(String.format("/aluno/%d", savedAluno.get().getId()));
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> updateAluno(@PathVariable("id") Long id, @Validated @RequestBody AlunoDTO alunoDTO) {
        return alunoService.atualizarAluno(id, alunoDTO) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<Boolean> activateAluno(@PathVariable Long id){
        return alunoService.activateAluno(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteAluno(@PathVariable("id") Long id) {
        return alunoService.deletarAluno(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
