package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.AvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.model.Avaliacao;
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

    @PostMapping
    public ResponseEntity<String> createAvaliacao(@Validated @RequestBody AvaliacaoDTO dto){
        Optional<AvaliacaoDTO> savedAvaliacao = avaliacaoService.criarAvaliacao(dto);

        if(savedAvaliacao.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create(String.format("/avaliacao/%d", savedAvaliacao.get().getId()));
        return ResponseEntity.created(location).build();
    }

}
