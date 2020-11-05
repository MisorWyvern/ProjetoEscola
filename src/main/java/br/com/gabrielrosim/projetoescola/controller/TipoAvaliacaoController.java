package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.TipoAvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.model.TipoAvaliacao;
import br.com.gabrielrosim.projetoescola.service.TipoAvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoavaliacao")
public class TipoAvaliacaoController {

    @Autowired
    private TipoAvaliacaoService tipoAvaliacaoService;

    @GetMapping
    public ResponseEntity<List<TipoAvaliacaoDTO>> getTipoAvaliacao(){
        return new ResponseEntity<List<TipoAvaliacaoDTO>>(tipoAvaliacaoService.getTiposAvaliacoes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAvaliacaoDTO> getTipoAvaliacaoByIndex(@PathVariable Long id){
        Optional<TipoAvaliacaoDTO> tipoAvaliacaoDTO = tipoAvaliacaoService.getTipoAvaliacaoById(id);
        return tipoAvaliacaoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> createTipoAvaliacao(@RequestBody @Validated TipoAvaliacaoDTO dto){
        TipoAvaliacaoDTO savedTipoAvaliacao = tipoAvaliacaoService.criarTipoAvaliacao(dto);
        URI location = URI.create(String.format("/tipoavaliacao/%d", savedTipoAvaliacao.getId()));
        return ResponseEntity.created(location).build();
    }
}
