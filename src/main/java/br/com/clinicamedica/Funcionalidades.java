package br.com.clinicamedica;

import java.util.Scanner;

public abstract class Funcionalidades {

    private static final Scanner input = new Scanner(System.in);

    public static int validarInt(int low, int high, int escape) {
        int choice;

        do {
            try {
                System.out.printf("Informe a entrada entre %d e %d \n utilize %d para sair \n", low, high, escape);
                choice = input.nextInt();

                if (((low <= choice) && (choice <= high)) || choice == escape) {
                    return choice;
                }
            } catch (Exception exception) {
                System.out.println("Erro: " + exception.getMessage());
                input.nextLine();
            }
        } while (true);
    }
}