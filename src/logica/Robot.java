package logica;

import java.util.ArrayList;

import cu.edu.cujae.ceis.graph.vertex.Vertex;

public class Robot {
	private ArrayList<Paso> pasos;
	private Vertex verticeActual;
	private boolean carreraTerminada;
	
	
	public Robot(Vertex verticeActual, boolean carreraTerminada) {
		this.pasos = new ArrayList<Paso>();
		this.verticeActual = verticeActual;
		this.carreraTerminada = carreraTerminada;
	}
	
	public ArrayList<Paso> getPasos(){
		return pasos;
	}

	public void setPasos(ArrayList<Paso> pasos) {
		this.pasos = pasos;
	}

	public Vertex getVerticeActual() {
		return verticeActual;
	}

	public void setVerticeActual(Vertex verticeActual) {
		this.verticeActual = verticeActual;
	}

	public boolean isCarreraTerminada() {
		return carreraTerminada;
	}

	public void setCarreraTerminada(boolean carreraTerminada) {
		this.carreraTerminada = carreraTerminada;
	}
	
	public int cantPasos() {
		return pasos.size();
	}
	
}
