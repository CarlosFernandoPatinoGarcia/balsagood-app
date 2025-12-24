import java.math.BigDecimal;
import java.math.RoundingMode;

public class BftVerify {
    public static void main(String[] args) {
        // Inputs
        BigDecimal largoOriginal = new BigDecimal("4");
        BigDecimal largoAceptado = new BigDecimal("3"); // "largo" in DTO
        BigDecimal espesor = new BigDecimal("1");
        BigDecimal cantidad = new BigDecimal("2");
        Boolean esCastigada = true;

        System.out.println("Inputs:");
        System.out.println("Largo Original: " + largoOriginal);
        System.out.println("Largo Aceptado: " + largoAceptado);
        System.out.println("Espesor: " + espesor);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Es Castigada: " + esCastigada);
        System.out.println("Constant Ancho: 81");

        // Calculation Logic (Copied from Service)
        BigDecimal bftItemRecibido;
        BigDecimal bftItemAceptado;

        if (Boolean.TRUE.equals(esCastigada)) {
            // Recibido uses Original Length
            bftItemRecibido = largoOriginal.multiply(new BigDecimal("81"))
                    .multiply(espesor)
                    .multiply(cantidad)
                    .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);

            // Aceptado uses Punished Length
            bftItemAceptado = largoAceptado.multiply(new BigDecimal("81"))
                    .multiply(espesor)
                    .multiply(cantidad)
                    .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);
        } else {
            bftItemRecibido = BigDecimal.ZERO;
            bftItemAceptado = BigDecimal.ZERO;
        }

        System.out.println("\nResults:");
        System.out.println("BFT Recibido Calculated: " + bftItemRecibido);
        System.out.println("BFT Aceptado Calculated: " + bftItemAceptado);

        BigDecimal expectedRecibido = new BigDecimal("54.0000"); // 54
        BigDecimal expectedAceptado = new BigDecimal("40.5000"); // 40.5

        boolean matchRecibido = bftItemRecibido.compareTo(expectedRecibido) == 0;
        boolean matchAceptado = bftItemAceptado.compareTo(expectedAceptado) == 0;

        System.out.println("\nVerification:");
        System.out.println("Matches Expected Recibido (54)? " + matchRecibido);
        System.out.println("Matches Expected Aceptado (40.5)? " + matchAceptado);
    }
}
