import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Main {
    public static void main(String args[]){
        Display.create(800,600,"Tanks", 0xff00ff00, 3);    //create window

        Timer time = new Timer(1000 / 60, new AbstractAction(){
            public void actionPerformed(ActionEvent e) {        //calling every 60 times in second
                Display.clear();                                //refresh window
                Display.render();                               //draw a picture
                Display.swapBuffers();                          //change a picture
            }
        });

        time.setRepeats(true);          //looping timer
        time.start();
    }
}
