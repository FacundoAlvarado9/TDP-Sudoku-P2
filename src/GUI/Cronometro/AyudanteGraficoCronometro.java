package GUI.Cronometro;

import javax.swing.*;
import java.io.File;
import java.net.URL;

public class AyudanteGraficoCronometro {

    private ImageIcon imagen;

    /**
     * Inicializa un grafico de digito de cronómetro con el dígito pasado como parametro.
     * @param digito a colocar en el grafico del digito de cronómetro. Requiere ser menor a 9.
     */
    public AyudanteGraficoCronometro(int digito){
        this.imagen = new ImageIcon(this.getClass().getResource("/img/cronometro/0" + digito + ".png"));
    }

    /**
     * Actualiza el grafico del digito de cronómetro con el nuevo dígito pasado como parametro.
     * @param digito a colocar en el grafico del digito de cronómetro. Requiere ser menor a 9.
     */
    public void actualizar(int digito){
            ImageIcon nuevoGrafico = new ImageIcon(this.getClass().getResource("/img/cronometro/0" + digito + ".png"));
            this.imagen.setImage(nuevoGrafico.getImage());
    }

    public ImageIcon getImagen(){
        return imagen;
    }

}
