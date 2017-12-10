import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Tanks";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;

    public static final float UPDATE_RATE = 60.0f;
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long IDLE_TIME = 1;

    public static final String ATLAS_FILE_NAME = "texture_atlas.png";

    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;

    private Input input;

    private TextureAtlas atlas;
    private SpriteSheet sheet;
    private Sprite sprite;

    //temp
    int x = 350;
    int y = 250;


    //temp end


    public Game(){
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        sheet = new SpriteSheet(atlas.cut(8*16, 8*16, 32, 16), 2, 16);      //take a tank
        sprite = new Sprite(sheet, 1);                                                          //create individual sprite for tank
    }

    public synchronized void start(){
        if(running) return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop(){
        if(!running) return;

        running = false;

        try {
            gameThread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        cleanUp();
    }

    private void update(){
        if(input.getKey(KeyEvent.VK_UP)){
            y -= 5;
        }
        if(input.getKey(KeyEvent.VK_DOWN)){
            y += 5;
        }
        if(input.getKey(KeyEvent.VK_LEFT)){
            x -= 5;
        }
        if(input.getKey(KeyEvent.VK_RIGHT)){
            x += 5;
        }
    }

    private void render(){
        Display.clear();

        sprite.render(graphics, x, y);              //draw tank

        Display.swapBuffers();
    }

    public void run(){
        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;

        float delta = 0;

        long lastTime = Time.get();

        while(running){
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            while(delta > 1){
                update();
                upd++;
                delta--;
                if(render){
                    updl++;             //check lost updates
                } else {
                    render = true;
                }
            }

            if(render){
                render();
                fps++;
            }
            else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(count >= Time.SECOND){
                Display.setTitle(TITLE + " || FPS: " + fps + " | update: " + upd + " | updateL: " + updl);
                fps = upd = updl = 0;
                count = 0;
            }
        }
    }

    private void cleanUp(){
        Display.destroy();
    }
}
