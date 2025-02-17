package logica;

import java.util.ArrayList;

public class Casilla {
	private boolean activa;
	private ArrayList<Robot> robots;
	private int tipoCasilla; // 0 salida, 1 llegada, 2 camino
	private int fila;
	private int columna;

	public Casilla(boolean activa, ArrayList<Robot> robots, int tipoCasilla, int fila, int columna) {
		super();
		this.activa = activa;
		this.robots = robots;
		setFila(fila);
		setColumna(columna);
		setTipoCasilla(tipoCasilla);
	}
	public int getTipoCasilla() {
		return tipoCasilla;
	}
	public void setTipoCasilla(int tipoCasilla) {
		if(tipoCasilla == 0 || tipoCasilla == 1 || tipoCasilla == 2) {
			this.tipoCasilla = tipoCasilla;
		}

	}
	public boolean isActiva() {
		return activa;
	}
	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	public ArrayList<Robot> getRobots() {
		return robots;
	}
	public void setRobots(ArrayList<Robot> robots) {
		this.robots = robots;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		if(fila >= 0) {
			this.fila = fila;
		}
		else {
			throw new IllegalArgumentException("El valor de la fila debe de ser mayor o igual a cero ");
		}

	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		if(columna >= 0) {
			this.columna = columna;
		}
		else {
			throw new IllegalArgumentException("El valor de la columna debe de ser mayor o igual a cero ");
		}

	}

}
