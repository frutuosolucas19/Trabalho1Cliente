package model;

/**
 *
 * @author Lucas de Liz Frutuoso e Matheus Henrique Maas
 */
public class Funcao {

    private String nome;
    private String setor;
    private double salario;

    public Funcao() {

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

    @Override
    public String toString() {
        return "Funcao{" + "nome=" + nome + ", setor=" + setor + ", salario=" + salario + '}';
    }

}
