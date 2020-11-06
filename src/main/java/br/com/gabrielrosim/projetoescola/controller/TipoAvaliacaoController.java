package br.com.gabrielrosim.projetoescola.controller;

import br.com.gabrielrosim.projetoescola.dto.TipoAvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.exception.CodigoAlreadyExistsException;
import br.com.gabrielrosim.projetoescola.model.TipoAvaliacao;
import br.com.gabrielrosim.projetoescola.service.TipoAvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoavaliacao")
public class TipoAvaliacaoController {

    @Autowired
    private TipoAvaliacaoService tipoAvaliacaoService;

    @GetMapping
    public ResponseEntity<List<TipoAvaliacaoDTO>> getTipoAvaliacao() {
        return new ResponseEntity<List<TipoAvaliacaoDTO>>(tipoAvaliacaoService.getTiposAvaliacoes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAvaliacaoDTO> getTipoAvaliacaoByIndex(@PathVariable Long id) {
        Optional<TipoAvaliacaoDTO> tipoAvaliacaoDTO = tipoAvaliacaoService.getTipoAvaliacaoById(id);
        return tipoAvaliacaoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createTipoAvaliacao(@RequestBody @Validated TipoAvaliacaoDTO dto) {

        Optional<TipoAvaliacaoDTO> savedTipoAvaliacao = tipoAvaliacaoService.criarTipoAvaliacao(dto);

        if (savedTipoAvaliacao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        URI location = URI.create(String.format("/tipoavaliacao/%d", savedTipoAvaliacao.get().getId()));
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTipoAvaliacao(@PathVariable Long id, @Validated @RequestBody TipoAvaliacaoDTO dto) {
        return tipoAvaliacaoService.atualizarTipoDisciplina(id, dto) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTipoAvaliacao(@PathVariable Long id) {
        return tipoAvaliacaoService.deletarTipoDisciplina(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
