package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import model.Pessoa;
import utils.ConexaoSocket;
import utils.ConversorClasseJSON;

/**
 *
 * @author Matheus Henrique Maas
 */
public class ControllerPessoa {
    private String msg = "";
    private Pessoa pessoa;
    private ConversorClasseJSON conversorCJ;
    Scanner entrada;
    
     public void inserirPessoa() {
        String operacao = "INSERT";
        pessoa = new Pessoa();
        conversorCJ = new ConversorClasseJSON();
        
        entrada = new Scanner(System.in); 
        
        System.out.println("\nInsira o nome da pessoa: ");
        String nomePessoa = entrada.nextLine();
        pessoa.setNome(nomePessoa);

        System.out.println("Informe o CPF da pessoa: ");
        String cpf = entrada.nextLine();
        pessoa.setCpf(cpf);

        System.out.println("Informe o endereço da pessoa: ");
        String enderecoPessoa = entrada.nextLine();
        pessoa.setEndereco(enderecoPessoa);
        
        msg = conversorCJ.PessoaParaJson(pessoa, operacao);
         System.out.println(msg);
        // try {
          //   ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
          //   conexaoSocket.setMensagem(msg);
          //   String retorno = conexaoSocket.call();
           //  System.out.println(retorno);
             
        // } catch (IOException ex) {
         //    Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE,null, ex);
        // }
    }
}
