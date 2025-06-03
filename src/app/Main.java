package app;

import audit.AuditService;
import models.Category;
import models.NonPerishableProduct;
import models.PerishableProduct;
import models.Product;
import models.StockChangeLog;
import models.Supplier;
import services.InventoryService;
import services.StockChangeLogService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner sc               = new Scanner(System.in);
    private static final AuditService audit       = AuditService.INSTANCE;
    private static final InventoryService inv     = InventoryService.INSTANCE;
    private static final StockChangeLogService logService = StockChangeLogService.INSTANCE;

    public static void main(String[] args) throws Exception {
        audit.log("app_start");

        while (true) {
            System.out.println("\n===== MENIU INVENTAR =====");
            System.out.println("1.  Adauga categorie");
            System.out.println("2.  Adauga furnizor");
            System.out.println("3.  Adauga produs");
            System.out.println("4.  Afiseaza toate categoriile");
            System.out.println("5.  Afiseaza toti furnizorii");
            System.out.println("6.  Afiseaza toate produsele");
            System.out.println("7.  Sterge produs dupa ID");
            System.out.println("8.  Vanzare produs");
            System.out.println("9.  Aprovizionare produs");
            System.out.println("10. Lista produse dupa furnizor");
            System.out.println("11. Lista produse dupa categorie");
            System.out.println("0.  Iesire");
            System.out.print("Alege optiunea: ");
            int opt = Integer.parseInt(sc.nextLine());

            switch (opt) {
                case 1 -> addCategory();
                case 2 -> addSupplier();
                case 3 -> addProduct();
                case 4 -> listCategories();
                case 5 -> listSuppliers();
                case 6 -> listProducts();
                case 7 -> deleteProduct();
                case 8 -> sellProduct();
                case 9 -> restockProduct();
                case 10 -> listBySupplier();
                case 11 -> listByCategory();
                case 0 -> {
                    audit.log("app_exit");
                    System.out.println("Aplicatie inchisa. La revedere!");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Optiune invalida!");
                    audit.log("menu_invalid_option");
                }
            }
        }
    }

    private static void addCategory() throws Exception {
        System.out.print("Nume categorie: ");
        String nume = sc.nextLine();
        System.out.print("Descriere: ");
        String descriere = sc.nextLine();
        Category c = inv.getCategories().add(nume, descriere);
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
        System.out.print("Pret: ");
        double pret = Double.parseDouble(sc.nextLine());
        System.out.print("Cantitate stoc: ");
        int stoc = Integer.parseInt(sc.nextLine());

        listCategories();
        System.out.print("Alege ID categorie: ");
        int catId = Integer.parseInt(sc.nextLine());
        Optional<Category> catOpt = inv.getCategories().byId(catId);
        if (catOpt.isEmpty()) {
            System.out.println("Categorie inexistenta!");
            audit.log("menu_add_product_fail");
            return;
        }
        Category cat = catOpt.get();

        listSuppliers();
        System.out.print("Alege ID furnizor: ");
        int supId = Integer.parseInt(sc.nextLine());
        Optional<Supplier> supOpt = inv.getSuppliers().byId(supId);
        if (supOpt.isEmpty()) {
            System.out.println("Furnizor inexistent!");
            audit.log("menu_add_product_fail");
            return;
        }
        Supplier sup = supOpt.get();

        System.out.print("Este perisabil? (da/nu): ");
        String raspuns = sc.nextLine().trim().toLowerCase();
        Product p;
        if (raspuns.equals("da")) {
            System.out.print("Data expirare (YYYY-MM-DD): ");
            LocalDate dataExpirare = LocalDate.parse(sc.nextLine());
            p = new PerishableProduct(nume, pret, stoc, dataExpirare, cat, sup);
        } else {
            p = new NonPerishableProduct(nume, pret, stoc, cat, sup);
        }

        p = inv.addProduct(p);
        System.out.println("Produs adaugat: " + p);
        audit.log("menu_add_product");
    }

    private static void listCategories() throws Exception {
        List<Category> categorii = inv.getCategories().all();
        System.out.println(">> Lista categorii:");
        for (Category c : categorii) {
            System.out.printf("ID=%d, Nume='%s', Descriere='%s'%n",
                    c.getId(), c.getName(), c.getDescription());
        }
        audit.log("menu_list_categories");
    }

    private static void listSuppliers() throws Exception {
        List<Supplier> furnizori = inv.getSuppliers().all();
        System.out.println(">> Lista furnizori:");
        for (Supplier s : furnizori) {
            System.out.printf("ID=%d, Nume='%s', Tel='%s', Email='%s'%n",
                    s.getId(), s.getName(), s.getPhone(), s.getEmail());
        }
        audit.log("menu_list_suppliers");
    }

    private static void listProducts() throws Exception {
        List<Product> produse = inv.listProducts();
        System.out.println(">> Lista produse (sortata alfabetic):");
        for (Product p : produse) {
            System.out.printf(
                    "ID=%d, Nume='%s', Pret=%.2f, Stoc=%d, Categorie='%s', Furnizor='%s'%n",
                    p.getId(), p.getName(), p.getPrice(), p.getStockQty(),
                    p.getCategory().getName(), p.getSupplier().getName()
            );
        }
        audit.log("menu_list_products");
    }

    private static void deleteProduct() throws Exception {
        System.out.print("ID produs de sters: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean deleted = inv.getProducts().delete(id);
        if (deleted) {
            System.out.println("Produs sters cu succes!");
        } else {
            System.out.println("Produsul nu exista.");
        }
        audit.log("menu_delete_product");
    }

    private static void sellProduct() throws Exception {
        System.out.print("ID produs de vandut: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Cantitate vanduta: ");
        int qty = Integer.parseInt(sc.nextLine());

        Optional<Product> optProd = inv.findProduct(id);
        if (optProd.isEmpty()) {
            System.out.println("Produs inexistent!");
            audit.log("menu_sell_product_fail");
            return;
        }
        Product p = optProd.get();

        if (p.getStockQty() < qty) {
            System.out.println("Stoc insuficient!");
            audit.log("menu_sell_product_fail");
        } else {
            Product updated = new NonPerishableProduct(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getStockQty() - qty,
                    p.getCategory(),
                    p.getSupplier()
            );
            inv.getProducts().update(updated);

            int deltaQty = -qty;
            logService.add(new StockChangeLog(
                    null,
                    p,
                    deltaQty,
                    "sell",
                    LocalDateTime.now()
            ));
            System.out.println("Vanzare inregistrata.");
            audit.log("menu_sell_product");
        }
    }

    private static void restockProduct() throws Exception {
        System.out.print("ID produs de aprovizionat: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Cantitate adaugata: ");
        int qty = Integer.parseInt(sc.nextLine());

        Optional<Product> optProd = inv.findProduct(id);
        if (optProd.isEmpty()) {
            System.out.println("Produs inexistent!");
            audit.log("menu_restock_product_fail");
            return;
        }
        Product p = optProd.get();

        Product updated = new NonPerishableProduct(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getStockQty() + qty,
                p.getCategory(),
                p.getSupplier()
        );
        inv.getProducts().update(updated);

        int deltaQty = qty;
        logService.add(new StockChangeLog(
                null,
                p,
                deltaQty,
                "restock",
                LocalDateTime.now()
        ));
        System.out.println("Aprovizionare inregistrata.");
        audit.log("menu_restock_product");
    }

    private static void listBySupplier() throws Exception {
        System.out.print("ID furnizor: ");
        int supId = Integer.parseInt(sc.nextLine());
        List<Product> produse = inv.listProducts();
        boolean found = false;
        for (Product p : produse) {
            if (p.getSupplier().getId() == supId) {
                System.out.printf("ID=%d, Nume='%s', Stoc=%d%n",
                        p.getId(), p.getName(), p.getStockQty());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Niciun produs gasit pentru acest furnizor.");
        }
        audit.log("menu_list_by_supplier");
    }

    private static void listByCategory() throws Exception {
        System.out.print("ID categorie: ");
        int catId = Integer.parseInt(sc.nextLine());
        List<Product> produse = inv.listProducts();
        boolean found = false;
        for (Product p : produse) {
            if (p.getCategory().getId() == catId) {
                System.out.printf("ID=%d, Nume='%s', Stoc=%d%n",
                        p.getId(), p.getName(), p.getStockQty());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Niciun produs gasit pentru aceasta categorie.");
        }
        audit.log("menu_list_by_category");
    }
}









