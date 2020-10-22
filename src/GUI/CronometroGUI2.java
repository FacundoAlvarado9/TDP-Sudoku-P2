package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Duration;
import java.time.LocalTime;

public class CronometroGUI2 extends JPanel {

    private JLabel[] digitosHoras;
    private JLabel[] digitosMinutos;
    private JLabel[] digitosSegundos;

    private LocalTime timeReferencia;
    private Timer timer;

    private int horas, minutos, segundos;



    public CronometroGUI2(LocalTime timeReferencia){
        super();
        setLayout(new GridLayout(0, 6));

        this.timeReferencia = timeReferencia;

        digitosHoras = new JLabel[2];
        digitosMinutos = new JLabel[2];
        digitosSegundos = new JLabel[2];

        for(int i=0; i<2; i++){
            digitosHoras[i] = new JLabel();
            digitosMinutos[i] = new JLabel();
            digitosSegundos[i] = new JLabel();
        }
        add(digitosHoras[0]);
        add(digitosHoras[1]);
        add(new JLabel(":"));
        add(digitosMinutos[0]);
        add(digitosMinutos[1]);
        add(new JLabel(":"));
        add(digitosSegundos[0]);
        add(digitosSegundos[1]);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Duration d = Duration.between(timeReferencia, LocalTime.now());

                horas = d.toHoursPart();
                minutos = d.toMinutesPart();
                segundos = d.toSecondsPart();

                String hora_ = String.format("%02d:%02d:%02d",
                        horas, minutos, segundos);

                /*
                digitosHoras[0].setText(horas/10 + "");
                digitosHoras[1].setText(horas%10 + "");

                digitosMinutos[0].setText(minutos/10 + "");
                digitosMinutos[1].setText(minutos%10 + "");

                digitosSegundos[0].setText(segundos/10 + "");
                digitosSegundos[1].setText(segundos%10 + "");
                */

                digitosHoras[0].setIcon(refrescar(horas/10));
                digitosHoras[1].setIcon(refrescar(horas%10));

                digitosMinutos[0].setIcon(refrescar(minutos/10));
                digitosMinutos[1].setIcon(refrescar(minutos%10));

                digitosSegundos[0].setIcon(refrescar(segundos/10));
                digitosSegundos[1].setIcon(refrescar(segundos%10));

                for(int i=0; i<2; i++){
                    digitosHoras[i].repaint();
                    digitosMinutos[i].repaint();
                    digitosSegundos[i].repaint();
                }
            }
        });

        timer.start();
    }

    public ImageIcon refrescar(int num){
        return new ImageIcon(this.getClass().getResource("/img/celdas/0" + num + ".png"));
    }

    public Duration parar(){
        return null;
    }

}
