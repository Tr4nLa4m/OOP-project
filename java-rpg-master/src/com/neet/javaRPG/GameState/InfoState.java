package com.neet.javaRPG.GameState;

import com.neet.javaRPG.Manager.Content;
import com.neet.javaRPG.Manager.GameStateManager;
import com.neet.javaRPG.Manager.Keys;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InfoState extends GameState{
    private BufferedImage bg;

    public InfoState(GameStateManager gsm) {
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
        Content.drawStringBig(g,"CREATED BY", 80,50,30);
        Content.drawStringBig(g,"Tran Ngoc Lam-20194091", 100,180,20);
        Content.drawStringBig(g,"Pham The Manh-20194114", 100,250,20);
        Content.drawStringBig(g,"Luong Son Tinh-20194186", 100,320,20);
        Content.drawStringBig(g,"Nguyen Van Dang-20194009", 100,390,20);
        Content.drawStringBig(g,"Trinh Duy Truong-20194197", 100,460,20);



    }
    public void handleInput(){
        if(Keys.isPressed(Keys.ENTER)){
            gsm.setState(GameStateManager.MENU);
        }
    }
}
