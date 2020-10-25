package GUI.Cronometro;

import javax.swing.*;

/**
 * Clase DobleDigitoCronometro. Define un par de dígitos gráficos
 * a ser utilizados en un cronómetro.
 */
public class DobleDigitoCronometro extends JPanel {

    int digitoRepresentado;

    private DigitoCronometro digito1;
    private DigitoCronometro digito2;

    /**
     * Inicializa un DobleDigitoCronometro que representa al dígito pasado como
     * parámetro.
     * @param digito digito a ser representado por el doble dígito
     */
    public DobleDigitoCronometro(int digito){
        super();
        this.digitoRepresentado = digito;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        digito1 = new DigitoCronometro(digito/10);
        digito2 = new DigitoCronometro(digito%10);

        add(digito1);
        add(digito2);

    }

    /**
     * Actualiza el numero a ser representado por el doble dígito.
     * @param digito a ser representado por el Doble dígito gráfico.
     */
    public void actualizarDigito(int digito){
        this.digitoRepresentado = digito;
            digito1.actualizar(digito/10);
            digito2.actualizar(digito%10);
    }

    public void redimensionar(){
        digito1.redimensionar();
        digito2.redimensionar();
    }

    public DigitoCronometro getD1(){
        return digito1;
    }

    public DigitoCronometro getD2(){
        return digito2;
    }

}
