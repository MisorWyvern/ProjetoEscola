package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.AvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Avaliacao;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import br.com.gabrielrosim.projetoescola.model.TipoAvaliacao;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import br.com.gabrielrosim.projetoescola.repository.AvaliacaoRepository;
import br.com.gabrielrosim.projetoescola.repository.DisciplinaRepository;
import br.com.gabrielrosim.projetoescola.repository.TipoAvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public class AvaliacaoMapperDecorator implements AvaliacaoMapper {

    @Autowired
    @Qualifier("delegate")
    private AvaliacaoMapper delegate;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TipoAvaliacaoRepository tipoAvaliacaoRepository;

    @Override
    public Avaliacao toAvaliacao(AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = delegate.toAvaliacao(avaliacaoDTO);

        if (avaliacao.getId() != null) {
            if (avaliacaoDTO.getIdDisciplina() != null) {
                Optional<Disciplina> disciplina = disciplinaRepository.findById(avaliacaoDTO.getIdDisciplina());
                disciplina.ifPresent(avaliacao::setDisciplina);
            }
            if (avaliacaoDTO.getIdAluno() != null) {
                Optional<Aluno> aluno = alunoRepository.findById(avaliacaoDTO.getIdAluno());
                aluno.ifPresent(avaliacao::setAluno);
            }
            if (avaliacaoDTO.getIdTipoAvaliacao() != null) {
                Optional<TipoAvaliacao> tipoAvaliacao = tipoAvaliacaoRepository.findById(avaliacaoDTO.getIdTipoAvaliacao());
                tipoAvaliacao.ifPresent(avaliacao::setTipoAvaliacao);
            }
        }

        return avaliacao;
    }

    @Override
    public AvaliacaoDTO toAvaliacaoDTO(Avaliacao avaliacao) {
        AvaliacaoDTO dto = delegate.toAvaliacaoDTO(avaliacao);

        if (dto.getId() != null) {
            if (avaliacao.getDisciplina() != null) {
                if (avaliacao.getDisciplina().getId() != null) {
                    dto.setIdDisciplina(avaliacao.getDisciplina().getId());
                }
            }
            if (avaliacao.getAluno() != null) {
                if (avaliacao.getAluno().getId() != null) {
                    dto.setIdAluno(avaliacao.getAluno().getId());
                }
            }
            if (avaliacao.getTipoAvaliacao() != null) {
                if (avaliacao.getTipoAvaliacao().getId() != null) {
                    dto.setIdTipoAvaliacao(avaliacao.getTipoAvaliacao().getId());
                }
            }
        }

        return dto;
    }
}
