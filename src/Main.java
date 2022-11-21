import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int option;
        Scanner intScanner = new Scanner(System.in);
        Scanner strScanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Parkir masuk");
            System.out.println("2. Parkir keluar");
            System.out.print("Pilihan anda : ");
            int pilihan = intScanner.nextInt();
            if (pilihan == 1) {
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
        Kendaraan newKendaraan = new Kendaraan();
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        int pilihan = intScanner.nextInt();
        if (pilihan == 1){
            newKendaraan.jenis = "Mobil";
        } else if (pilihan == 2) {
            newKendaraan.jenis = "Motor";
        }
        newKendaraan.noPol = strScanner.nextLine();
    }

    public static void parkirKeluar(){

    }
}
