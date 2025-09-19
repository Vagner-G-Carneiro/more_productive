package br.com.moreproductive.entities;

import br.com.moreproductive.dto.TarefaDTO;
import br.com.moreproductive.enums.PrioridadeTarefaEnum;
import br.com.moreproductive.enums.PrioridadeTarefaEnumConverter;
import br.com.moreproductive.enums.StatusTarefaEnum;
import br.com.moreproductive.enums.StatusTarefaEnumConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private String descricao;
    @Convert(converter = StatusTarefaEnumConverter.class)
    private StatusTarefaEnum status;
    @Convert(converter = PrioridadeTarefaEnumConverter.class)
    private PrioridadeTarefaEnum prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    private LocalDateTime dataLimite;
    private int usuarioId;

    public Tarefa(){}

    public Tarefa(TarefaDTO tarefaDTO)
    {
        this.titulo = tarefaDTO.getTitulo();
        this.descricao = tarefaDTO.getDescricao();
        this.status = tarefaDTO.getStatus();
        this.prioridade = tarefaDTO.getPrioridade();
        this.dataCriacao = tarefaDTO.getDataCriacao();
        this.dataConclusao = tarefaDTO.getDataConclusao();
        this.dataLimite = tarefaDTO.getDataLimite();
        this.usuarioId = tarefaDTO.getUsuarioId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusTarefaEnum getStatus() {
        return status;
    }

    public void setStatus(StatusTarefaEnum status) {
        this.status = status;
    }

    public PrioridadeTarefaEnum getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeTarefaEnum prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public LocalDateTime getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDateTime dataLimite) {
        this.dataLimite = dataLimite;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
