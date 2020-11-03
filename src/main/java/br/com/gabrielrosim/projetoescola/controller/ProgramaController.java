package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.ProgramaDTO;
import br.com.gabrielrosim.projetoescola.service.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
