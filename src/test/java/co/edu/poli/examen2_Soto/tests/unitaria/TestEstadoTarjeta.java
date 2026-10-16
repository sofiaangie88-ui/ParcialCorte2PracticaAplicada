package co.edu.poli.examen2_Soto.tests.unitaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_Soto.modelo.Credito;
import co.edu.poli.examen2_Soto.modelo.EstadoTarjeta;
import co.edu.poli.examen2_Soto.modelo.Tarjeta;
import co.edu.poli.examen2_Soto.modelo.Titular;

/**
 * Pruebas unitarias de la HU-01: estados válidos de una tarjeta
 */
public class TestEstadoTarjeta {

	/**
	 * Verifica que existan únicamente los tres estados requeridos
	 */
	@Test
	void estados_sonActivaBloqueadaCancelada() {

		EstadoTarjeta[] valores = EstadoTarjeta.values();

		assertEquals(3, valores.length);
		assertEquals(EstadoTarjeta.ACTIVA, EstadoTarjeta.valueOf("ACTIVA"));
		assertEquals(EstadoTarjeta.BLOQUEADA, EstadoTarjeta.valueOf("BLOQUEADA"));
		assertEquals(EstadoTarjeta.CANCELADA, EstadoTarjeta.valueOf("CANCELADA"));
	}

	/**
	 * Verifica que un valor fuera de los tres estados sea rechazado
	 */
	@Test
	void estadoInvalido_lanzaExcepcion() {

		try {
			EstadoTarjeta.valueOf("SUSPENDIDA");
			fail("Debió lanzar IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// resultado esperado: el estado no es válido
		}
	}

	/**
	 * Verifica que una tarjeta de crédito conserve el estado con el que se crea
	 */
	@Test
	void credito_conservaEstadoInicial() {

		Tarjeta t = new Credito("456", "2025-12-25", EstadoTarjeta.BLOQUEADA, new Titular("1", "Test"), 5000);

		assertEquals(EstadoTarjeta.BLOQUEADA, t.getEstado());
		assertTrue(t.toString().contains("BLOQUEADA"));
	}
}