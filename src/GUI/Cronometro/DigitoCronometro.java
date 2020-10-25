package GUI.Cronometro;

import javax.swing.*;
import java.awt.*;

/**
 * Clase DigitoCronometro. Define un dígito gráfico que será parte
 * de un cronómetro. Es un JLabel con un componenete gráfico.
 */
public class DigitoCronometro extends JLabel {

    private int digitoRepresentado;
    private AyudanteGraficoCronometro img;

    /**
     * Inicializa un Digito gráfico representando al numero
     * pasado por parametro.
     * @param digito inicial a ser representado gráficamente
     *               por el DigitoCronometro. Se requiere positivo.
     */
    public DigitoCronometro(int digito){
        super();
        setSize(new Dimension(50, 50));

        this.digitoRepresentado = digito;
        img = new AyudanteGraficoCronometro(digito);

        redimensionar();

        setIcon(img.getImagen());
    }

    public AyudanteGraficoCronometro getGrafico(){
        return img;
    }

    /**
     * Cambia el tamaño del gráfico de la celda para acomodarse con la ventana.
     */
    public void redimensionar(){
        Image imagen = img.getImagen().getImage();
        if(imagen != null){
            Image newImg = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
            img.getImagen().setImage(newImg);
            repaint();
        }
    }

    /**
     * Actualiza el digito representado graficamente.
     * @param digito nuevo a ser representado por el digito grafico
     */
    public void actualizar(int digito){
        this.digitoRepresentado = digito;
        img.actualizar(digitoRepresentado);
        repaint();
    }

}
