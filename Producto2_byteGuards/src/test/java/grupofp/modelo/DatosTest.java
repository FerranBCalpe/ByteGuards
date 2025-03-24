package test.java.grupofp.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals; // Para usar métodos estáticos como assertEquals

import grupofp.modelo.Articulo;
import grupofp.modelo.ListaArticulos;
import org.junit.jupiter.api.Test; // Para la anotación @Test de JUnit 5


public class DatosTest {

    @Test
    public void testAddArticulo() {
        // 1. Configuración del entorno
        ListaArticulos listaArticulos = new ListaArticulos(); // Crear una instancia de ListaArticulos
        String id = "A001";
        String descripcion = "Portátil Gaming";
        float precio = 1500.0f;
        float gastosEnvio = 20.0f;
        int tiempoPreparacion = 3;

        // 2. Crear y añadir el artículo
        Articulo articulo = new Articulo(id, descripcion, precio, gastosEnvio, tiempoPreparacion);
        listaArticulos.add(articulo); // Usar el método 'add' de la clase base Lista

        // 3. Validación del resultado
        assertEquals(1, listaArticulos.getSize(), "Debe haber un artículo en la lista");
        Articulo articuloEnLista = listaArticulos.getAt(0); // Usar el método 'getAt' para recuperar el artículo
        assertEquals(id, articuloEnLista.getcodigoArticulo(), "El código del artículo no coincide");
        assertEquals(descripcion, articuloEnLista.getDescripcion(), "La descripción no coincide");
        assertEquals(precio, articuloEnLista.getPrecio(), "El precio no coincide");
        assertEquals(gastosEnvio, articuloEnLista.getGastosEnvio(), "Los gastos de envío no coinciden");
        assertEquals(tiempoPreparacion, articuloEnLista.getTiempoPreparacion(), "El tiempo de preparación no coincide");
    }








 /*   @Test
    void addCliente() {
    }
*/
}