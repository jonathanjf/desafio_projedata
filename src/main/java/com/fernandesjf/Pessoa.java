package com.fernandesjf;

import java.time.LocalDate;

public class Pessoa {
  private String nome;
  private LocalDate dataNascimento;

  public Pessoa(String nome, LocalDate dataNascimento) {
    this.nome = nome;
    this.dataNascimento = dataNascimento;
  }

  // Getters
  public String getNome() {
    return nome;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }
}
