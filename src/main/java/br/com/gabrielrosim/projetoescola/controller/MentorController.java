package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<MentorDTO>> getMentores(){
        return new ResponseEntity<List<MentorDTO>>(mentorService.getMentores(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable Long id){
        Optional<MentorDTO> mentorDTO = mentorService.getMentorByIndex(id);
        return mentorDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> criarMentor(@Valid @RequestBody MentorDTO dto){
        MentorDTO savedMentor = mentorService.criarMentor(dto);
        URI location = URI.create(String.format("/mentor/%d",savedMentor.getId()));
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarMentor(@PathVariable Long id, @Valid @RequestBody MentorDTO dto){
        mentorService.atualizarMentor(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletarMentor(@PathVariable Long id){
        mentorService.deletarMentor(id);
    }
}
