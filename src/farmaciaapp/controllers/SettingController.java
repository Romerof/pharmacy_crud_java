/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class SettingController implements MouseListener{
    
    

    private final Color normalCont = new Color(18,35,61);
    private final Color enteredCont= new Color(162,202,63);
    private final Color normalLabel= new Color(255,255,255);
    private final Color enteredLabel= new Color(18,35,61);

    public SettingController() {
    }
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        javax.swing.JLabel label= (javax.swing.JLabel) e.getSource();
        java.awt.Container parent = label.getParent();
        
        label.setForeground(enteredLabel);
        parent.setBackground(enteredCont);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        javax.swing.JLabel label= (javax.swing.JLabel) e.getSource();
        java.awt.Container parent = label.getParent();
        
        label.setForeground(normalLabel);
        parent.setBackground(normalCont);
    }
    
}
