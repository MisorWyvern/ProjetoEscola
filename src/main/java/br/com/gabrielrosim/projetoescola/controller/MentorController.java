package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mentor")
public class MentorController {

    @Autowired
    MentorService mentorService;

    @GetMapping
    public ResponseEntity<Page<MentorDTO>> getMentores(@RequestParam Optional<Boolean> active,
                                                       @PageableDefault(sort = "nome", direction = Sort.Direction.ASC)
                                                       Pageable pageable){
        return new ResponseEntity<Page<MentorDTO>>(mentorService.getMentores(active, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable Long id){
        Optional<MentorDTO> mentorDTO = mentorService.getMentorByIndex(id);
        return mentorDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> criarMentor(@Valid @RequestBody MentorDTO dto){
        Optional<MentorDTO> savedMentor = mentorService.criarMentor(dto);

        if(savedMentor.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create(String.format("/mentor/%d",savedMentor.get().getId()));
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> atualizarMentor(@PathVariable Long id, @Valid @RequestBody MentorDTO dto){
        return mentorService.atualizarMentor(id, dto) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<Boolean> activateMentor(@PathVariable Long id){
        return mentorService.activateMentor(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletarMentor(@PathVariable Long id){
        return mentorService.deletarMentor(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
