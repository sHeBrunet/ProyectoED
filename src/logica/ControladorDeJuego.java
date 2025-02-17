package logica;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import cu.edu.cujae.ceis.graph.interfaces.ILinkedNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

public class ControladorDeJuego {
	private Tablero tableroGraf;
	private ArrayList<Robot> familiaRobots;


	public ControladorDeJuego(Tablero tableroGraf, ArrayList<Robot> familiaRobots) {
		super();
		this.tableroGraf = tableroGraf;
		this.familiaRobots = familiaRobots;
	}
	//Funcion Para Asignar los pasos a la familia de robots
	public void generarListaDePasos() {
		ArrayList<Paso> pasos = new ArrayList<Paso>();
		Random r = new Random();
		int cantPasos = cantPasos(tableroGraf.getFilas(),tableroGraf.getColumnas());
		for(int i = 0; i < familiaRobots.size(); i++) {
			pasos.clear();
			for(int j = 0; j < cantPasos; j++) {
				int pos = r.nextInt(4);	
				pasos.add(designarMov(pos));
			}
			familiaRobots.get(i).setPasos(pasos);
		}
	}
	public int cantPasos(int m, int n) {
		return (Integer) m * n / 2;
	}
	//Funcion Auxiliar para corresponder el numero con un determinado paso
	public Paso designarMov(int pos) {
		Paso p = null;
		if(pos == 0 || pos == 1 || pos == 2 || pos == 3) {
			switch (pos) {
			case 0:
				p = Paso.Izquierda;
				break;
			case 1:
				p = Paso.Derecha;
				break;
			case 2:
				p = Paso.Arriba;
				break;
			case 3:
				p = Paso.Abajo;
				break;
			default:
				break;
			}
		}
		return p;
	}
	//Determinar 4 mejores y 4 perores robots
	 public static ArrayList<ArrayList<Robot>> determinarMejoresPeores(ArrayList<Robot> robots) {
	        ArrayList<Robot> mejores = new ArrayList<>();
	        ArrayList<Robot> peores = new ArrayList<>();

	        for (Robot robot : robots) {
	            if (mejores.size() < 4) {
	                mejores.add(robot);
	            } else {
	                int peorIdx = 0;
	                for (int i = 1; i < mejores.size(); i++) {
	                    if (getKey(mejores.get(i)) > getKey(mejores.get(peorIdx))) {
	                        peorIdx = i;
	                    }
	                }
	                if (getKey(robot) < getKey(mejores.get(peorIdx))) {
	                    mejores.set(peorIdx, robot);
	                }
	            }

	            if (peores.size() < 4) {
	                peores.add(robot);
	            } else {
	                int mejorIdx = 0;
	                for (int i = 1; i < peores.size(); i++) {
	                    if (getKey(peores.get(i)) < getKey(peores.get(mejorIdx))) {
	                        mejorIdx = i;
	                    }
	                }
	                if (getKey(robot) > getKey(peores.get(mejorIdx))) {
	                    peores.set(mejorIdx, robot);
	                }
	            }
	        }

	        ArrayList<ArrayList<Robot>> resultado = new ArrayList<>();
	        resultado.add(mejores);
	        resultado.add(peores);
	        return resultado;
	    }

	    private static int getKey(Robot robot) {
	        if (robot.isCarreraTerminada()) {
	            return robot.getPasosEmpleados();
	        } else {
	            return Integer.MAX_VALUE - robot.getDistanciaLlegada();
	        }
	    }// Para que los robots que no alcanzaron la meta tengan una clave más alta
	        

	//Funcion para determinar el camino más rápido desde la salida hasta la meta
	public LinkedList<Vertex> determinarCaminoMasRapido(Vertex llegada, Vertex salida, LinkedList<Vertex> verticesVisitados) {
		LinkedList<Vertex> camino = new LinkedList<>();
		verticesVisitados.add(salida);

		if (buscarCaminoRecursivo(llegada, salida, camino, verticesVisitados)) {
			return camino;
		}

		return null;
	}

