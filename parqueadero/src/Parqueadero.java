import java.util.*;

public class Parqueadero {
    static Scanner sc = new Scanner(System.in);
    static List<Vehiculo> vehiculos = new ArrayList<>();
    static Stack<Vehiculo> vehiculos2ruedas = new Stack<>();
    static Stack<Vehiculo> vehiculos4ruedas = new Stack<>();
    
    public static void main(String[] args) {
        int opcion = 0;
        do {
            mostrarMenu();
            opcion = sc.nextInt();
            switch (opcion) {
                case 1 -> ingresarVehiculo();
                case 2 -> visualizarTabla();
                case 3 -> visualizarVehiculos(vehiculos2ruedas);
                case 4 -> visualizarVehiculos(vehiculos4ruedas);
                case 5 -> mostrarTotal();
                case 6 -> eliminarVehiculo();
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 7);
    }
    
    static void mostrarMenu() {
        System.out.println("\n----- MENU -----");
        System.out.println("1. Ingreso de vehículo");
        System.out.println("2. Visualizar tabla actualizada con el valor a pagar");
        System.out.println("3. Visualizar lista de vehículos de 2 ruedas con el valor a pagar total");
        System.out.println("4. Visualizar lista de vehículos de 4 ruedas con el valor a pagar total");
        System.out.println("5. Cantidad de vehículos en parqueadero y valor total a pagar");
        System.out.println("6. Eliminar algún vehículo");
        System.out.println("7. Salir");
        System.out.print("Ingrese una opción: ");
    }
    
    static void ingresarVehiculo() {
        System.out.print("Ingrese la placa del vehículo: ");
        String placa = sc.next();
        System.out.print("Ingrese el tipo de vehículo (B = bicicleta, C = ciclomotor, M = motocicleta, A = carro): ");
        String tipo = sc.next().toUpperCase();
        System.out.print("Ingrese la hora de ingreso (formato hh:mm): ");
        String hora = sc.next();
        int numero = vehiculos.size() + 1;
        Vehiculo vehiculo = new Vehiculo(placa, tipo, hora, numero);
        vehiculos.add(vehiculo);
        System.out.println("Vehículo ingresado correctamente.");
    }
    
    static void visualizarTabla() {
        System.out.println("\n----- TABLA DE VEHÍCULOS -----");
        System.out.println("Placa\tTipo\tHora de ingreso\tNúmero\tValor a pagar");
        for (Vehiculo v : vehiculos) {
            double valor = v.calcularValor();
            System.out.printf("%s\t%s\t%s\t\t%d\t$%.0f%n", v.placa, v.tipo, v.hora, v.numero, valor);
        }
    }
    
    static void visualizarVehiculos(Stack<Vehiculo> vehiculosStack) {
        String tipo = vehiculosStack == vehiculos2ruedas ? "2 ruedas" : "4 ruedas";
        System.out.println("\n----- VEHÍCULOS DE " + tipo.toUpperCase() + " -----");
        double total = 0;
        System.out.println("Placa\tHora de ingreso\tNúmero\tValor a pagar");
        while (!vehiculosStack.empty()) {
            Vehiculo v = vehiculosStack.pop();
            double valor = v.calcularValor();
            System.out.printf("%s\t%s\t\t%d\t$%.0f%n", v.placa, v.hora, v.numero, valor);
            total += valor;
        }
        System.out.printf("Total a pagar por vehículos de %s: $%.0f%n", tipo, total);
    }
    
    static void mostrarTotal() {
        int cantidad = vehiculos.size();
        double total = 0;
        for (Vehiculo v : vehiculos) {
            total += v.calcularValor();
        }
        System.out.printf("Cantidad de vehículos en parqueadero: %d%n", cantidad);
        System.out.printf("Valor total a pagar: $%.0f%n", total);
    }
    
    static void eliminarVehiculo() {
        System.out.print("Ingrese la placa del vehículo a eliminar: ");
        String placa = sc.next();
        boolean eliminado = false;
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo v = vehiculos.get(i);
            if (v.placa.equals(placa)) {
                vehiculos.remove(i);
                if (v.tipo.equals("B") || v.tipo.equals("C")) {
                    vehiculos2ruedas.remove(v);
                } else {
                    vehiculos4ruedas.remove(v);
                }
                eliminado = true;
                System.out.println("Vehículo eliminado correctamente.");
                break;
            }
        }
        if (!eliminado) {
            System.out.println("Vehículo no encontrado.");
        }
    }
}

class Vehiculo {
    String placa;
    String tipo;
    String hora;
    int numero;
    
    Vehiculo(String placa, String tipo, String hora, int numero) {
        this.placa = placa;
        this.tipo = tipo;
        this.hora = hora;
        this.numero = numero;
    }
    
    double calcularValor() {
        double valor = 0;
        int minutos = Integer.parseInt(hora.split(":")[1]);
        int horas = Integer.parseInt(hora.split(":")[0]);
        minutos += horas * 60;
        int minutosActual = Calendar.getInstance().get(Calendar.MINUTE);
        int horasActual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        minutosActual += horasActual * 60;
        int tiempo = minutosActual - minutos;
        if (tiempo <= 0) {
            return valor;
        }
        switch (tipo) {
            case "B":
                valor = tiempo * 20;
            case "C":
                valor = tiempo * 20;
                break;
            case "M":
                valor = tiempo * 30;
                break;
            case "A":
                valor = tiempo * 60;
                break;
            default:
                break;
        }
        return valor;
    }
}

