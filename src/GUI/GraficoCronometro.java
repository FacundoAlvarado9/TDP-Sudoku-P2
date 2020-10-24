package GUI;

import javax.swing.*;

public class GraficoCronometro {

    private ImageIcon imagen;

    public GraficoCronometro(int digito){
        this.imagen = new ImageIcon(this.getClass().getResource("/img/cronometro/0" + digito + ".png"));
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
