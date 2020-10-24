package GUI;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DobleDigitoCronometro extends JLabel {

    int digitoRepresentado;

    private JLabel digito1;
    private JLabel digito2;

    public DobleDigitoCronometro(int digito){
        super();
        this.digitoRepresentado = digito;

        //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        /*
        digito1 = new DigitoCronometro(digito/10);
        digito2 = new DigitoCronometro(digito%10);
         */

        digito1 = new JLabel("0");
        digito2 = new JLabel("1");

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //digito1.redimensionar();
                //digito2.redimensionar();
            }
        });

        add(digito1);
        add(digito2);

        System.out.println("Llegue");

    }

    public void actualizarDigito(int digito){
        this.digitoRepresentado = digito;
        //digito1.actualizar(digito/10);
        //digito2.actualizar(digito%10);
    }

}
