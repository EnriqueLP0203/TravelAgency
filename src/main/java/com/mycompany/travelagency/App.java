/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travelagency;

import com.mycompany.travelagency.vista.TuristaFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author enriq
 */
public class App {
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Configurar Look and Feel

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.err.println("Error al congigurar Look and Feel: " + e.getMessage());
        }

        // Ejecutar ene el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            TuristaFrame estudianteFrame = new TuristaFrame();
            estudianteFrame.setVisible(true);
        });
    }
    
}
    

