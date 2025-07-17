package com.mycompany.travelagency.vista;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Agencia de Viajes - Gestión General");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null); // Centrar
        setLayout(new BorderLayout());

        // Crear las pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Cada gestor como panel dentro de la pestaña
        tabbedPane.addTab("Turistas", new TuristaFramePanel());
        tabbedPane.addTab("Sucursales", new SucursalFramePanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Versión simplificada del Frame como Panel
    static class TuristaFramePanel extends JPanel {
        public TuristaFramePanel() {
            setLayout(new BorderLayout());
            TuristaFrame turistaFrame = new TuristaFrame();
            add(turistaFrame.getContentPane(), BorderLayout.CENTER);
        }
    }

    static class SucursalFramePanel extends JPanel {
        public SucursalFramePanel() {
            setLayout(new BorderLayout());
            SucursalFrame sucursalFrame = new SucursalFrame();
            add(sucursalFrame.getContentPane(), BorderLayout.CENTER);
        }
    }
}
