package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.AvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.AvaliacaoMapper;
import br.com.gabrielrosim.projetoescola.exception.AvaliacaoCurrentlyInUseException;
import br.com.gabrielrosim.projetoescola.model.Avaliacao;
import br.com.gabrielrosim.projetoescola.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    DisciplinaService disciplinaService;
    @Autowired
    AlunoService alunoService;
    @Autowired
    TipoAvaliacaoService tipoAvaliacaoService;

    @Autowired
    AvaliacaoMapper avaliacaoMapper;

    public List<AvaliacaoDTO> getAvaliacoes() {
        return avaliacaoRepository.findAll()
                .parallelStream()
                .map(avaliacaoMapper::toAvaliacaoDTO)
                .collect(Collectors.toList());
    }


    public Optional<AvaliacaoDTO> criarAvaliacao(AvaliacaoDTO dto) {

        if(disciplinaService.getDisciplinaByIndex(dto.getIdDisciplina()).isEmpty()){
            return Optional.empty();
        }

        if(tipoAvaliacaoService.getTipoAvaliacaoById(dto.getIdTipoAvaliacao()).isEmpty()){
            return Optional.empty();
        }

        if(alunoService.getAlunoByIndex(dto.getIdAluno()).isEmpty()){
            return Optional.empty();
        }

        Avaliacao avaliacao = avaliacaoMapper.toAvaliacao(dto);

        if(avaliacaoRepository.findByDisciplinaAndTipoAvaliacaoAndAluno(
                avaliacao.getDisciplina(), avaliacao.getTipoAvaliacao(), avaliacao.getAluno()).isPresent()){
            throw new AvaliacaoCurrentlyInUseException();
        }

        if(avaliacao.getNota() == null || avaliacao.getNota() < 0){
            avaliacao.setNota((double) 0);
        }

        if(avaliacao.getDataAplicacao() == null){
            avaliacao.setDataAplicacao(LocalDate.now());
        }

        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);
        return Optional.of(avaliacaoMapper.toAvaliacaoDTO(savedAvaliacao));

    }

    public Optional<AvaliacaoDTO> getAvaliacaoByIndex(Long id) {
        Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(id);
        return avaliacao.map(ava -> avaliacaoMapper.toAvaliacaoDTO(ava));
    }

    @Transactional
    public Boolean atualizarAvaliacao(Long id, AvaliacaoDTO dto) {
        Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(id);

        if(avaliacao.isEmpty()){
            return Boolean.FALSE;
        }

        Avaliacao dtoAvaliacao = avaliacaoMapper.toAvaliacao(dto);

        Optional<Avaliacao> avaliacaoDTAA = avaliacaoRepository.findByDisciplinaAndTipoAvaliacaoAndAluno(dtoAvaliacao.getDisciplina(), dtoAvaliacao.getTipoAvaliacao(), dtoAvaliacao.getAluno());
        if(avaliacaoDTAA.isPresent()){
            if(!avaliacaoDTAA.get().getId().equals(id)){
                throw new AvaliacaoCurrentlyInUseException();
            }
        }

        if(dtoAvaliacao.getDisciplina() != null){
            avaliacao.get().setDisciplina(dtoAvaliacao.getDisciplina());
        }

        if(dtoAvaliacao.getTipoAvaliacao() != null){
            avaliacao.get().setTipoAvaliacao(dtoAvaliacao.getTipoAvaliacao());
        }

        if(dtoAvaliacao.getAluno() != null){
            avaliacao.get().setAluno(dtoAvaliacao.getAluno());
        }

        if(dtoAvaliacao.getNota() != null){
            avaliacao.get().setNota(dtoAvaliacao.getNota());
        }

        if(dtoAvaliacao.getDataAplicacao() != null){
            avaliacao.get().setDataAplicacao(dtoAvaliacao.getDataAplicacao());
        }

        return Boolean.TRUE;
    }
}
