package GUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class PanelCronometro extends JPanel {

    CronometroGUI cronometro;

    public PanelCronometro(LocalTime inicio){
        super();
        setLayout(new BorderLayout());

        cronometro = new CronometroGUI(inicio);

        add(cronometro, BorderLayout.EAST);

    }

}
