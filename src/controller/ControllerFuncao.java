package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import model.Funcao;
import org.json.simple.JSONObject;
import utils.ConexaoSocket;
import utils.ConversorClasseJSON;

/**
 *
 * @author Lucas
 */
public class ControllerFuncao {

    private String msg = "";
    private Funcao funcao;
    private ConversorClasseJSON conversorCJ;
    Scanner entrada;

    public void insereFuncao() {
        String operacao = "INSERT";
        funcao = new Funcao();
        conversorCJ = new ConversorClasseJSON();

        entrada = new Scanner(System.in);

        System.out.println("\nInsira o nome da Função: ");
        String nomefuncao = entrada.nextLine();
        funcao.setNome(nomefuncao);

        System.out.println("Informe o Setor da Função: ");
        String setorFuncao = entrada.nextLine();
        funcao.setSetor(setorFuncao);

        System.out.println("Informe o Salário da Função: ");
        double salarioFuncao = entrada.nextDouble();
        funcao.setSalario(salarioFuncao);

        msg = conversorCJ.FuncaoParaJson(funcao, operacao);
        System.out.println(msg);
        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();
            System.out.println(retorno);

        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listaFuncoes() {
        JSONObject funcaoJson = new JSONObject();
        funcaoJson.put("operacao", "2");
        funcaoJson.put("classe", "funcao");
        msg = funcaoJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String resposta = conexaoSocket.call();

            System.out.println(resposta);
        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
