package miPrincipal;

public class Principal {
    public String getGreeting() {
        return "Buenas a todas guapisimos!";
    }

    public static void main(String[] args) {
        Principal Principal = new Principal();
        String saludo = Principal.getGreeting();
        System.out.println(new Principal().getGreeting());
    }
}