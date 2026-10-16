package co.edu.poli.examen2_Soto.tests.integracion;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_Soto.modelo.Credito;
import co.edu.poli.examen2_Soto.modelo.Debito;
import co.edu.poli.examen2_Soto.modelo.EstadoTarjeta;
import co.edu.poli.examen2_Soto.modelo.Tarjeta;
import co.edu.poli.examen2_Soto.modelo.Titular;
import co.edu.poli.examen2_Soto.servicios.ConexionBD;
import co.edu.poli.examen2_Soto.servicios.DAOTarjeta;

/**
 * Pruebas de integración del DAO de tarjetas. Verifican que el estado (HU-01) se almacene y se recupere correctamente en MySQL
 *
 * Requiere la base de datos creada con ScriptDB.sql y el archivo .env configurado
 */
public class TestDAOTarjeta {

	DAOTarjeta dao = new DAOTarjeta();

	/** Números de tarjeta que usan estas pruebas */
	private static final String[] NUMEROS_PRUEBA = { "999001", "999002", "999003", "999004" };

	/**
	 * Elimina las tarjetas de prueba antes y después de cada test para que las pruebas se puedan ejecutar varias veces sin violar la llave primaria
	 */
	@BeforeEach
	@AfterEach
	void limpiarTarjetasDePrueba() throws Exception {

		Connection con = ConexionBD.getInstancia().getConexion();

		// tarjeta_debito y tarjeta_credito se borran por ON DELETE CASCADE
		try (PreparedStatement ps = con.prepareStatement("DELETE FROM tarjeta WHERE numero = ?")) {
			for (String numero : NUMEROS_PRUEBA) {
				ps.setString(1, numero);
				ps.executeUpdate();
			}
		}
	}

	/**
	 * Verifica que una tarjeta de débito se cree y que su estado se almacene y recupere correctamente desde la base de datos
	 */
	@Test
	void create_debito_y_readone() throws Exception {

		Titular titular = new Titular("T001", "Test");

		Debito debito = new Debito(
				"999001",
				"2025-12-25",
				EstadoTarjeta.ACTIVA,
				titular,
				5000.0
		);

		String result = dao.create(debito);

		assertTrue(result.contains("guardada"));

		Tarjeta t = dao.readone("999001");

		assertNotNull(t);
		assertTrue(t instanceof Debito);

		Debito d = (Debito) t;

		assertEquals(5000.0, d.getSaldo());
		assertEquals(EstadoTarjeta.ACTIVA, d.getEstado());
	}

	/**
	 * Verifica que una tarjeta de crédito se cree y que su estado se almacene y recupere correctamente desde la base de datos
	 */
	@Test
	void create_credito_y_readone() throws Exception {

		Titular titular = new Titular("T001", "Test");

		Credito credito = new Credito(
				"999002",
				"2025-12-25",
				EstadoTarjeta.BLOQUEADA,
				titular,
				10000.0
		);

		String result = dao.create(credito);

		assertTrue(result.contains("guardada"));

		Tarjeta t = dao.readone("999002");

		assertNotNull(t);
		assertTrue(t instanceof Credito);

		Credito c = (Credito) t;

		assertEquals(10000.0, c.getLimite());
		assertEquals(EstadoTarjeta.BLOQUEADA, c.getEstado());
	}

	/**
	 * Verifica que una tarjeta cancelada conserve su estado después de ser almacenada y consultada
	 */
	@Test
	void create_tarjeta_cancelada_y_readone() throws Exception {

		Titular titular = new Titular("T001", "Test");

		Debito debito = new Debito(
				"999003",
				"2025-12-25",
				EstadoTarjeta.CANCELADA,
				titular,
				3000.0
		);

		String result = dao.create(debito);

		assertTrue(result.contains("guardada"));

		Tarjeta t = dao.readone("999003");

		assertNotNull(t);
		assertEquals(EstadoTarjeta.CANCELADA, t.getEstado());
	}

	/**
	 * Verifica que no sea posible guardar una tarjeta sin estado y que no quede ningún registro en la base de datos
	 */
	@Test
	void create_sinEstado_noSeGuarda() throws Exception {

		Titular titular = new Titular("T001", "Test");

		Debito debito = new Debito(
				"999004",
				"2025-12-25",
				null,
				titular,
				1000.0
		);

		try {
			dao.create(debito);
			fail("Debió lanzar IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// resultado esperado: no se permite una tarjeta sin estado
		}

		assertNull(dao.readone("999004"));
	}

	@Test
	void readone_noExiste() throws Exception {

		Tarjeta t = dao.readone("000000");

		assertNull(t);
	}
}