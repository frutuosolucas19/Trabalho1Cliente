package view;

import controller.ControllerFuncao;
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
    private static ControllerFuncao controllerFuncao;
    int opcao;
    int classe = 0;

    public void iniciarMenu() {
        entrada = new Scanner(System.in);

        System.out.println("Para o cliente precisamos do endereço IP e da porta, por favor informe abaixo.\n");
        System.out.println("Endereço IP: ");
        String endereco = entrada.nextLine();
        System.out.println("\nPorta: ");
        int porta = entrada.nextInt();

        ConexaoSocket.getInstance().init(endereco, porta);

        opcoesDoMenu();
    }

    public void opcoesDoMenu() {

        do {
            controllerPessoa = new ControllerPessoa();
            controllerFuncao = new ControllerFuncao();

            System.out.println("\nMenu de Opções Cliente\n");
            System.out.print("1.) Inserir \n");
            System.out.print("2.) Listar\n");
            System.out.print("3.) Buscar.\n");
            System.out.print("4.) Deletar.\n");
            System.out.print("5.) Atualizar\n");
            System.out.print("6.) Sair\n");
            System.out.print("\nDigite a opção desejada: ");
            opcao = entrada.nextInt();

            if (opcao != 6) {
                defineClasse();
            }

            switch (opcao) {

                case 1:
                    if (classe == 1) {
                        controllerPessoa.inserePessoa();
                    }
                    if (classe == 2) {
                        controllerFuncao.insereFuncao();
                    }
                    break;

                case 2:
                    if (classe == 1) {
                        controllerPessoa.listaPessoas();
                    }
                    if (classe == 2) {
                        controllerFuncao.listaFuncoes();
                    }
                    break;

                case 3:
                    if (classe == 1) {
                        controllerPessoa.buscaPessoa();
                    }
                    if (classe == 2) {
                        controllerFuncao.buscaFuncao();
                    }
                    break;

                case 4:
                    if (classe == 1) {
                        controllerPessoa.deletaPessoa();
                    }
                    if (classe == 2) {
                        controllerFuncao.deletaFuncao();
                    }
                    break;

                case 5:
                    if (classe == 1) {
                        controllerPessoa.atualizaPessoa();
                    }
                    if (classe == 2) {
                        controllerFuncao.atualizaFuncao();
                    }
                    break;

                case 6:
                    System.out.println("Finalizando sistema...");
                    System.exit(0);
                    break;
                default:
                    System.out.println(opcao + " não é um valor válido do menu, por favor digite um valor válido.");

            }
        } while (opcao != 6);

    }

    public void defineClasse() {

        do {

            System.out.println("\nDigite o número correspondente à Classe desejada: ");
            System.out.print("1.) Pessoa \n");
            System.out.print("2.) Funcao\n");
            classe = entrada.nextInt();

            if (classe != 1 && classe != 2) {
                System.out.println("'" + classe + "'" + " não é um valor válido para a classe, por favor digite um valor válido.");
            }

        } while (classe != 1 && classe != 2);
    }

}
