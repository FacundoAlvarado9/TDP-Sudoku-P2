package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;

public class CronometroGUI extends JLabel {

    private Timer timer;
    private LocalTime timeReferencia;


    public CronometroGUI(LocalTime timeRef){
        super("88:88:88");
        this.timeReferencia = timeRef;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Duration d = Duration.between(timeReferencia, LocalTime.now());

                String hora_ = String.format("%02d:%02d:%02d",
                        d.toHours(),
                        d.toMinutesPart(),
                        d.toSecondsPart());

                setText(hora_);

            }
        });

        timer.start();
    }

    public Duration parar(){
        Duration ultimaMedicion = Duration.between(timeReferencia, LocalTime.now());
        timer.stop();
        return ultimaMedicion;
    }



}
