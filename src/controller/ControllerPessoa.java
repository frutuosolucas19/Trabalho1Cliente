package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import model.Pessoa;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.ConexaoSocket;
import utils.Conversor;

/**
 *
 * @author Lucas de Liz Frutuoso e Matheus Henrique Maas
 */
public class ControllerPessoa {

    private String msg = "";
    private Pessoa pessoa;
    private Conversor conversorCJ;
    private List<Pessoa> pessoas1 = new ArrayList<>();
    private Scanner entrada = new Scanner(System.in);

    public void inserePessoa() {

        String operacao = "INSERT";
        pessoa = new Pessoa();
        conversorCJ = new Conversor();

        //entrada = new Scanner(System.in);
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

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();

        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listaPessoas() throws java.text.ParseException, ParseException {

        JSONObject pessoasJson = new JSONObject();
        pessoasJson.put("operacao", "LIST");
        pessoasJson.put("classe", "pessoa");
        msg = pessoasJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String resposta = conexaoSocket.call();

            if(!"0".equals(resposta)){
            String menuPessoas = getPessoa(resposta);
            System.out.println(menuPessoas);
            }
            else{
                System.out.println(resposta);
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscaPessoa() throws java.text.ParseException, ParseException {
        
        String resposta = null;
        //entrada = new Scanner(System.in);

        System.out.println("Informe o CPF da pessoa: ");
        String cpfPessoa = entrada.nextLine();

        JSONObject pessoaJson = new JSONObject();
        pessoaJson.put("operacao", "GET");
        pessoaJson.put("classe", "pessoa");
        pessoaJson.put("cpf", cpfPessoa);
        msg = pessoaJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            resposta = conexaoSocket.call();
             
            if(!"Pessoa não encontrada".equals(resposta)&&!"Sem pessoas cadastradas".equals(resposta)){
             Conversor c1 = new Conversor();
             Pessoa p2 = c1.JsonParaPessoa(resposta);
             
             System.out.println(p2.getCpf()+";"+p2.getNome()+";"+p2.getEndereco());
            }else{
                System.out.println(resposta);
            }
                
        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletaPessoa() {
        //entrada = new Scanner(System.in);

        System.out.println("Informe o CPF da pessoa: ");
        String cpfPessoa = entrada.next();

        JSONObject pessoaJson = new JSONObject();
        pessoaJson.put("operacao", "DELETE");
        pessoaJson.put("classe", "pessoa");
        pessoaJson.put("cpf", cpfPessoa);
        msg = pessoaJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String resposta = conexaoSocket.call();

            System.out.println(resposta);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaPessoa() {

        String operacao = "UPDATE";
        pessoa = new Pessoa();
        conversorCJ = new Conversor();

        //entrada = new Scanner(System.in);
        System.out.println("Informe o CPF da pessoa: ");
        String cpf = entrada.nextLine();
        pessoa.setCpf(cpf);

        System.out.println("\nInsira o nome da pessoa: ");
        String nomePessoa = entrada.nextLine();
        pessoa.setNome(nomePessoa);

        System.out.println("Informe o endereço da pessoa: ");
        String enderecoPessoa = entrada.nextLine();
        pessoa.setEndereco(enderecoPessoa);

        msg = conversorCJ.PessoaParaJson(pessoa, operacao);
        
        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();
            
            System.out.println(retorno);

        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String listaPessoasMensagem() {
        
        JSONObject pessoaJSON = new JSONObject();  
        pessoaJSON.put("operacao", "LIST");
        pessoaJSON.put("entidade", "pessoa");
        msg = pessoaJSON.toJSONString();
    
        return msg;  
    }
    
     public String getPessoa(String mensagem) throws java.text.ParseException, ParseException {

        String menuPessoas;

        Conversor conversorJSONListPessoa = new Conversor();
        List<Pessoa> listaPessoas = conversorJSONListPessoa.JsonParaPessoaList(mensagem);

        menuPessoas = "----Pessoas Existentes----\n";
        menuPessoas += "Quantidades de pessoas: "+listaPessoas.size()+"\n";
        menuPessoas += "------------------------------------\n";

        for (int i = 0; i < listaPessoas.size(); i++) {
            Pessoa pessoa = listaPessoas.get(i);

            menuPessoas += "CPF: " + pessoa.getCpf() + "\n";
            menuPessoas += "Nome: " + pessoa.getNome() + "\n";
            menuPessoas += "Endereço: " + pessoa.getEndereco() + "\n";
            menuPessoas += "------------------------------------\n";
        }

        return menuPessoas;
    }
     
    
     
}
