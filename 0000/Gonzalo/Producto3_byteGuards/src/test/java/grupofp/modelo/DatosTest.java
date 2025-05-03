package test.java.grupofp.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals; // Para usar métodos estáticos como assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue;

import grupofp.modelo.*;
import org.junit.jupiter.api.Test; // Para la anotación @Test de JUnit 5

import java.util.ArrayList;
import java.util.List;


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





    @Test
    public void addCliente() {
        // 1. Configuración del entorno: Crear una lista para manejar clientes
        List<Cliente> listaClientes = new ArrayList<>();

        // Crear clientes
        Cliente clienteEstandar = new ClienteEstandar("Gonzalo", "Calle Falsa 123", "12345678A", "gonzalo@example.com");
        Cliente clientePremium = new ClientePremium("Laura", "Avenida Real 456", "87654321B", "laura@example.com", 15.0f);

        // 2. Agregar los clientes a la lista
        listaClientes.add(clienteEstandar);
        listaClientes.add(clientePremium);

        // 3. Validación del resultado
        assertEquals(2, listaClientes.size(), "Debe haber 2 clientes en la lista");
        assertTrue(listaClientes.contains(clienteEstandar), "La lista debe contener al cliente estándar");
        assertTrue(listaClientes.contains(clientePremium), "La lista debe contener al cliente premium");
    }

}