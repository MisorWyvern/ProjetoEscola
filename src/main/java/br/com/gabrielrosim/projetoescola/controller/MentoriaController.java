package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.MentoriaDTO;
import br.com.gabrielrosim.projetoescola.model.Mentoria;
import br.com.gabrielrosim.projetoescola.service.MentoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mentoria")
public class MentoriaController {

    @Autowired
    private MentoriaService mentoriaService;

    @GetMapping
    public ResponseEntity<List<MentoriaDTO>> getMentorias(@RequestParam Optional<Boolean> active){
        return new ResponseEntity<List<MentoriaDTO>>(mentoriaService.getMentorias(active), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentoriaDTO> getMentoriaById(@PathVariable Long id){
        Optional<MentoriaDTO> mentoriaDTO = mentoriaService.getMentoriaByIndex(id);
        return mentoriaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> createMentoria(@Validated @RequestBody MentoriaDTO dto){
        Optional<MentoriaDTO> savedMentoria = mentoriaService.criarMentoria(dto);

        if(savedMentoria.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create(String.format("/mentoria/%d", savedMentoria.get().getId()));
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateMentoria(@PathVariable Long id, @Validated @RequestBody MentoriaDTO dto){
        return mentoriaService.atualizarMentoria(id, dto) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
