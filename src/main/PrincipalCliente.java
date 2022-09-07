package main;

import java.text.ParseException;
import view.MenuPrincipal;

/**
 *
 * @author Lucas de Liz Frutuoso e Matheus Henrique Maas
 */
public class PrincipalCliente {

    public static void main(String[] args) throws ParseException, org.json.simple.parser.ParseException {

        MenuPrincipal menuPrincipal = new MenuPrincipal();

        menuPrincipal.iniciarMenu();
    }

}
