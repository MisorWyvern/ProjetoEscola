package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.ProgramaDTO;
import br.com.gabrielrosim.projetoescola.service.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/programa")
public class ProgramaController {

    @Autowired
    ProgramaService programaService;

    @GetMapping
    public ResponseEntity<List<ProgramaDTO>> getProgramas(){
        return new ResponseEntity<List<ProgramaDTO>>(programaService.getProgramas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaDTO> getProgramaById(@PathVariable Long id){
        Optional<ProgramaDTO> programaDTO = programaService.getProgramaByIndex(id);
        return programaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> createPrograma(@Validated @RequestBody ProgramaDTO dto){
        ProgramaDTO savedPrograma = programaService.criarPrograma(dto);
        URI location = URI.create(String.format("/programa/%d", savedPrograma.getId()));
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePrograma(@PathVariable Long id, @Valid @RequestBody ProgramaDTO dto){
        programaService.atualiarPrograma(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePrograma(@PathVariable Long id){
        programaService.deletarPrograma(id);
    }


}
