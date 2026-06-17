package br.com.clinicamedica.ui;

import java.util.Scanner;

public class Utils {

    private static final Scanner entrada = new Scanner(System.in);

    public static void limparTela() {
    for (int i = 0; i < 15; i++) {
        System.out.println();
    }
}

    public static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine();
    }

    public static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(entrada.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    public static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        entrada.nextLine();
    }

    public static void msgOpcaoInvalida() {
        System.out.println("Opção inválida!");
    }

    public static void fecharScanner() {
        entrada.close();
    }
}