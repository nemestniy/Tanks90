import javax.swing.JFrame;
import java.awt.*;

public class Display {
    private static boolean created = false;

    private static JFrame window;
    private static Canvas content;

    public static void create(int width, int height, String title){
        if(created) return;                     //checking display

        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = new Canvas(){
            public void paint(Graphics g){
                super.paint(g);                 //call original function(kill future artefacts on display)
                render(g);
            }
        };

        Dimension size = new Dimension(width, height);
        content.setPreferredSize(size);             //size of content
        content.setBackground(Color.black);

        window.setResizable(false);             //you cant change size of window
        window.getContentPane().add(content);   //put content into window
        window.pack();                          //change size content for window
        window.setLocationRelativeTo(null);     //position of window is center
        window.setVisible(true);                //turn on window

    }

    public static void render() {
        content.repaint();
    }

    private static void render(Graphics g){
        g.setColor(Color.white);
        g.fillOval(375,275,50,50);
    }
}