	private boolean buscarCaminoRecursivo(Vertex llegada, Vertex actual, LinkedList<Vertex> camino, LinkedList<Vertex> verticesVisitados) {
		camino.add(actual);

		if (actual.equals(llegada)) {
			return true;
		}

		for (Vertex ady : actual.getAdjacents()) {
			if (!verticesVisitados.contains(ady) && ((Casilla) ady.getInfo()).isActiva()) {
				verticesVisitados.add(ady);
				if (buscarCaminoRecursivo(llegada, ady, camino, verticesVisitados)) {
					return true;
				}
				verticesVisitados.remove(ady);
			}
		}

		camino.removeLast(); 
		return false;
	}
	public LinkedList<Vertex> caminoOptimo() {
		LinkedList<Vertex> camino = new LinkedList<Vertex>();
		LinkedList<Vertex> verticesVisitados  = new LinkedList<Vertex>();
		Vertex llegada  = tableroGraf.buscarLlegada();
		Vertex salida = tableroGraf.buscarLlegada();
		if(tableroGraf.existiraCamino()) {
			camino = determinarCaminoMasRapido(llegada, salida, verticesVisitados);
		}
		else {
			System.out.println("No se puede determinar el camino mas óptimo ya que no existe camino entre el punto de salida y llegada");
		}
		return camino != null? camino : null;
	}

	//Funcion para crear 4 nuevos Robots
	public void crearRobots(ArrayList<Robot> robotsMejores, ArrayList<Robot> robotsPeores, Vertex salida) {
		for(int i = 0; i < 4; i++) {
			if(i+1 != 4) {
				robotsPeores.get(i).setPasos(fusionarListas(robotsMejores.get(i), robotsMejores.get(i + 1)));
			}
			else {
				robotsPeores.get(i).setPasos(fusionarListas(robotsMejores.get(3), robotsPeores.get(0)));
			}
		}
	}
	//Funcion para fusionar las lista de pasos de los 4 mejores robots
	public ArrayList<Paso> fusionarListas(Robot R1, Robot R2){
		ArrayList<Paso> pasos = new ArrayList<Paso>();
		Random r = new Random();
		int p = -1;
		for(int i = 0; i < R1.cantPasos(); i++) {
			p = r.nextInt(2);
			switch (p) {
			case 0:
				pasos.add(R1.getPasos().get(i));
				break;
			case 1:
				pasos.add(R2.getPasos().get(i));
				break;
			default:
				break;

			}
		}
		return pasos;
	}
	public static void main(String[] args) {
		// Crear un tablero de ejemplo con dimensiones deseadas
		int filas = 3; // Puedes cambiar este valor entre 3 y 10
		int columnas = 3; // Puedes cambiar este valor entre 3 y 10

		// Instanciar el tablero
		Tablero tablero = new Tablero(filas, columnas);

		// Imprimir el tablero para visualizar las casillas
		imprimirTablero(tablero);

		// Verificar si existe un camino desde la salida hasta la meta
		try {
			boolean hayCamino = tablero.existiraCamino();
			if (hayCamino) {
				System.out.println("Existe al menos un camino desde la salida hasta la meta.");
			} else {
				System.out.println("No existe un camino desde la salida hasta la meta.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		ControladorDeJuego c = new ControladorDeJuego(tablero, null);
		LinkedList<Vertex> camino = c.caminoOptimo();

		// Mostrar el camino
		if (camino != null && !camino.isEmpty()) {
			System.out.println("Camino más rápido:");
			for (Vertex v : camino) {
				System.out.print("(" + Arrays.toString(encontrarPosicion(tablero, v)) + ") ");
			}
			System.out.println(); // Nueva línea después del camino
		} else {
			System.out.println("No se encontró un camino.");
		}
	}

	// Método auxiliar para encontrar la posición de un vértice en el tablero
	private static int[] encontrarPosicion(Tablero tablero, Vertex vertex) {
		Casilla[][] casillas = tablero.getTableroCasillas();
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				if (((Casilla) casillas[i][j]).equals(vertex.getInfo())) {
					return new int[]{i, j};
				}
			}
		}
		return null;
	}

	// Método para imprimir el tablero y visualizar las casillas
	public static void imprimirTablero(Tablero tablero) {
		int cant = tablero.getColumnas() - 1;
		int l;
		Casilla[][] arr = tablero.getTableroCasillas();
		for (int i = 0; i < tablero.getFilas(); i++) {
			for (int j = 0; j < tablero.getColumnas(); j++) {
				l = arr[i][j].getTipoCasilla();
				System.out.print(l + " " + arr[i][j].isActiva() + " ");
				if (j != cant) {
					System.out.print(" ");
				} else {
					System.out.println();
				}
			}
		}
	}
}
