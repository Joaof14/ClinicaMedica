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
    public static boolean lerBooleano(String mensagem) {
        while (true) {
            String entrada = lerTexto(mensagem).trim().toLowerCase();

            if (entrada.equals("true") || entrada.equals("t") || entrada.equals("sim") || entrada.equals("s")) {
                return true;
            }

            if (entrada.equals("false") || entrada.equals("f") || entrada.equals("nao") || entrada.equals("não") || entrada.equals("n")) {
                return false;
            }

            System.out.println("Valor inválido. Digite sim/não ou true/false.");
        }
    }
    public static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        entrada.nextLine();
    }

    public static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(entrada.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido (ex: 1200.50).");
            }
        }
    }

    public static float lerFloat(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Float.parseFloat(entrada.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido (ex: 75.5).");
            }
        }
    }

    public static java.time.LocalDate lerData(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return java.time.LocalDate.parse(entrada.nextLine());
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use AAAA-MM-DD.");
            }
        }
    }

    public static java.time.LocalTime lerHorario(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return java.time.LocalTime.parse(entrada.nextLine());
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Formato de horário inválido. Use HH:MM.");
            }
        }
    }

    public static void msgOpcaoInvalida() {
        System.out.println("Opção inválida!");
    }

    public static void fecharScanner() {
        entrada.close();
    }
}