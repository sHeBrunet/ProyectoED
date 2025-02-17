package logica;

import cu.edu.cujae.ceis.graph.interfaces.ILinkedNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import cu.edu.cujae.ceis.graph.LinkedGraph;

public class Tablero {
	private ILinkedNotDirectedGraph tableroGraf;
	private int filas;
	private int columnas;
	private Casilla[][] tableroCasillas;


	public Tablero(int filas, int columnas) {
		this.tableroGraf = new LinkedGraph();
		setFilas(filas);
		setColumnas(columnas);
		this.tableroCasillas = new Casilla[getFilas()][getColumnas()];
		crearTablero();
	}

	//Creacion del tablero
	public void crearTablero() {
		crearCasillas();
		verticesActividad();
		verticesInactivosRandom();
		setInicio();
		setMeta();
		vincularCasillasAlGrafo();
	}

	// Funcion para crear las casillas del tablero (En el Arreglo Bidimensional)
	public void crearCasillas() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				Casilla c = new Casilla(true, null, 2, i, j);
				tableroCasillas[i][j] = c;
			}
		}
	}


	//Funcion para crear casillas inactivas (bordes)
	public void verticesActividad() {
		int cantF = filas-1;
		int cantC = columnas-1;
		for(int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				if(i == 0 || j == 0 || i == cantF || j == cantC) {
					tableroCasillas[i][j].setActiva(false);
				}
			}
		}
	}
	//Funcion para crear vertices inactivos (aleatoriamente)
	public void verticesInactivosRandom() {
		Random r = new Random();
		int cantInactivos = r.nextInt((Integer)(filas -2) * (columnas -2)/4);
		int fila = -1;
		int columna = -1;
		int i = 0;
		while(cantInactivos > i) {
			boolean actividad = false;
			while(!actividad) {
				fila = r.nextInt(filas);
				columna = r.nextInt(columnas);
				if(tableroCasillas[fila][columna].isActiva()) {
					if(tableroCasillas[fila][columna].getTipoCasilla() != 0 || tableroCasillas[fila][columna].getTipoCasilla() != 1) {
						tableroCasillas[fila][columna].setActiva(false);
						i++;
						actividad = true;
					}
				}
			}
		}
	}
	//Funcion para determinar el inicio del tablero
	public void setInicio() {
		boolean inicio = false;
		Random r = new Random();
		int i = -1;
		int j = -1;
		while(!inicio) {
			i = r.nextInt(filas);
			j = r.nextInt(columnas);
			if(tableroCasillas[i][j].isActiva()){
				inicio = true;
				tableroCasillas[i][j].setTipoCasilla(0);
			}
		}
	}
	//Funcion para determinar el final del tablero
	public void setMeta() {
		boolean llegada = false;
		Random r = new Random();
		int i = -1;
		int j = -1;
		while(!llegada) {
			i = r.nextInt(filas);
			j = r.nextInt(columnas);
			if(tableroCasillas[i][j].isActiva() && tableroCasillas[i][j].getTipoCasilla() != 0){
				llegada= true;
				tableroCasillas[i][j].setTipoCasilla(1);
			}
		}
	}
	//Algoritmo para vincular las casillas con los nodos y crear las conexiones (aristas)
	public void vincularCasillasAlGrafo() {
		int indiceAct = -1;
		int indiceDcha = -1;
		int indiceAbj = -1;
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				Casilla actual = tableroCasillas[i][j];
				if (!casillaExisteEnGrafo(actual)) {
					tableroGraf.insertVertex(actual);
				}
				if(j < columnas - 1 || i < filas - 1) {
					indiceAct = obtenerIndiceVertice(actual);
				}
				// Conexion a la derecha
				if (j < columnas - 1) {
					Casilla derecha = tableroCasillas[i][j + 1];
					if (!casillaExisteEnGrafo(derecha)) {
						tableroGraf.insertVertex(derecha);
					}
					indiceDcha = obtenerIndiceVertice(derecha);
					if (indiceAct != -1 && indiceDcha != -1 && !tableroGraf.areAdjacents(indiceAct, indiceDcha)) {
						tableroGraf.insertEdgeNDG(indiceAct, indiceDcha);
					}
				}
				// Conexion hacia abajo
				if (i < filas - 1) {
					Casilla abajo = tableroCasillas[i + 1][j];
					if (!casillaExisteEnGrafo(abajo)) {
						tableroGraf.insertVertex(abajo);
					}
					indiceAbj = obtenerIndiceVertice(abajo);
					if (indiceAct != -1 && indiceAbj != -1 && !tableroGraf.areAdjacents(indiceAct, indiceAbj)) {
						tableroGraf.insertEdgeNDG(indiceAct, indiceAbj);
					}
				}
			}
		}
	}
	// Verificar la existencia de la Casilla en el grafo
	private boolean casillaExisteEnGrafo(Casilla casilla) {
		Iterator <Vertex> it = tableroGraf.getVerticesList().iterator();
		boolean encont = false;
		while (it.hasNext() && !encont) {
			if(it.next().getInfo().equals(casilla)) {
				encont = true;
			}
		}
		return encont;
	}

	// Obtener el índice del vértice asociado
	private int obtenerIndiceVertice(Casilla casilla) {
		LinkedList<Vertex> vertices = tableroGraf.getVerticesList();
		Iterator<Vertex> it = vertices.iterator();
		int count = 0;
		boolean encont = false;
		while(it.hasNext() && !encont) {
			if (it.next().getInfo().equals(casilla)) {
				encont = true;
			}
			else {
				count++;
			}
		}
		return count != -1? count: -1;
	}

	//Funcion para comprobar que al menos exista un camino desde la salida hasta la llegada
	public boolean existeCamino(Vertex llegada, LinkedList<Vertex> verticesAdyacentes, List<Vertex> verticesVisitados) {
		Vertex v = null;
		Iterator<Vertex> it = verticesAdyacentes.iterator();
		if(cantVerticesActivos() == verticesVisitados.size()) {
			return false;
		}
		while(it.hasNext()){
			v = it.next();
			if(llegada.equals(v)) {
				return true;
			}
			else {
				if(v != null && ((Casilla)v.getInfo()).isActiva()  && !verticesVisitados.contains(v)) {
					verticesVisitados.add(v);
					if(existeCamino(llegada, v.getAdjacents(), verticesVisitados)){
						return true;
					}
				}
			}

		}
		return false;
	}
	//Funcion para saber la cantidad de vertices incativos en el tablero
	public int cantVerticesActivos() {
		int num = ((Integer)(filas - 2)*(columnas -2) - (filas - 2)*(columnas -2)/4);
		return num;
	}
	//Funcion auxiliar para comprobar que existe al menos un camino
	public boolean existiraCamino() {
		boolean hayCamino = false;
		Vertex salida = null;
		Vertex llegada = null;
		List<Vertex> verticesVisitados = new ArrayList<Vertex>();
		List<Vertex> salidaLlegadaV = buscarExistenciaDeSalidaLlegada();
		if(salidaLlegadaV.size() == 2) {
			if(((Casilla)salidaLlegadaV.get(0).getInfo()).getTipoCasilla() == 0) {
				salida = salidaLlegadaV.get(0);
				llegada = salidaLlegadaV.get(1);
			}
			else {
				salida = salidaLlegadaV.get(1);
				llegada = salidaLlegadaV.get(0);
			}
			verticesVisitados.add(salida);
			hayCamino = existeCamino(llegada, salida.getAdjacents(), verticesVisitados);
		}
		else {
			throw new IllegalArgumentException("No se encontro ninguna salida definida");
		}
		return hayCamino;

	}
	//Funcion para buscar si existe el vertice de salida y de llegada y devolverlos en una lista
	public List<Vertex> buscarExistenciaDeSalidaLlegada() {
		Iterator<Vertex> it = tableroGraf.getVerticesList().iterator();
		List<Vertex> v = new ArrayList<Vertex>();
		Vertex vertice = null;
		while(v.size() < 2 && it.hasNext()) {
			vertice = it.next();
			if(((Casilla)vertice.getInfo()).getTipoCasilla() == 0 || ((Casilla)vertice.getInfo()).getTipoCasilla() == 1) {
				v.add(vertice);
			}
		}
		return v;
	}
	//Funcion para buscar el vertice de salida
	public Vertex buscarSalida() {
		Iterator<Vertex> it = tableroGraf.getVerticesList().iterator();
		Vertex v = null;
		boolean encont = false;
		while(!encont && it.hasNext()) {
			v = it.next();
			if(((Casilla)v.getInfo()).getTipoCasilla() == 0) {
				encont = true;
			}
		}
		return encont? v: null;
	}
	//Funcion para buscar el vertice de llegada
	public Vertex buscarLlegada() {
		Iterator<Vertex> it = tableroGraf.getVerticesList().iterator();
		Vertex v = null;
		boolean encont = false;
		while(!encont && it.hasNext()) {
			v = it.next();
			if(((Casilla)v.getInfo()).getTipoCasilla() == 1) {
				encont = true;
			}
		}
		return encont? v: null;
	}
	//getters y setters
	public ILinkedNotDirectedGraph getGrafo() {
		return tableroGraf;
	}

	public int getFilas() {
		return filas;
	}
	public void setFilas(int filas) {
		if(filas >= 3 && filas <= 10) {
			this.filas = filas + 2;
		}
		else {
			throw new IllegalArgumentException("Valores erróneos");
		}
	}

	public int getColumnas() {
		return columnas;
	}
	public void setColumnas(int columnas) {
		if(columnas >= 3 && columnas <= 10) {
			this.columnas = columnas + 2;
		}
		else {
			throw new IllegalArgumentException("Valores erróneos");
		}
	}

	public ILinkedNotDirectedGraph getTableroGraf() {
		return tableroGraf;
	}

	public void setTableroGraf(ILinkedNotDirectedGraph tableroGraf) {
		this.tableroGraf = tableroGraf;
	}
	public Casilla[][] getTableroCasillas() {
		return tableroCasillas;
	}

	public void setTableroCasillas(Casilla[][] tableroCasillas) {
		this.tableroCasillas = tableroCasillas;
	}

}

