package br.com.moreproductive.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tarefa {
    @Id
    int id;

    public Tarefa(int id) {
        this.id = id;
    }
}
