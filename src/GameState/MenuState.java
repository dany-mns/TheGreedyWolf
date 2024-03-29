package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MenuState extends GameState{

    private Background bg;

    private int currentChoice = 0;
    private String[] options = {"Start", "Help", "Quit"};

    private Color titleColor;
    private Font titleFont;

    private Font font;


    public MenuState(GameStateManager gsm){
        this.gsm = gsm;

        try{
            bg = new Background("/Backgrounds/bgMenu.png", 1);
            bg.setVector(-0.1, 0);

            titleColor = new Color(111, 0, 129);
            titleFont = new Font("Consolas", Font.BOLD, 18);
            font = new Font("Arial", Font.PLAIN, 12);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        bg.update();
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);

        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("The greedy wolf", 80, 70);

        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if(i == currentChoice){
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }

    }

    private void select() {
        if(currentChoice == 0){
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        if(currentChoice == 1){
            //help
        }
        if(currentChoice == 2){
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP){
            currentChoice--;
            if(currentChoice == -1){
                currentChoice = options.length - 1;
            }
        }
        if(k == KeyEvent.VK_DOWN){
            currentChoice++;
            if(currentChoice == options.length){
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
