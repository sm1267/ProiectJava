import models.*;
import services.InventoryService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final InventoryService inventoryService = new InventoryService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        runCLI();
    }

    private static void runCLI() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addCategory();
                    case "2" -> addSupplier();
                    case "3" -> addProduct();
                    case "4" -> listProducts();
                    case "5" -> sellProduct();
                    case "6" -> restockProduct();
                    case "7" -> showLowStock();
                    case "0" -> running = false;
                    default -> System.out.println("Optiune invalida.");
                }
            } catch (Exception e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
            ===== Gestiune Inventar =====
            1. Adauga categorie
            2. Adauga furnizor
            3. Adauga produs
            4. Listeaza toate produsele
            5. Vinde produs
            6. Aprovizioneaza produs
            7. Produse sub stoc minim
            0. Iesire
            Alege optiunea:""");
    }

    private static void addCategory() {
        System.out.print("Nume categorie: ");
        String name = scanner.nextLine();
        Category category = inventoryService.addCategory(name);
        System.out.println("Categorie adaugata: " + category);
    }

    private static void addSupplier() {
        System.out.print("Nume furnizor: ");
        String name = scanner.nextLine();
        System.out.print("Telefon: ");
        String phone = scanner.nextLine();
        Supplier supplier = inventoryService.addSupplier(name, phone);
        System.out.println("Furnizor adaugat: " + supplier);
    }

    private static void addProduct() {
        if (inventoryService.listAllProducts().isEmpty()) {
            System.out.println("Adauga mai intai categorii si furnizori.");
            return;
        }
        System.out.print("Nume produs: ");
        String name = scanner.nextLine();
        System.out.print("Pret: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Cantitate initiala: ");
        int qty = Integer.parseInt(scanner.nextLine());

        Category category = inventoryService.listAllProducts().get(0).getCategory();
        Supplier supplier = inventoryService.listAllProducts().get(0).getSupplier();

        Product product = new NonPerishableProduct(name, price, qty, category, supplier);
        inventoryService.addProduct(product);
        System.out.println("Produs adaugat: " + product);
    }

    private static void listProducts() {
        inventoryService.listAllProducts().forEach(System.out::println);
    }

    private static void sellProduct() {
        System.out.print("ID produs: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Cantitate vanduta: ");
        int qty = Integer.parseInt(scanner.nextLine());
        inventoryService.sellProduct(id, qty);
        System.out.println("Vanzare efectuata.");
    }

    private static void restockProduct() {
        System.out.print("ID produs: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Cantitate adaugata: ");
        int qty = Integer.parseInt(scanner.nextLine());
        inventoryService.restockProduct(id, qty);
        System.out.println("Produs aprovizionat.");
    }

    private static void showLowStock() {
        System.out.print("Stoc minim: ");
        int threshold = Integer.parseInt(scanner.nextLine());
        inventoryService.getProductsBelowStock(threshold).forEach(System.out::println);
    }

    private static void seedData() {
        Category beverages = inventoryService.addCategory("Bauturi");
        Supplier supplier = inventoryService.addSupplier("Coca-Cola", "0722333444");
        inventoryService.addProduct(new PerishableProduct("Coca-Cola 500ml", 5.0, 50, beverages, supplier, LocalDate.now().plusMonths(6)));

        Category electronics = inventoryService.addCategory("Electronice");
        Supplier supplier2 = inventoryService.addSupplier("Samsung", "0733555666");
        inventoryService.addProduct(new NonPerishableProduct("Samsung Galaxy S24", 4000.0, 15, electronics, supplier2));
    }
}
