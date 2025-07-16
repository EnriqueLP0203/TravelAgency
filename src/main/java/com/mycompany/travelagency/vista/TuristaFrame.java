package com.mycompany.travelagency.vista;

import com.mycompany.travelagency.modelo.Turista;
import com.mycompany.travelagency.servicio.TuristaService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TuristaFrame extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtFechaNacimiento;

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
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 5, 10);
    gbc.anchor = GridBagConstraints.WEST;

    // Labels - siempre en gridx=0, anchor WEST
    // Campos - en gridx=1, fill horizontal + weightx=1 para que se estiren bien

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

    // Botones en otro panel para que queden en fila con espacios
    gbc.gridx = 0; gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0;
    gbc.anchor = GridBagConstraints.CENTER;

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    btnGuardar = new JButton("Guardar");
    btnLimpiar = new JButton("Limpiar");
    btnActualizar = new JButton("Actualizar");
    btnEliminar = new JButton("Eliminar");
    btnRefrescar = new JButton("Refrescar");

    btnActualizar.setEnabled(false);
    btnEliminar.setEnabled(false);

    buttonPanel.add(btnGuardar);
    buttonPanel.add(btnActualizar);
    buttonPanel.add(btnEliminar);
    buttonPanel.add(btnLimpiar);
    buttonPanel.add(btnRefrescar);

    formPanel.add(buttonPanel, gbc);

    // Panel tabla con border y scrollpane
    tableModel = new TuristaTableModel();
    tableTuristas = new JTable(tableModel);
    tableTuristas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tableTuristas.setRowHeight(25);

    JScrollPane scrollPane = new JScrollPane(tableTuristas);
    scrollPane.setBorder(new TitledBorder("Lista de Turistas"));

    // Usar JSplitPane para dividir pantalla entre formulario y tabla
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, scrollPane);
    splitPane.setResizeWeight(0.4); // 40% para formulario, 60% para tabla
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
        }
    }

    private void guardarTurista() {
        try {
            if (validarCampos()) {
                Turista turista = new Turista(
                    txtNombre.getText().trim(),
                    txtApellidoPaterno.getText().trim(),
                    txtApellidoMaterno.getText().trim(),
                    LocalDate.parse(txtFechaNacimiento.getText().trim())
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
