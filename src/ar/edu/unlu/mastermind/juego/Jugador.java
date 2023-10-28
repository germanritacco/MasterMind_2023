package ar.edu.unlu.mastermind.juego;

public class Jugador {
	private String nombre;
	private int intentos;
	private int cantIntentos;

	public Jugador(String nombre) {
		this.nombre = nombre;
		this.reset();
	}

	public String getNombre() {
		return nombre;
	}

	public void acumularIntentos(int intentos) {
		this.intentos += intentos;
		cantIntentos ++;
		
	}

	public int getIntentosAcumulados() {
		
		return intentos;
	}

	public void reset() {
		intentos = 0;
		cantIntentos = 0;
		
	}

	public int getIntentos() {
		return cantIntentos;
	}

}
