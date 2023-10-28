package ar.edu.unlu.mastermind.juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MasterMind {
	private List<Jugador> misJugadores;
	private Tablero miTablero;
	private int puntosMaximos;

	public static void main(String[] args) throws Exception {
		MasterMind juego = new MasterMind();
		juego.comenzarJuego();
	}

	private void comenzarJuego() throws Exception {
		int opcion = 1;
		while (opcion != 0) {
			opcion = mostrarMenu();
				switch (opcion) {
				case 1:
					// Configurar intententos maximos
					puntosMaximos = consultarPuntosMaximos();
					System.out.println("Puntos maximos establecidos en " + puntosMaximos);
					esperarEnter();
					break;
				case 2: 
					// Agregar Jugador
					misJugadores.add(new Jugador(agregarJugador()));
					break;
				case 3:
					// mostrar lista de Jugadores
					mostrarJugadores();
					break;
				case 4:
					//Comenzar Juego
					if (misJugadores.size() > 0)
						jugar();
					else
						System.out.println("Debe agregar, al menos, un jugador primero ... ");
					esperarEnter();
					break;
				}
		}

	}

     //////////----------------------- Metodos de Juego ------------------------------------- ////////

	private void jugar() throws Exception {
		int jugadorActual = 0;
		FichaCodificadora[] codigo = new FichaCodificadora[5];
		FichaResultado[] respuesta = new FichaResultado[5];
		while (jugadorActual < misJugadores.size()) {
			miTablero.inicializar();
			boolean acerto = false;
			while (!acerto) {
				Clave claveAProbar = pedirClave(codigo,respuesta);
				for (int i = 1; i <= 5; i++)
					codigo[i-1] = claveAProbar.getColor(i);
				miTablero.agregarClave(claveAProbar);
				respuesta = miTablero.getRespuesta();
				acerto = comprobarRespuesta(respuesta);
				if (acerto) {
					System.out.printf("Tu combinación elegida fue correcta!!!!, lo lograste en %d intentos. \n",miTablero.getIntentos());
					misJugadores.get(jugadorActual).acumularIntentos(miTablero.getIntentos());
				} else {
					System.out.printf("Dada tu combianción, la respuesta devuelve %s %s %s %s %s \n", 
							respuesta[0],respuesta[1],respuesta[2],respuesta[3],respuesta[4]);
				}
				esperarEnter();
			}
			// Control de Fin de Ronda
			jugadorActual ++;
			if (jugadorActual == misJugadores.size()) {
				if (hayPerdedores()) {
					System.out.println("El juego terminó!!");
					System.out.println("El o los ganadores fueron " + buscarGanadores());
				} else {
					jugadorActual = 0;
				}
			}
		}
	}
	
	
	// - PEDIR CLAVE //

	private Clave pedirClave(FichaCodificadora[] codigoAnterior, FichaResultado[] respuesta) throws Exception {
	       Clave clave = new Clave();
	        for (int i = 1; i  <= 5 ; i ++)
	            clave.setColor(codigoAnterior[i - 1],i);
	        int opcion = -1;
	        while (opcion != 3) {
	            System.out.println("------------------------------------------------------------------------------");
	            System.out.println("--                        Elijamos una Clave                               --");
	            System.out.println("------------------------------------------------------------------------------");
	            System.out.println("-- Tu clave hasta ahora está asi :                                      --");
	            System.out.println("------------------------------------------------------------------------------");
	            System.out.printf("-- %s \t %s \t %s \t %s \t  %s --\n",color(clave,1),color(clave,2),color(clave,3),color(clave,4),color(clave,5));
	            System.out.println("------------------------------------------------------------------------------");
	            System.out.println("-- La ultima evaluación resulto en :                                  --");
	            System.out.printf("-- %s \t %s \t %s \t %s \t  %s --\n",color(respuesta,1),color(respuesta,2),color(respuesta,3),color(respuesta,4),color(respuesta,5));
	            System.out.println("------------------------------------------------------------------------------");
	            System.out.println("--                                Opciones                                      --");
	            System.out.println("------------------------------------------------------------------------------");
	            System.out.println("-- 1 - Elegir / Cambiar un color                                        --");
	            System.out.println("--                                                                                     --");
	            System.out.println("------------------------------------------------------------------------------");
	            System.out.println("-- 3 - Aceptar la combinacion                                          --");
	            System.out.println("------------------------------------------------------------------------------");
	            Scanner sc = new Scanner(System.in);
	            opcion = sc.nextInt();
	            if (opcion == 1) {
	                ponerColor(clave);
	            } else if (!claveCompleta(clave)) {
	                System.out.println("Complete la clave por favor antes de aceptarla");
	                esperarEnter();
	                opcion = 1;
	            }
	        }
	        return clave;
	}
	
    private void ponerColor(Clave clave) throws Exception {
        // TODO Auto-generated method stub
        FichaCodificadora f;
        int color = -1;
        while (color <= 0 || color > FichaCodificadora.values().length) {
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("--                        Elija el Color                                          --");
            System.out.println("------------------------------------------------------------------------------");
            for(int i = 0;i < FichaCodificadora.values().length; i++)
                System.out.printf("-- %d - %s \n", i + 1,FichaCodificadora.values()[i].toString());

            System.out.println("------------------------------------------------------------------------------");
            Scanner sc = new Scanner(System.in);
            color = sc.nextInt();
        }
        int posicion = 0;
        while (posicion < 1 || posicion > 5) {
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("--                        Elija la Posicion                                      --");
            System.out.println("------------------------------------------------------------------------------");
            for(int i = 1;i <= 5; i++)
                System.out.printf("-- %d - %s \n", i,i);
            System.out.println("------------------------------------------------------------------------------");
            Scanner sc = new Scanner(System.in);
            posicion = sc.nextInt();
        }
        clave.setColor(FichaCodificadora.values()[color - 1],posicion );
    }
    
    
    private String color(Clave clave,int posicion) throws Exception {
        FichaCodificadora f = clave.getColor(posicion);
        if (f == null) {
            return "VACIA";
        } else {
            return f.toString();
        }
    }

    private String color(FichaResultado[] ficha,int posicion) {
        if(ficha != null && ficha[posicion - 1] != null && posicion > 0 && posicion <= ficha.length)
            return ficha[posicion - 1].toString();
        else
            return "VACIA";
    }

    
    private boolean claveCompleta(Clave clave) throws Exception {
        boolean acerto = true;
        for(int i = 1; i <= 5; i++)
            acerto &= (clave.getColor(i) != null);
        return acerto;
    }
    
    // - ----------------------------------
    
	private String buscarGanadores() {
        int minimo = puntosMaximos * 5;
        String ganador = "";
        for(Jugador j : misJugadores) {
            if(j.getIntentosAcumulados() < minimo) {
                minimo = j.getIntentosAcumulados();
                ganador = j.getNombre();
            } else if (j.getIntentosAcumulados() == minimo) {
                ganador += " | " + j.getNombre();
            }
        }
        return ganador;
	}

	private boolean hayPerdedores() {
        boolean hayPerdedor = false;
        for (Jugador j : misJugadores)
            hayPerdedor |= (j.getIntentosAcumulados() >= puntosMaximos);
        return hayPerdedor;
	}

	private boolean comprobarRespuesta(FichaResultado[] respuesta) {
        boolean acerto = true;
        for(FichaResultado f : respuesta)
            acerto &= (f == FichaResultado.BUENA);
        return acerto;
	}

	//////////// ----------------------- Metodos de Juego ------------------------------------- ////////
	
	private void mostrarJugadores() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("--                            Lista de Jugadores                             --");
		System.out.println("------------------------------------------------------------------------------");
		for(Jugador j : misJugadores)
			System.out.printf("Nombre : %s  \t Intentos Acumuladores : %d, en %d Intentos\n",j.getNombre(),j.getIntentosAcumulados(), j.getIntentos() );
		esperarEnter();
	}

	private int mostrarMenu() {
		int opcion = -1;
		while (opcion < 0 || opcion > 4) {
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                              MASTER MIND                                 --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                el juego que esconde un secreto                  --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                        Menú de Configuración                         --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                                Opciones                                      --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("-- 1 - Establecer cantidad de intentos de claves              --");
			System.out.println("-- 2 - Agregar Jugador                                                     --");
			System.out.println("-- 3 - Mostrar Lista de Jugadores                                      --");
			System.out.println("-- 4 - Comenzar Partida                                                   --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("-- 0 - Salir del Juego                                                        --");
			System.out.println("------------------------------------------------------------------------------");
			Scanner sc = new Scanner(System.in);
			opcion = sc.nextInt();
		}
		return opcion;
	}
	
	private int consultarPuntosMaximos() {
		int puntos = -1;
		while (puntos < 10 || puntos > 30) {
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                              MASTER MIND                                 --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                el juego que esconde un secreto                  --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                        Establecer Puntos Maximos                   --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.print("-- Puntos Maximos : ");
			Scanner sc = new Scanner(System.in);
			puntos = sc.nextInt();
		}
		return puntos;
	}
	
	private void esperarEnter() {
		System.out.println("Presione ENTER para continuar...");
		Scanner sc = new Scanner(System.in);
		String pausa = sc.nextLine();
	};
	

	private String agregarJugador() {
		String nombre = "";
		while (nombre.equals("")) {
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("--                          Agregando Jugador                             --");
			System.out.println("------------------------------------------------------------------------------");
			System.out.print("-- Nombre : ");
			Scanner sc = new Scanner(System.in);
			nombre = sc.nextLine();
		}
		return nombre;
	}
	
	public MasterMind() {
		misJugadores = new ArrayList<Jugador>();
		miTablero = new Tablero();
		puntosMaximos = 20;
	}

}
