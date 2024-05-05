import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {

    //ATRIBUTOS
    private Scanner scanner;
    private CyclicBarrier barrera;

    //CONSTRUCTOR
    public Main(Scanner scanner) {
        this.scanner = scanner;
        this.barrera = new CyclicBarrier(2);
    }

    //MOSTRAR MENU PRINCIPAL
    public void showMenuPrincipal(){

        System.out.println("Bienvenido al simulador de servidor-cliente");
        System.out.println("1. Ejecución con un único cliente");
        System.out.println("2. Ejecución con varios clientes");
        System.out.println("3. Salir");
        System.out.print("Opcion: ");

    }

    //GET OPCION
    public int getOpcion(){
        return scanner.nextInt();
    }

    //INSTRUCCIONES DE QUE HACER CON CADA OPCION
    public void doOpciones(int opcion) throws InterruptedException, BrokenBarrierException{

        switch(opcion){
            case 1:
            	
                System.out.println("\n***Opcion con un único cliente***");
                try {
                    doFirstOption();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
                break;

            case 2:
                System.out.println("\n***Opcion varios clientes***");
                try {
                    doSecondOption();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
                break;

            case 3:
                System.err.println("Saliendo del programa");
                break;
            
            default:
                System.out.println("Opción no valida, vuelva a intentar");
                break;
        }
    }
    
    // PRIMERA OPCION
    private void doFirstOption() throws IOException, NoSuchAlgorithmException {
    	int numClientes = 1;
    	System.out.print("Digite el número a comunicar: ");
    	int numToComunicate = getOpcion();
    	Servidor server = new Servidor();
    	server.start();
    	Cliente cliente = new Cliente(numClientes, numToComunicate, server.getPublica());
    	cliente.runClientes();
    }
    
    // SEGUNDA OPCIÓN
    private void doSecondOption() throws IOException, NoSuchAlgorithmException{
    	System.out.print("Digite el número de clientes: ");
    	int numClientes = getOpcion();
    	System.out.print("\nDigite el número a comunicar: ");
    	int numToComunicate = getOpcion();
    	Servidor server = new Servidor();
    	server.start();
    	Cliente cliente = new Cliente(numClientes, numToComunicate, server.getPublica());
    	cliente.runClientes();
    }

    //PROGRAMA PRINCIPAL
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Scanner scanner = new Scanner(System.in);
        Main main = new Main(scanner);
        int opcion;
        do{
            main.showMenuPrincipal();
            opcion = main.getOpcion();
            main.doOpciones(opcion);
        }while(opcion != 3);

        scanner.close();
    }
}
