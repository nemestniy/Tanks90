import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Main {
    public static void main(String args[]){
        Display.create(800,600,"Tanks");    //create window

        Timer time = new Timer(1000 / 60, new AbstractAction(){
            public void actionPerformed(ActionEvent e) {        //calling every 60 times in second
                Display.render();
            }
        });

        time.setRepeats(true);          //looping timer
        time.start();
    }
}
