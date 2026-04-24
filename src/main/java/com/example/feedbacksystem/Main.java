package com.example.feedbacksystem;

import com.example.feedbacksystem.services.DatabaseService;

import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Feedback");
            System.out.println("2. View Feedback");
            System.out.println("3. Update Feedback");
            System.out.println("4. Delete Feedback (to Trash)");
            System.out.println("5. Export CSV");
            System.out.println("6. Import CSV");
            System.out.println("7. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Message: ");
                    String msg = sc.nextLine();

                    DatabaseService.addFeedback(name, email, msg);
                    break;

                case 2:
                    try {
                        ResultSet rs = DatabaseService.getActive();
                        while (rs.next()) {
                            System.out.println(
                                    "ID: " + rs.getInt("id") +
                                            " | " + rs.getString("email") +
                                            " | " + rs.getString("message")
                            );
                        }
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    break;

                case 3:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New message: ");
                    String newMsg = sc.nextLine();

                    DatabaseService.updateFeedback(id, newMsg);
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    int delId = sc.nextInt();

                    DatabaseService.softDelete(delId);
                    break;

                case 5:
                    DatabaseService.exportToCSV("data.csv");
                    System.out.println("Exported!");
                    break;

                case 6:
                    DatabaseService.importFromCSV("data.csv");
                    System.out.println("Imported!");
                    break;

                case 7:
                    System.out.println("Exit...");
                    return;

                default:
                    System.out.println("Wrong choice");
            }
        }
    }
}