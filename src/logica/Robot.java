package logica;

import java.util.ArrayList;

import cu.edu.cujae.ceis.graph.vertex.Vertex;

public class Robot {
	private ArrayList<Paso> pasos;
	private Vertex verticeActual;
	private boolean carreraTerminada;
	private int distanciaLlegada;
	private int pasosEmpleados;
	
	
	public Robot(Vertex verticeActual, boolean carreraTerminada, ArrayList<Paso> pasos, int distanciaLlegada, int pasosEmpleados) {
		setPasos(pasos);
		this.verticeActual = verticeActual;
		this.carreraTerminada = carreraTerminada;
		setDistanciaLlegada(distanciaLlegada);
		setPasosEmpleados(pasosEmpleados);
	}
	
	private void setPasosEmpleados(int pasosEmpleados) {
		if(pasosEmpleados >= 0) {
			this.pasosEmpleados = pasosEmpleados;
			}
			else {
				throw new IllegalArgumentException("Los pasos empleados no deben de ser menor a cero");
			}
		
	}

	public int getPasosEmpleados() {
		return pasosEmpleados;
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
	
	public int getDistanciaLlegada() {
		return distanciaLlegada;
	}

	public void setDistanciaLlegada(int distanciaLlegada) {
		if(distanciaLlegada >= 0) {
		this.distanciaLlegada = distanciaLlegada;
		}
		else {
			throw new IllegalArgumentException("La distancia no debe de ser menor a cero");
		}
	}

	public int cantPasos() {
		return pasos.size();
	}
	
}
