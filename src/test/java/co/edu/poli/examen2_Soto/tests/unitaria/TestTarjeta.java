package co.edu.poli.examen2_Soto.tests.unitaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_Soto.modelo.Debito;
import co.edu.poli.examen2_Soto.modelo.EstadoTarjeta;
import co.edu.poli.examen2_Soto.modelo.Tarjeta;
import co.edu.poli.examen2_Soto.modelo.Titular;

public class TestTarjeta {

	/**
	 * Verifica que una tarjeta pueda establecerse en estado bloqueada
	 */
	@Test
	void bloquear_cambiaEstadoABloqueada() {

		Titular titular = new Titular("1", "Test");

		Tarjeta t = new Debito(
				"123",
				"2025-12-25",
				EstadoTarjeta.ACTIVA,
				titular,
				1000
		);

		String mensaje = t.bloquear();

		assertEquals(EstadoTarjeta.BLOQUEADA, t.getEstado());
		assertTrue(mensaje.contains("BLOQUEADA"));
	}

	/**
	 * Verifica que una tarjeta pueda establecerse en estado activa
	 */
	@Test
	void activar_cambiaEstadoAActiva() {

		Titular titular = new Titular("1", "Test");

		Tarjeta t = new Debito(
				"123",
				"2025-12-25",
				EstadoTarjeta.BLOQUEADA,
				titular,
				1000
		);

		String mensaje = t.activar();

		assertEquals(EstadoTarjeta.ACTIVA, t.getEstado());
		assertTrue(mensaje.contains("ACTIVADA"));
	}

	/**
	 * Verifica que una tarjeta pueda establecerse en estado cancelada
	 */
	@Test
	void estadoPuedeSerCancelado() {

		Titular titular = new Titular("1", "Test");

		Tarjeta t = new Debito(
				"123",
				"2025-12-25",
				EstadoTarjeta.CANCELADA,
				titular,
				1000
		);

		assertEquals(EstadoTarjeta.CANCELADA, t.getEstado());
	}

	@Test
	void getters_retornaValoresCorrectos() {

		Titular titular = new Titular("1", "Test");

		Tarjeta t = new Debito(
				"123",
				"2025-12-25",
				EstadoTarjeta.ACTIVA,
				titular,
				1000
		);

		assertEquals("123", t.getNumero());
		assertEquals("2025-12-25", t.getFechaExp());
		assertEquals(EstadoTarjeta.ACTIVA, t.getEstado());
		assertEquals(titular, t.getTitular());
	}

	@Test
	void setters_modificanValores() {

		Titular titular = new Titular("1", "Test");
		Titular nuevo = new Titular("2", "Nuevo");

		Tarjeta t = new Debito(
				"123",
				"2025-12-25",
				EstadoTarjeta.ACTIVA,
				titular,
				1000
		);

		t.setNumero("999");
		t.setFechaExp("2030-01-01");
		t.setEstado(EstadoTarjeta.BLOQUEADA);
		t.setTitular(nuevo);

		assertEquals("999", t.getNumero());
		assertEquals("2030-01-01", t.getFechaExp());
		assertEquals(EstadoTarjeta.BLOQUEADA, t.getEstado());
		assertEquals(nuevo, t.getTitular());
	}

	@Test
	void toString_contieneDatos() {

		Titular titular = new Titular("1", "Test");

		Tarjeta t = new Debito(
				"123",
				"2025-12-25",
				EstadoTarjeta.ACTIVA,
				titular,
				1000
		);

		String texto = t.toString();

		assertTrue(texto.contains("123"));
		assertTrue(texto.contains("2025-12-25"));
		assertTrue(texto.contains("ACTIVA"));
	}
}