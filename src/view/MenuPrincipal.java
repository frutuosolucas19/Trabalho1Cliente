
package view;

import controller.ControllerPessoa;
import java.util.Scanner;
import utils.ConexaoSocket;

/**
 *
 * @author Matheus Henrique Maas
 */
public class MenuPrincipal {
    public static Scanner entrada;
    private static ControllerPessoa controllerPessoa;
    
    public void iniciarMenu(){
        entrada = new Scanner(System.in);
        
        System.out.println("Para o cliente precisamos do endereço e da porta, por favor informe abaixo.\n");
        System.out.println("Endereço: ");
        String endereco = entrada.nextLine();
        System.out.println("\nPorta: ");
        int porta = entrada.nextInt();
        
        ConexaoSocket.getInstance().init(endereco, porta);
        
        opcoesDoMenu();
    }
     
    
    public void opcoesDoMenu(){
        
        
        int opcao;
        
        do{
            controllerPessoa = new ControllerPessoa();
            
            System.out.println("\nMenu de Opções Cliente\n");
            System.out.print("1.) Inserir \n");
            System.out.print("2.) Listar\n");
            //System.out.print("3.) Buscar.\n");
           // System.out.print("4.) Deletar.\n");
           // System.out.print("5.) Atualizar\n");
            System.out.print("6.) Sair\n");
            System.out.print("\nDigite a opção desejada: ");
            opcao = entrada.nextInt();




        switch(opcao){

        case 1:
            controllerPessoa.inserirPessoa();
            break;

        case 2: 
            //do something
            break;

        case 3:
            //do something
            break;

        case 4: 
            //do something
            break;

        case 5:
            //do something
            break;

        case 6: 
            System.out.println("Finalizando sistema...");
            System.exit(0);
             break;
        default:
        System.out.println(opcao + " não é um valor válido do menu, por favor digite um valor válido.");

    }}while(opcao != 6 /*Exit loop when choice is 6*/);
     
    }
}


