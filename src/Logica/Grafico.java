package Logica;

import javax.swing.*;

public class Grafico {

    private ImageIcon grafico;
    private String[] imgs;

    public Grafico(int numCelda){
        this.grafico = new ImageIcon(this.getClass().getResource("/img/0" + numCelda + ".png"));
        System.out.println("/img/0" + numCelda + ".png");
    }

    public void actualizar(int num){
        if(num <= 9){
            ImageIcon nuevoGrafico = new ImageIcon(this.getClass().getResource("/img/0" + num + ".png"));
            this.grafico.setImage(nuevoGrafico.getImage());
        }
    }

    public ImageIcon getGrafico(){
        return grafico;
    }
}
