import java.sql.*;
import java.util.Scanner;

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
        if (pilihan == 1) {
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
            stmt.close();
            c.commit();
            c.close();
        }catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
    }

    public static void parkirKeluar(){
        Scanner strScanner = new Scanner(System.in);
        Connection c = null;
        Statement stmt = null;
        Info_masuk infoBaru = new Info_masuk();

        System.out.print("Masukkan nomor polisi : ");
        infoBaru.noPol = strScanner.nextLine();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/parkingsystem", "postgres", "@Randy2003");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT NO_TIKET FROM INFO_MASUK WHERE NOPOL = '%s' AND BIAYA IS NULL;", infoBaru.noPol));
            rs.next();
            infoBaru.no_tiket = rs.getInt("NO_TIKET");
            stmt.executeUpdate(String.format("UPDATE INFO_MASUK SET TGGL_KELUAR = CURRENT_DATE, JAM_KELUAR = CURRENT_TIME WHERE NO_TIKET = %d;", infoBaru.no_tiket));
            rs = stmt.executeQuery(String.format("SELECT * FROM INFO_MASUK WHERE NO_TIKET = %d;", infoBaru.no_tiket));
            rs.next();
            infoBaru.jenis = rs.getString("JENIS");
            infoBaru.tggl_masuk = rs.getDate("TGGL_MASUK");
            infoBaru.jam_masuk = rs.getTime("JAM_MASUK");
            infoBaru.tggl_keluar = rs.getDate("TGGL_KELUAR");
            infoBaru.jam_keluar = rs.getTime("JAM_KELUAR");
            if (infoBaru.jenis.equals("Motor")){
                infoBaru.biaya = 3000 + calcTotalWaktu(infoBaru) * 2000;
            } else {
                infoBaru.biaya = 4000 + calcTotalWaktu(infoBaru) * 3000;
            }
            stmt.executeUpdate(String.format("UPDATE INFO_MASUK SET BIAYA = %d WHERE NO_TIKET = %d;", infoBaru.biaya, infoBaru.no_tiket));

            stmt.close();
            c.commit();
            c.close();

            System.out.print("No. Tiket: ");
            System.out.println(infoBaru.no_tiket);
            System.out.print("Jenis Kendaraan: ");
            System.out.println(infoBaru.jenis);
            System.out.println(String.format("Tanggal Masuk   : %s           Tanggal Keluar  : %s", infoBaru.tggl_masuk, infoBaru.tggl_keluar));
            System.out.println(String.format("Jam Masuk       : %s           Jam Keluar      : %s", infoBaru.tggl_masuk, infoBaru.tggl_keluar));
            System.out.print("Biaya: ");
            System.out.println(infoBaru.biaya);

        }catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return;
        }
    }

    public static int calcTotalWaktu(Info_masuk dataBaru){
        int jam_keluar = Integer.parseInt(dataBaru.jam_keluar.toString().substring(0,2));
        int jam_masuk = Integer.parseInt(dataBaru.jam_masuk.toString().substring(0,2));

        if ((jam_keluar - jam_masuk) > 2){
            return 2;
        }

        return jam_keluar - jam_masuk;
    }
}
