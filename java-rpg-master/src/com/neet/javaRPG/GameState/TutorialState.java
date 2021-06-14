package com.neet.javaRPG.GameState;

import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.Manager.GameStateManager;
import com.neet.javaRPG.Manager.Keys;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TutorialState extends GameState {
    private BufferedImage bg;

    public TutorialState(GameStateManager gsm) {
        super(gsm);
    }
    public void init(){
        bg = Content.MENUBG[0][0];
    }
    public void update(){
        handleInput();

    }
    public void draw(Graphics2D g){
        g.drawImage(bg, 0, 0 , null);
        Content.drawStringBig(g,"Move :: Arrow keys", 80,100,24);
        Content.drawStringBig(g,"Normal Attack :: Space", 80,150,24);
        Content.drawStringBig(g,"Use MP Portion :: G", 80,200,24);
        Content.drawStringBig(g,"Use HP Portion :: H", 80,250,24);


    }
    public void handleInput(){
        if(Keys.isPressed(Keys.ENTER)){
            gsm.setState(GameStateManager.MENU);
        }
    }
}
