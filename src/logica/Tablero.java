package logica;

import cu.edu.cujae.ceis.graph.interfaces.ILinkedNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import java.util.LinkedList;
import cu.edu.cujae.ceis.graph.LinkedGraph;

public class Tablero {
	private ILinkedNotDirectedGraph tableroGraf;
	private int filas;
	private int columnas;
	private LinkedList<Vertex> vertices;
	Vertex[][] tablero;

	public Tablero(int filas, int columnas) {
		this.tableroGraf= new LinkedGraph();
		setFilas(filas);
		setColumnas(columnas);
		this.vertices = new LinkedList<>();
		tablero = new Vertex[filas][columnas];
	}

	// Crear las casillas del tablero (Nodos)
	public void crearVertices() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				Vertex vertice = new Vertex("Posición(" + i + "," + j + ")");
				tablero[i][j] = vertice;
				vertices.add(vertice);
				tableroGraf.insertVertex(vertice);
			}
		}
	}

	// Crear las conexiones (Aristas)
	public void crearAristas() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				int currentIndex = i * columnas + j;
				if (i < filas - 1) {
					int belowIndex = (i + 1) * columnas + j;
					tableroGraf.insertEdgeNDG(currentIndex, belowIndex);
				}
				if (j < columnas - 1) {
					int rightIndex = i * columnas + (j + 1);
					tableroGraf.insertEdgeNDG(currentIndex, rightIndex);
				}
			}
		}
	}

	public ILinkedNotDirectedGraph getGrafo() {
		return tableroGraf;
	}

	public int getFilas() {
		return filas;
	}
	public void setFilas(int fila) {
		if(fila >= 3 && fila <= 10) {
			this.filas = fila;
		}
		else {
			throw new IllegalArgumentException("Valores erróneos");
		}
	}

	public int getColumnas() {
		return columnas;
	}
	public void setColumnas(int columna) {
		if(columna >= 3 && columna <= 10) {
			this.columnas = columna;
		}
		else {
			throw new IllegalArgumentException("Valores erróneos");
		}
	}
}


