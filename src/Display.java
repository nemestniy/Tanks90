import javax.swing.JFrame;
import java.awt.*;
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

    public static void create(int width, int height, String title, int _clearColor){
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
        clearColor = _clearColor;

        created = true;
    }

    public static void clear() {
        Arrays.fill(bufferData, clearColor);        //fill array bufferData by clearColor
    }

    public static void render() {
        bufferGraphics.setColor(new Color(0xffffff00));
        bufferGraphics.fillOval(350,250,100,100);
        bufferGraphics.setColor(new Color(0xff0000ff));
        bufferGraphics.fillRect(0, 0, 50, 50);
    }

    public static void swapBuffers(){
        Graphics g = content.getGraphics();
        g.drawImage(buffer, 0, 0, null);
    }


}
