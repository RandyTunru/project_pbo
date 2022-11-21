import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int option;
        Scanner intScanner = new Scanner(System.in);
        Scanner strScanner = new Scanner(System.in);
        while (true){
            System.out.println("1. Parkir masuk");
            System.out.println("2. Parkir keluar");
            System.out.print("Pilihan anda : ");
            int pilihan = intScanner.nextInt();
            if (pilihan == 1){

            }else if (pilihan == 2){

            }else{
                System.out.println("Opsi tidak tersedia");
            }

        }
    }
}
