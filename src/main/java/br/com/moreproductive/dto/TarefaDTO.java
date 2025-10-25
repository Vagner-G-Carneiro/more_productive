package br.com.moreproductive.dto;

import br.com.moreproductive.entities.Tarefa;
import br.com.moreproductive.entities.Usuario;
import br.com.moreproductive.enums.PrioridadeTarefaEnum;
import br.com.moreproductive.enums.PrioridadeTarefaEnumDeserializer;
import br.com.moreproductive.enums.StatusTarefaEnum;
import br.com.moreproductive.enums.StatusTarefaEnumDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TarefaDTO {

    @NotBlank(message = "Titulo é um campo obrigatório.")
    private String titulo;
    private String descricao;
    @JsonDeserialize(using = StatusTarefaEnumDeserializer.class)
    private StatusTarefaEnum status;
    @JsonDeserialize(using = PrioridadeTarefaEnumDeserializer.class)
    private PrioridadeTarefaEnum prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    @NotNull(message = "Data limite é obrigatória, isso ajuda muito com a produtividade!")
    private LocalDateTime dataLimite;
    private Usuario usuario;
    public TarefaDTO(){

    }

    public TarefaDTO(String titulo, String descricao, StatusTarefaEnum status, PrioridadeTarefaEnum prioridade,
                     LocalDateTime dataCriacao, LocalDateTime dataConclusao, LocalDateTime dataLimite, Usuario usuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.dataLimite = dataLimite;
        this.usuario = usuario;
    }

    public TarefaDTO(Tarefa tarefa)
    {
        this.titulo = tarefa.getTitulo();
        this.descricao = tarefa.getDescricao();
        this.status = tarefa.getStatus();
        this.prioridade = tarefa.getPrioridade();
        this.dataCriacao = tarefa.getDataCriacao();
        this.dataConclusao = tarefa.getDataConclusao();
        this.dataLimite = tarefa.getDataLimite();
        this.usuario = tarefa.getUsuario();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
