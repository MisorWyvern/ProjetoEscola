package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.AvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> getAvaliacoes(){
        return new ResponseEntity<List<AvaliacaoDTO>>(avaliacaoService.getAvaliacoes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> getAvaliacaoByIndex(@PathVariable Long id){
        Optional<AvaliacaoDTO> avaliacaoDTO = avaliacaoService.getAvaliacaoByIndex(id);
        return avaliacaoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createAvaliacao(@Validated @RequestBody AvaliacaoDTO dto){
        Optional<AvaliacaoDTO> savedAvaliacao = avaliacaoService.criarAvaliacao(dto);

        if(savedAvaliacao.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create(String.format("/avaliacao/%d", savedAvaliacao.get().getId()));
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateAvaliacao(@PathVariable Long id, @Validated @RequestBody AvaliacaoDTO dto){
        return avaliacaoService.atualizarAvaliacao(id, dto) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAvaliacao(@PathVariable Long id){
        return avaliacaoService.deletarAvaliacao(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
