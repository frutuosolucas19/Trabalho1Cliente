package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas de Liz Frutuoso e Matheus Henrique Maas
 */
public class Funcao {

    private String nome;
    private String setor;
    private double salario;
    private List<Pessoa> pessoas = new ArrayList<>();

    public Funcao() {

    }

    public Funcao(String nome, String setor) {
        this.nome = nome;
        this.setor = setor;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    @Override
    public String toString() {
        return "Funcao{" + "nome=" + nome + ", setor=" + setor + ", salario=" + salario + ", pessoas=" + pessoas + '}';
    }
    
    

}
