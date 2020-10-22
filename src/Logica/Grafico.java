package Logica;

import javax.swing.*;

public class Grafico {

    private ImageIcon imagen;

    public Grafico(int numCelda){
        this.imagen = new ImageIcon(this.getClass().getResource("/img/celdas/0" + numCelda + ".png"));
    }

    public void actualizar(int num){
        if(num <= 9){
            ImageIcon nuevoGrafico = new ImageIcon(this.getClass().getResource("/img/celdas/0" + num + ".png"));
            this.imagen.setImage(nuevoGrafico.getImage());
        }
    }

    public ImageIcon getImagen(){
        return imagen;
    }
}
