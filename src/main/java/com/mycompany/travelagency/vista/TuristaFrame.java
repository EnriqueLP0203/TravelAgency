package com.mycompany.travelagency.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.awt.GridLayout;


import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.mycompany.travelagency.modelo.Turista;
import com.mycompany.travelagency.servicio.TuristaService;

public class TuristaFrame extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtFechaNacimiento;
    private JTextField txtCorreo;
    private JTextField txtTelefono;

    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnRefrescar;

    private JTable tableTuristas;
    private TuristaTableModel tableModel;

    private final TuristaService turistaService;

    private Turista turistaSeleccionado;

    public TuristaFrame() {
        turistaService = new TuristaService();
        initComponents();
        configureFrame();
        configureEvents();
        loadData();
    }

    private void initComponents() {
        setTitle("Registro y Gestión de Turistas");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel formulario con GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Datos del Turista"));
        formPanel.setPreferredSize(new Dimension(350, 0));  // Para que no se comprima
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels y campos

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        formPanel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Apellido Paterno:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtApellidoPaterno = new JTextField(20);
        formPanel.add(txtApellidoPaterno, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Apellido Materno:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtApellidoMaterno = new JTextField(20);
        formPanel.add(txtApellidoMaterno, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Fecha Nacimiento (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtFechaNacimiento = new JTextField(20);
        formPanel.add(txtFechaNacimiento, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Correo (Gmail):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCorreo = new JTextField(20);
        formPanel.add(txtCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtTelefono = new JTextField(20);
        formPanel.add(txtTelefono, gbc);

        // Panel de botones vertical
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Acciones"));

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new java.awt.Color(76, 175, 80));
        btnGuardar.setForeground(java.awt.Color.BLACK);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new java.awt.Color(33, 150, 243));
        btnActualizar.setForeground(java.awt.Color.BLACK);
        btnActualizar.setEnabled(false);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new java.awt.Color(244, 67, 54));
        btnEliminar.setForeground(java.awt.Color.BLACK);
        btnEliminar.setEnabled(false);

        btnLimpiar = new JButton("Limpiar");
        btnRefrescar = new JButton("Refrescar");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnRefrescar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(buttonPanel, gbc);

        // Panel tabla con scrollpane
        tableModel = new TuristaTableModel();
        tableTuristas = new JTable(tableModel);
        tableTuristas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTuristas.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tableTuristas);
        scrollPane.setBorder(new TitledBorder("Lista de Turistas"));

        // Dividir pantalla entre formulario y tabla
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, scrollPane);
        splitPane.setResizeWeight(0.4); // 40% formulario, 60% tabla
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(350);

        add(splitPane, BorderLayout.CENTER);
    }

    private void configureEvents() {
        btnGuardar.addActionListener(e -> guardarTurista());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnActualizar.addActionListener(e -> actualizarTurista());
        btnEliminar.addActionListener(e -> eliminarTurista());
        btnRefrescar.addActionListener(e -> loadData());

        tableTuristas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableTuristas.getSelectedRow();
                if (selectedRow >= 0) {
                    turistaSeleccionado = tableModel.getTuristaAt(selectedRow);
                    cargarDatosTurista(turistaSeleccionado);
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    btnGuardar.setEnabled(false);
                } else {
                    turistaSeleccionado = null;
                    btnActualizar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    btnGuardar.setEnabled(true);
                    limpiarFormulario();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarAplicacion();
            }
        });
    }

    private void cargarDatosTurista(Turista t) {
        if (t != null) {
            txtNombre.setText(t.getNombre());
            txtApellidoPaterno.setText(t.getApellidoPaterno());
            txtApellidoMaterno.setText(t.getApellidoMaterno());
            txtFechaNacimiento.setText(t.getFechaNacimiento().toString());
            txtCorreo.setText(t.getCorreo());
            txtTelefono.setText(t.getTelefono());
        }
    }

    private void guardarTurista() {
        try {
            if (validarCampos()) {
                Turista turista = new Turista(
                    txtNombre.getText().trim(),
                    txtApellidoPaterno.getText().trim(),
                    txtApellidoMaterno.getText().trim(),
                    LocalDate.parse(txtFechaNacimiento.getText().trim()),
                    txtCorreo.getText().trim(),
                    txtTelefono.getText().trim()
                );

                turistaService.guardarTurista(turista);
                mostrarMensaje("Turista guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                loadData();
            }
        } catch (DateTimeParseException ex) {
            mostrarMensaje("Formato de fecha inválido. Usa YYYY-MM-DD.", "Error de validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            mostrarMensaje("Error al guardar turista: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTurista() {
        if (turistaSeleccionado != null) {
            try {
                if (validarCampos()) {
                    turistaSeleccionado.setNombre(txtNombre.getText().trim());
                    turistaSeleccionado.setApellidoPaterno(txtApellidoPaterno.getText().trim());
                    turistaSeleccionado.setApellidoMaterno(txtApellidoMaterno.getText().trim());
                    turistaSeleccionado.setFechaNacimiento(LocalDate.parse(txtFechaNacimiento.getText().trim()));
                    turistaSeleccionado.setCorreo(txtCorreo.getText().trim());
                    turistaSeleccionado.setTelefono(txtTelefono.getText().trim());

                    turistaService.actualizarTurista(turistaSeleccionado);
                    mostrarMensaje("Turista actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    loadData();
                    turistaSeleccionado = null;
                    btnActualizar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    btnGuardar.setEnabled(true);
                }
            } catch (DateTimeParseException ex) {
                mostrarMensaje("Formato de fecha inválido. Usa YYYY-MM-DD.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                mostrarMensaje("Error al actualizar turista: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarTurista() {
        if (turistaSeleccionado != null) {
            int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar a " + turistaSeleccionado.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );
            if (opcion == JOptionPane.YES_OPTION) {
                try {
                    turistaService.eliminarTurista(turistaSeleccionado.getId());
                    mostrarMensaje("Turista eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    loadData();
                    turistaSeleccionado = null;
                    btnActualizar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    btnGuardar.setEnabled(true);
                } catch (Exception e) {
                    mostrarMensaje("Error al eliminar turista: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtFechaNacimiento.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");

        tableTuristas.clearSelection();
        turistaSeleccionado = null;
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(true);
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
            txtApellidoPaterno.getText().trim().isEmpty() ||
            txtApellidoMaterno.getText().trim().isEmpty() ||
            txtFechaNacimiento.getText().trim().isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtCorreo.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty()) {
            mostrarMensaje("Correo y teléfono son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            LocalDate.parse(txtFechaNacimiento.getText().trim());
        } catch (DateTimeParseException e) {
            mostrarMensaje("Formato de fecha inválido. Usa YYYY-MM-DD.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void loadData() {
        try {
            List<Turista> turistas = turistaService.obtenerTodosLosTuristas();
            tableModel.setTuristas(turistas);
        } catch (Exception e) {
            mostrarMensaje("Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    private void cerrarAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de salir?",
            "Salir",
            JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.YES_OPTION) {
            turistaService.cerrar();
            System.exit(0);
        }
    }

    private void configureFrame() {
        setSize(900, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 400));
    }
}
