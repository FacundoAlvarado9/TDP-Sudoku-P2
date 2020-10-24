package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DigitoCronometro extends JLabel {

    private int digitoRepresentado;
    private GraficoCronometro img;

    public DigitoCronometro(int digito){
        super();

        this.digitoRepresentado = digito;
        img = new GraficoCronometro(digito);

        setIcon(img.getImagen());

        System.out.println("Llegue");


    }

    public void redimensionar(){
        Image imagen = img.getImagen().getImage();
        if(imagen != null){
            Image newImg = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
            img.getImagen().setImage(newImg);
            repaint();
        }
    }

    public void actualizar(int digito){
        this.digitoRepresentado = digito;
        img.actualizar(digito);
        repaint();
    }

}
