package ar.edu.unlu.mastermind.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unlu.mastermind.juego.Jugador;

class TestJugador {
	Jugador jugador;
	@BeforeEach
	void setUp() throws Exception {
		jugador = new Jugador("Jugador 1");
	}

	@Test
	void probarQueDevuelveSuNombre() {
		assertEquals("Jugador 1",jugador.getNombre());
	}
	
	// 1 - Calcular Intentos Acumulados
	@Test
	void probarQueAlEnviar5IntentosDevuelve5comoAcumulado() {
		jugador.acumularIntentos(5);
		assertEquals(5,jugador.getIntentosAcumulados());
	}
	@Test
	void probarQueAlEnviar2IntentosMasDevuelve7comoAcumulado() {
		jugador.acumularIntentos(5);
		jugador.acumularIntentos(2);
		assertEquals(7,jugador.getIntentosAcumulados());
	}
	// reset, comienza el contador a 0;
	@Test
	void probarQuePoneEn0elContadorDeIntentosAcumulados() {
		jugador.acumularIntentos(5);
		jugador.acumularIntentos(2);
		jugador.reset();
		assertEquals(0,jugador.getIntentosAcumulados());
	}
	// 2 - Calcular Cantidad de Adivinanzas
	@Test
	void probarQueCuentaCantidadDeIntentos_paso1() {
		jugador.acumularIntentos(5);
		assertEquals(1,jugador.getIntentos());
	}
	@Test
	void probarQueCuentaCantidadDeIntentos_paso2() {
		jugador.acumularIntentos(5);
		jugador.acumularIntentos(2);
		assertEquals(2,jugador.getIntentos());
	}
	@Test
	void probarQuePoneEn0elContadorDeIntentos() {
		jugador.acumularIntentos(5);
		jugador.acumularIntentos(2);
		jugador.reset();
		assertEquals(0,jugador.getIntentos());
	}
}
