package br.com.clinicamedica.ui;

import java.util.Scanner;

public class Utils {
    public static Scanner entrada = new Scanner(System.in);
    // utilitarios 

    public static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        entrada.nextLine();
    }

    public static int lerInteiro() {
        try {
            String linha = entrada.nextLine().trim();
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String lerTexto(String prompt) {
        System.out.print(prompt);
        return entrada.nextLine().trim();
    }

    public static void msgOpcaoInvalida() {
        System.out.println("Opção inválida. Tente novamente.");
        pausar();
    }

   

    public static void fecharScanner() {
        entrada.close();
    }
}
