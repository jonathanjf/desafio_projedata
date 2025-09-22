package com.fernandesjf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Month;
import java.time.Period;
import java.util.Comparator;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

  private static final BigDecimal SALARIO_MINIMO = BigDecimal.valueOf(1212.00);

  public static void main(String[] args) {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Main desafio = new Main();

    System.out.println("\nResultados utilizando dados iniciais \n");

    desafio.inserirFuncionarios(desafio.funcionarios);
    desafio.relatorioFuncionarios();
    desafio.imprimirFuncionariosOrdemAlfabetica(desafio.funcionarios);
    desafio.imprimirPoderDeCompraEmSalariosMinimos(desafio.funcionarios);
    desafio.imprimirTotalSalarioFuncionarios(desafio.funcionarios);
    desafio.imprimirFuncionariosDeOutubroEdezembro(desafio.funcionarios);
    desafio.imprimeFuncionariosAgrupados(desafio.funcionarios);
    desafio.imprimirFuncionarioMaisVelho(desafio.funcionarios);

    System.out.println("\nResultados após aumento salarial \n");
    desafio.darAumento(desafio.funcionarios);
    desafio.relatorioFuncionarios();
    desafio.imprimirTotalSalarioFuncionarios(desafio.funcionarios);
    desafio.imprimirPoderDeCompraEmSalariosMinimos(desafio.funcionarios);

    System.out.println("\nResultados após remoção do funcionário João \n");
    desafio.removeFuncionario(desafio.funcionarios);
    desafio.relatorioFuncionarios();
  }

  List<Funcionario> funcionarios = new ArrayList<>();

  public void inserirFuncionarios(List<Funcionario> funcionarios) {
    // Insere os funcionários conforme a tabela
    funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
    funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
    funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
    funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
    funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
    funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
    funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
    funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
    funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
    funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

  }

  public void removeFuncionario(List<Funcionario> funcionarios) {
    String nomeFuncionario = "João";

    Iterator<Funcionario> iterator = funcionarios.iterator();

    while (iterator.hasNext()) {
      Funcionario f = iterator.next();

      if (f.getNome().equals(nomeFuncionario)) {

        iterator.remove();
        System.out.println("Funcionário '" + nomeFuncionario + "' removido com sucesso.\n");

        return;
      }
    }
    System.out.println("Funcionário '" + nomeFuncionario + "' não encontrado na lista.");
  }

  public void relatorioFuncionarios() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    System.out.println("\n--- Dados dos funcionários --- \n");
    for (Funcionario funcionario : funcionarios) {
      System.out.println("Nome: " + funcionario.getNome() +
          " - Data de nascimento: " + funcionario.getDataNascimento().format(formatter) +
          " - Salário: " + FormatarValor(funcionario.getSalario()) +
          " - Função: " + funcionario.getFuncao()
      );
    }
    System.out.println("\n--- Fim do relatório de funcionários --- \n");
  }

  public String FormatarValor(BigDecimal valor) {

    DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.of("pt", "BR"));
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');

    DecimalFormat df = new DecimalFormat("#,##0.00", simbolos);

    return df.format(valor);
  }

  public void darAumento(List<Funcionario> funcionarios) {

    // Usa a stream para iterar e dar o aumento
    funcionarios.forEach(f -> {

      // Calcula o novo salário com 10% de aumento
      BigDecimal aumento = f.getSalario().multiply(new BigDecimal("1.10"));

      // Atualiza o salário do funcionário com o novo valor
      f.setSalario(aumento.setScale(2, RoundingMode.HALF_UP));
    });

    System.out.println("\n Salário de todos os funcionários alterado para +10%! \n");
  }

  public Map<String, List<Funcionario>> agrupaFuncionariosPorFuncao(List<Funcionario> funcionarios) {

    return funcionarios.stream()
        .collect(Collectors.groupingBy(Funcionario::getFuncao));
  }

  public void imprimeFuncionariosAgrupados(List<Funcionario> funcionarios) {
    
    Map<String, List<Funcionario>> funcionariosAgrupados = agrupaFuncionariosPorFuncao(funcionarios);

    // Imprime agrupados por função
    funcionariosAgrupados.forEach((funcao, lista) -> {
      System.out.println("\n Função: " + funcao + "\n");
      lista.forEach(f -> System.out.println(" - " + f.getNome()));
    });
  }

  public void imprimirFuncionariosDeOutubroEdezembro(List<Funcionario> funcionarios) {

    System.out.println("\n--- Aniversariantes de Outubro e Dezembro --- \n");

    funcionarios.stream()
        .filter(f -> f.getDataNascimento().getMonth() == Month.OCTOBER ||
            f.getDataNascimento().getMonth() == Month.DECEMBER)
        .forEach(f -> System.out.println("Nome: " + f.getNome() + ", Aniversário: " + f.getDataNascimento()));
  }

  public void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
    System.out.println("\n--- Funcionário com a Maior Idade --- \n");

    funcionarios.stream()
        .max(Comparator.comparing(Funcionario::getDataNascimento))
        .ifPresent(oldestEmployee -> {
          int idade = Period.between(oldestEmployee.getDataNascimento(), LocalDate.now()).getYears();
          System.out.println("Nome: " + oldestEmployee.getNome() + " Idade: " + idade + " anos");
        });
  }

  public void imprimirFuncionariosOrdemAlfabetica(List<Funcionario> funcionarios) {

    System.out.println("\n--- Lista de Funcionários em Ordem Alfabética ---\n");

    funcionarios.stream()
        .sorted(Comparator.comparing(Funcionario::getNome))
        .forEach(f -> System.out.println("Nome: " + f.getNome()));
  }

  public void imprimirTotalSalarioFuncionarios(List<Funcionario> funcionarios) {
    BigDecimal totalSalaries = funcionarios.stream()
        .map(Funcionario::getSalario)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    System.out.println("\n Salário total: " + totalSalaries);
  }

  public void imprimirPoderDeCompraEmSalariosMinimos(List<Funcionario> funcionarios) {
    funcionarios.forEach(f -> {
      // Calcula a quantidade de salários mínimos
      BigDecimal quantidadeSalariosMinimos = f.getSalario()
          .divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);

      System.out.println(f.getNome() + ": " + quantidadeSalariosMinimos + " salários mínimos.");
    });
  }
}