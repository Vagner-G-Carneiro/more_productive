package br.com.moreproductive.dto;

import br.com.moreproductive.enums.PrioridadeTarefaEnum;
import br.com.moreproductive.enums.StatusTarefaEnum;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class TarefaDTO {

    @NotBlank(message = "Titulo é um campo obrigatório.")
    private String titulo;
    private String descricao;
    private StatusTarefaEnum status;
    private PrioridadeTarefaEnum prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;

    @NotBlank(message = "Data limite é obrigatória, isso ajuda muito com a produtividade!")
    private LocalDateTime dataLimite;

    @NotBlank(message = "Toda tarefa deve ser vinculada a algum usuario.")
    private int usuarioId;

    public TarefaDTO(String titulo, String descricao, StatusTarefaEnum status, PrioridadeTarefaEnum prioridade,
                     LocalDateTime dataCriacao, LocalDateTime dataConclusao, LocalDateTime dataLimite, int usuarioId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.dataLimite = dataLimite;
        this.usuarioId = usuarioId;
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
