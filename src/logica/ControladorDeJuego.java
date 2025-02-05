package logica;

import java.util.ArrayList;
import java.util.Random;

import cu.edu.cujae.ceis.graph.vertex.Vertex;

public class ControladorDeJuego {
	private Tablero tableroGraf;
	private ArrayList<Robot> familiaRobots;
	

	//Funcion Para Asignar los pasos a la familia de robots
	public void generarListaDePasos(int m, int n) {
		ArrayList<Paso> pasos = new ArrayList<Paso>();
		Random r = new Random();
		int cantPasos = cantPasos(m, n);
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
	//Seleccionar los 4 mejores Robots




	//Seleccionar los 4 peores Robots
	

	//Funcion para crear 4 nuevos Robots
	public void crearRobots(ArrayList<Robot> robotsMejores, ArrayList<Robot> robotsPeores, Vertex salida) {
		familiaRobots.removeAll(robotsPeores);
		for(int i = 0; i < 4; i++) {
			if(i+1 != 4) {
			Robot r = new Robot(salida, false);
			r.setPasos(fusionarListas(robotsMejores.get(i), robotsPeores.get(i + 1)));
			familiaRobots.add(r);
			}
			else {
				Robot r = new Robot(salida, false);
				r.setPasos(fusionarListas(robotsMejores.get(3), robotsPeores.get(0)));
				familiaRobots.add(r);
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

}
