package logica;

import GUI.AyudanteGrafico;

import javax.swing.*;
import java.io.File;
import java.net.URL;

public class AyudanteGraficoCelda implements AyudanteGrafico {

    private ImageIcon imagen;

    /**
     * Inicializa un ayudante gráfico con el numero pasado por parametro.
     * @param numCelda numero a ser representado.
     */
    public AyudanteGraficoCelda(int numCelda){
        this.imagen = new ImageIcon(this.getClass().getResource("/img/celdas/0" + numCelda + ".png"));
    }

    /**
     * Actualiza el numero a ser representado graficamente.
     * @param num nuevo a ser representado graficamente
     */
    public void actualizar(int num){
        if(num <= 9){
            ImageIcon nuevoGrafico = new ImageIcon(this.getClass().getResource("/img/celdas/0" + num + ".png"));
            this.imagen.setImage(nuevoGrafico.getImage());
        }
    }

    /**
     * @return el ícono de tipo ImageIcon asociado al ayudante gráfico correspondiente.
     */
    public ImageIcon getImagen(){
        return imagen;
    }
}
