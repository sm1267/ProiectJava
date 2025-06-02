package app;

import audit.AuditService;
import models.*;
import services.InventoryService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner sc  = new Scanner(System.in);
    private static final AuditService audit = AuditService.INSTANCE;
    private static final InventoryService inv = InventoryService.INSTANCE;

    public static void main(String[] args) throws Exception {
        audit.log("app_start");

        while (true) {
            System.out.println("\nMENIU");
            System.out.println("1. Adauga categorie");
            System.out.println("2. Adauga furnizor");
            System.out.println("3. Adauga produs");
            System.out.println("4. Afiseaza toate produsele");
            System.out.println("5. Sterge produs dupa ID");
            System.out.println("0. Iesire");
            System.out.print("Alegere: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1 -> addCategory();
                case 2 -> addSupplier();
                case 3 -> addProduct();
                case 4 -> listProducts();
                case 5 -> deleteProduct();
                case 0 -> {
                    audit.log("app_exit");
                    return;
                }
                default -> {
                    System.out.println("Optiune invalida.");
                    audit.log("menu_invalid_option");
                }
            }
        }
    }


    private static void addCategory() throws Exception {
        System.out.print("Nume categorie: ");
        String nume = sc.nextLine();
        System.out.print("Descriere: ");
        String desc = sc.nextLine();
        Category c = inv.getCategories().add(nume, desc);
        System.out.println("Categorie adaugata: " + c);
        audit.log("menu_add_category");
    }

    private static void addSupplier() throws Exception {
        System.out.print("Nume furnizor: ");
        String nume = sc.nextLine();
        System.out.print("Telefon: ");
        String tel = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        Supplier s = inv.getSuppliers().add(nume, tel, email);
        System.out.println("Furnizor adaugat: " + s);
        audit.log("menu_add_supplier");
    }

    private static void addProduct() throws Exception {
        System.out.print("Nume produs: ");
        String nume = sc.nextLine();
        System.out.print("Preț: ");
        double pret = sc.nextDouble();
        System.out.print("Cantitate stoc: ");
        int stoc = sc.nextInt();
        sc.nextLine();
        System.out.print("Este perisabil? (da/nu): ");
        boolean perisabil = sc.nextLine().equalsIgnoreCase("da");

        System.out.print("ID categorie: ");
        int catId = sc.nextInt();
        System.out.print("ID furnizor: ");
        int supId = sc.nextInt();
        sc.nextLine();

        Category cat = inv.getCategories().byId(catId).orElse(null);
        Supplier sup = inv.getSuppliers().byId(supId).orElse(null);

        if (cat == null || sup == null) {
            System.out.println("Categorie sau furnizor invalid.");
            audit.log("menu_add_product_fail");
            return;
        }

        Product p;
        if (perisabil) {
            System.out.print("Data expirare (YYYY-MM-DD): ");
            LocalDate exp = LocalDate.parse(sc.nextLine());
            p = new PerishableProduct(nume, pret, stoc, exp, cat, sup);
        } else {
            p = new NonPerishableProduct(nume, pret, stoc, cat, sup);
        }

        p = inv.getProducts().add(p);
        System.out.println("Produs adaugat: " + p);
        audit.log("menu_add_product");
    }

    private static void listProducts() throws Exception {
        inv.getProducts().all().forEach(System.out::println);
        audit.log("menu_list_products");
    }

    private static void deleteProduct() throws Exception {
        System.out.print("ID produs de sters: ");
        int id = sc.nextInt();
        sc.nextLine();
        boolean ok = inv.getProducts().delete(id);
        System.out.println(ok ? "Sters cu succes!" : "Produsul nu exista.");
        audit.log("menu_delete_product");
    }
}


