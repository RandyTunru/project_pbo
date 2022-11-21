import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        int option;
        Scanner intScanner = new Scanner(System.in);
        Scanner strScanner = new Scanner(System.in);
        while (true) {
            System.out.println("0. Keluar");
            System.out.println("1. Parkir masuk");
            System.out.println("2. Parkir keluar");
            System.out.print("Pilihan anda : ");
            int pilihan = intScanner.nextInt();
            if (pilihan == 0) {
                System.exit(0);
            }else if (pilihan == 1) {
                parkirMasuk();
            } else if (pilihan == 2) {
                parkirKeluar();
            } else {
                System.out.println("Opsi tidak tersedia");
            }
        }
    }

    public static void parkirMasuk(){
        Scanner intScanner = new Scanner(System.in);
        Scanner strScanner = new Scanner(System.in);
        Connection c = null;
        Statement stmt = null;

        Kendaraan newKendaraan = new Kendaraan();
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.print("Pilihan anda : ");
        int pilihan = intScanner.nextInt();
        if (pilihan == 1){
            newKendaraan.jenis = "Mobil";
        } else if (pilihan == 2) {
            newKendaraan.jenis = "Motor";
        }
        System.out.print("Masukkan nomor polisi : ");
        newKendaraan.noPol = strScanner.nextLine();
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/parkingsystem", "postgres", "@Randy2003");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate(String.format("INSERT INTO INFO_MASUK (NOPOL, JENIS) VALUES ('%s', '%s');", newKendaraan.noPol, newKendaraan.jenis));
            c.commit();
        }catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
    }

    public static void parkirKeluar(){

    }
}
