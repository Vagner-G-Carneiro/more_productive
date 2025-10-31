package br.com.moreproductive.service;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.entities.Usuario;
import br.com.moreproductive.enums.PrioridadeTarefaEnum;
import br.com.moreproductive.enums.StatusTarefaEnum;
import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
import br.com.moreproductive.exceptions.UsuarioException;
import br.com.moreproductive.repository.TarefaRepository;
import br.com.moreproductive.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaService(TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository)
    {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario recuperarUsuarioLogado(String usuarioLogadoEmail)
    {
        return this.usuarioRepository.findUsuarioByEmail(usuarioLogadoEmail)
                .orElseThrow(() -> new UsuarioException("Erro ao recuperar usuário logado"));
    }

    private List<TarefaDTO> validarECoverterTarefas(List<Tarefa> tarefas)
    {
        if(tarefas.isEmpty())
        {
            throw new InformacaoNaoEncontradaException("Nenhuma tarefa encontrada para este critério.");
        }
        return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).toList();
    }

    private boolean validaPermissao(Usuario usuario, Tarefa tarefa) {
        return usuario.getEmail().equals(tarefa.getUsuario().getEmail());
    }

    public TarefaDTO salvar(TarefaDTO novaTarefa, String usuarioLogadoEmail) {
            Usuario usuarioLogado = this.recuperarUsuarioLogado(usuarioLogadoEmail);
            Tarefa tarefa = new Tarefa(novaTarefa);
            tarefa.setUsuario(usuarioLogado);
            tarefa.setDataCriacao(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            this.tarefaRepository.save(tarefa);
            return new TarefaDTO(tarefa);
    }

    public List<TarefaDTO> buscarTodas(String usuarioLogadoEmail)
    {
        Usuario usuario = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByStatusAndPrioridadeAndDataLimite(usuario.getId());
        return this.validarECoverterTarefas(tarefas);
    }

    public List<TarefaDTO> buscarOrdenadasDataLimite(String usuarioLogadoEmail)
    {
        Usuario usuario = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByDataLimite(usuario.getId());
        return this.validarECoverterTarefas(tarefas);
    }

    public List<TarefaDTO> buscarOrdenadasDataCriacao(String usuarioLogadoEmail)
    {
        Usuario usuario = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuario.getId());
        return this.validarECoverterTarefas(tarefas);
    }

    public List<TarefaDTO> buscarOrdenadasPrioridade(String usuarioLogadoEmail)
    {
        Usuario usuario = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByPrioridadeDesc(usuario.getId());
        return this.validarECoverterTarefas(tarefas);
    }

    public List<TarefaDTO> buscarOrdenadasStatus(String usuarioLogadoEmail)
    {
        Usuario usuario = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdOrderByStatus(usuario.getId());
        return this.validarECoverterTarefas(tarefas);
    }

    public List<TarefaDTO> filtrarPorStatus(String usuarioLogadoEmail, StatusTarefaEnum status)
    {
        Usuario usuario = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdAndStatus(usuario.getId(), status);
        return this.validarECoverterTarefas(tarefas);
    }

    public List<TarefaDTO> filtrarPorPrioridade(String usuarioLogadoEmail, PrioridadeTarefaEnum prioridade)
    {
        Usuario usuario = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        List<Tarefa> tarefas = this.tarefaRepository.findByUsuarioIdAndPrioridade(usuario.getId(), prioridade);
        return this.validarECoverterTarefas(tarefas);
    }

    public TarefaDTO atualizarTarefa(String usuarioLogadoEmail, TarefaDTO tarefaAtualizadaDTO)
    {
        Usuario usuario = recuperarUsuarioLogado(usuarioLogadoEmail);
        Tarefa tarefa = this.tarefaRepository.findById(tarefaAtualizadaDTO.getTarefaId())
                .orElseThrow(() -> new InformacaoNaoEncontradaException("Erro ao encontrar tarefa para atualização"));
        if(this.validaPermissao(usuario, tarefa))
        {
            tarefa.setStatus(tarefaAtualizadaDTO.getStatus());
            tarefa.setDataConclusao(tarefaAtualizadaDTO.getDataConclusao());
            tarefa.setDescricao(tarefaAtualizadaDTO.getDescricao());
            tarefa.setPrioridade(tarefaAtualizadaDTO.getPrioridade());
            tarefa.setTitulo(tarefaAtualizadaDTO.getTitulo());
            tarefa.setDataLimite(tarefaAtualizadaDTO.getDataLimite());

            return new TarefaDTO(tarefaRepository.save(tarefa));
        }
        throw new UsuarioException("Erro ao atualizar tarefa, permissão não concedida!");
    }

    public void excluirTarefa(String usuarioLogadoEmail, int tarefaId)
    {
        Usuario usuario = recuperarUsuarioLogado(usuarioLogadoEmail);
        Tarefa tarefa = this.tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new InformacaoNaoEncontradaException("Erro ao encontrar tarefa para atualização"));
        if(this.validaPermissao(usuario, tarefa))
        {
            this.tarefaRepository.deleteById(tarefa.getId());
        }
        throw new UsuarioException("Erro ao atualizar tarefa, permissão não concedida!");
    }
}
