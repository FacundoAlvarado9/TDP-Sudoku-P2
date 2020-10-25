package GUI.Cronometro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Clase Cronometro. Define un cronómetro gráfico en forma de
 * JPanel que muestra el tiempo transcurrido desde un determinado
 * tiempo de referencia.
 */
public class Cronometro extends JPanel {

    private Timer timer;
    private LocalTime timeReferencia;

    private DobleDigitoCronometro horas;
    private DobleDigitoCronometro minutos;
    private DobleDigitoCronometro segundos;

    /**
     * Inicializa un Cronometro sin darle arranque.
     * Dentro de él, inicializa los componentes gráficos, es decir
     * dígitos del cronometro, y los añade al mismo.
     */
    public Cronometro(){
        super();

        horas = new DobleDigitoCronometro(0);
        minutos = new DobleDigitoCronometro(0);
        segundos = new DobleDigitoCronometro(0);


        add(horas);
        add(new JLabel(":"));
        add(minutos);
        add(new JLabel(":"));
        add(segundos);
    }

    /**
     * Da inicio al timer con un tiempo de referencia pasado como parametro.
     * @param timeReferencia tiempo de referencia del timer.
     */
    public void arrancar(LocalTime timeReferencia){

        this.timeReferencia = timeReferencia;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Duration d = Duration.between(timeReferencia, LocalTime.now());

                String hora_ = String.format("%02d:%02d:%02d",
                        d.toHours(),
                        d.toMinutesPart(),
                        d.toSecondsPart());

                segundos.actualizarDigito(d.toSecondsPart());
                segundos.redimensionar();

                minutos.actualizarDigito(d.toMinutesPart());
                minutos.redimensionar();

                horas.actualizarDigito(d.toHoursPart());
                horas.redimensionar();

            }
        });
        timer.start();
    }

    private void redimensionar(DigitoCronometro label, ImageIcon grafico){
        Image imagen = grafico.getImage();
        if(imagen != null){
            Image newImg = imagen.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT);
            grafico.setImage(newImg);
            label.repaint();
        }
    }

    public boolean estaCorriendo(){
        return timer.isRunning();
    }

    /**
     * Frena el timer y devuelve su última medición.
     * @return la última medición del timer
     */
    public Duration parar(){
        Duration ultimaMedicion = Duration.between(timeReferencia, LocalTime.now());
        timer.stop();
        return ultimaMedicion;
    }

}
