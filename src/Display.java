import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Display {
    private static boolean created = false;

    private static JFrame window;
    private static Canvas content;

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;

    public static void create(int width, int height, String title, int _clearColor, int numbuffers){
        if(created) return;                     //checking display

        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = new Canvas();

        Dimension size = new Dimension(width, height);
        content.setPreferredSize(size);             //size of content

        window.setResizable(false);             //you cant change size of window
        window.getContentPane().add(content);   //put content into window
        window.pack();                          //change size content for window
        window.setLocationRelativeTo(null);     //position of window is center
        window.setVisible(true);                //turn on window

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();        //get array of image colors
        bufferGraphics = buffer.getGraphics();                                          //get picture
        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //add smoothing
        clearColor = _clearColor;

        content.createBufferStrategy(numbuffers);
        bufferStrategy = content.getBufferStrategy();

        created = true;
    }

    public static void clear() {
        Arrays.fill(bufferData, clearColor);        //fill array bufferData by clearColor
    }

    public static void swapBuffers(){
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }

    public static Graphics2D getGraphics(){
        return(Graphics2D) bufferGraphics;
    }

    public static void destroy(){
        if(!created) return;

        window.dispose();
    }

    public static void setTitle(String title){
        window.setTitle(title);
    }

    public static void addInputListener(Input inputListener){
        window.add(inputListener);
    }
}
