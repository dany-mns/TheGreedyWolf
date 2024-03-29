package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;

public abstract class MapObject {

    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap;
    protected double ymap;

    //position
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    //dimension
    protected int width;
    protected int height;

    //collision box
    protected int cwidth;
    protected int cheight;

    //collision
    protected int currentRow;
    protected int currentCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    //animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;

    //movement
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    // movement attributes
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;


    public MapObject(TileMap tileMap) {
        this.tileMap = tileMap;
        this.tileSize = tileMap.getTileSize();
    }

    public boolean intersects(MapObject o){
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle(){
        return new Rectangle((int)x - cwidth, (int)y - cheight, cwidth, cheight);
    }

    public void calculateCorners(double x, double y) {

        int leftTile = (int)(x - cwidth/2) / tileSize;
        int rightTile = (int)(x + cwidth/2 - 1) / tileSize;
        int topTile = (int)(y - cheight/2) / tileSize;
        int bottomTile = (int)(y + cheight/2) / tileSize;

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);

        topLeft = tl == Tile.BLOCK;
        topRight = tr == Tile.BLOCK;
        bottomLeft = bl == Tile.BLOCK;
        bottomRight = br == Tile.BLOCK;

    }

    public void checkTileMapCollision() {

        currentCol = (int)x / tileSize;
        currentRow = (int)y / tileSize;

        xdest = x + dx;
        ydest = y + dy;

        xtemp = x;
        ytemp = y;

        calculateCorners(x, ydest);
        if(dy < 0) {
            if(topRight || topLeft){
                dy = 0;
                ytemp = currentRow * tileSize + cheight/2;
            } else
                ytemp += dy;
        }
        if(dy > 0) {
            if(bottomRight || bottomLeft){
                dy = 0;
                ytemp = (currentRow+1) * tileSize - cheight/2;
            } else
                ytemp += dy;
        }

        calculateCorners(xdest, y);
        if(dx < 0) {
            if(topLeft || bottomLeft){
                dx = 0;
                xtemp = currentCol * tileSize + cwidth/2;
            } else
                xtemp += dx;
        }
        if(dx > 0) {
            if(topRight || bottomRight) {
                dx = 0;
                xtemp = (currentCol + 1) * tileSize - cwidth/2;
            } else
                xtemp += dx;
        }

        if(!falling){
            calculateCorners(x, ydest + 1);
            if(!bottomRight && !bottomLeft) {
                falling = true;
            }
        }
    }

    public int getx() { return (int)x; }
    public int gety() { return (int)y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getCWidth() { return cwidth; }
    public int getCHeight() { return cheight; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setMapPosition() {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }

    public void setLeft(boolean bool) { left = bool; }
    public void setRight(boolean bool) { right = bool; }
    public void setUp(boolean bool) { up = bool; }
    public void setDown(boolean bool) { down = bool; }
    public void setJumping(boolean bool) { jumping = bool; }

    public boolean isOnScreen() {
        return x + xmap + width > 0 || x + xmap - width < GamePanel.WIDTH ||
                y + ymap + height > 0 || y + ymap - height < GamePanel.HEIGHT;
    }
}
