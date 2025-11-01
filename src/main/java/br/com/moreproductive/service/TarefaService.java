package br.com.moreproductive.service;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.entities.Usuario;
import br.com.moreproductive.enums.StatusTarefaEnum;
import br.com.moreproductive.exceptions.InformacaoNaoEncontradaException;
import br.com.moreproductive.exceptions.PermissaoNegada;
import br.com.moreproductive.exceptions.UsuarioException;
import br.com.moreproductive.repository.TarefaRepository;
import br.com.moreproductive.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    private Page<TarefaDTO> validarECoverterTarefasDtoPage(Page<Tarefa> tarefas)
    {
        if(tarefas.isEmpty())
        {
            throw new InformacaoNaoEncontradaException("Nenhuma tarefa encontrada para este critério.");
        }
        return tarefas.map(tarefa -> new TarefaDTO(tarefa));
    }

    private boolean validarPermissao(Usuario usuario, Tarefa tarefa) {
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

    public Page<TarefaDTO> buscarTarefas(String usuarioLogadoEmail, Pageable pageable)
    {
        Usuario usuario = this.recuperarUsuarioLogado(usuarioLogadoEmail);
        Page<Tarefa> pageTarefasPage = this.tarefaRepository.findByUsuarioId(usuario.getId(), pageable);
        return this.validarECoverterTarefasDtoPage(pageTarefasPage);
    }

    public TarefaDTO atualizarTarefa(String usuarioLogadoEmail, TarefaDTO tarefaAtualizadaDTO)
    {
        Usuario usuario = recuperarUsuarioLogado(usuarioLogadoEmail);
        Tarefa tarefa = this.tarefaRepository.findById(tarefaAtualizadaDTO.getTarefaId())
                .orElseThrow(() -> new InformacaoNaoEncontradaException("Erro ao encontrar tarefa para atualização"));
        if(this.validarPermissao(usuario, tarefa))
        {
            tarefa.setStatus(tarefaAtualizadaDTO.getStatus());
            tarefa.setDescricao(tarefaAtualizadaDTO.getDescricao());
            tarefa.setPrioridade(tarefaAtualizadaDTO.getPrioridade());
            tarefa.setTitulo(tarefaAtualizadaDTO.getTitulo());
            tarefa.setDataLimite(tarefaAtualizadaDTO.getDataLimite());

            return new TarefaDTO(tarefaRepository.save(tarefa));
        }
        throw new UsuarioException("Erro ao atualizar tarefa, permissão não concedida!");
    }

    public TarefaDTO concluirTarefa(String usuarioLogadoEmail, int tarefaId)
    {
        Usuario usuarioLogado = recuperarUsuarioLogado(usuarioLogadoEmail);
        Tarefa tarefa = this.tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new InformacaoNaoEncontradaException("Tarefa não encontrada."));
        if(this.validarPermissao(usuarioLogado, tarefa))
        {
            tarefa.setDataConclusao(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            tarefa.setStatus(StatusTarefaEnum.CONCLUIDA);
            this.tarefaRepository.save(tarefa);
            return new TarefaDTO(tarefa);
        }
        throw new PermissaoNegada();
    }

    public void excluirTarefa(String usuarioLogadoEmail, int tarefaId)
    {
        Usuario usuarioLogado = recuperarUsuarioLogado(usuarioLogadoEmail);
        Tarefa tarefa = this.tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new InformacaoNaoEncontradaException("Erro ao encontrar tarefa para atualização"));
        if(this.validarPermissao(usuarioLogado, tarefa))
        {
            this.tarefaRepository.deleteById(tarefa.getId());
        }
        throw new UsuarioException("Erro ao atualizar tarefa, permissão não concedida!");
    }
}
