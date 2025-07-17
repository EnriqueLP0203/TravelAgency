package com.mycompany.travelagency.vista;

import com.mycompany.travelagency.modelo.Sucursal;
import com.mycompany.travelagency.servicio.SucursalService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class SucursalFrame extends JFrame {

    private JTextField txtCodigoSucursal;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtCiudad;

    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnRefrescar;

    private JTable tableSucursales;
    private SucursalTableModel tableModel;

    private final SucursalService sucursalService;

    private Sucursal sucursalSeleccionada;

    public SucursalFrame() {
        sucursalService = new SucursalService();
        initComponents();
        configureFrame();
        configureEvents();
        loadData();
    }

    private void initComponents() {
        setTitle("Gestión de Sucursales");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Datos de la Sucursal"));
        formPanel.setPreferredSize(new Dimension(350, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Código de Sucursal:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCodigoSucursal = new JTextField(20);
        formPanel.add(txtCodigoSucursal, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtDireccion = new JTextField(20);
        formPanel.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtTelefono = new JTextField(20);
        formPanel.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCiudad = new JTextField(20);
        formPanel.add(txtCiudad, gbc);

        // Botones en forma vertical y con colores
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Acciones"));

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(76, 175, 80)); // Verde
        btnGuardar.setForeground(Color.BLACK);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(33, 150, 243)); // Azul
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setEnabled(false);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(244, 67, 54)); // Rojo
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setEnabled(false);

        btnLimpiar = new JButton("Limpiar");
        btnRefrescar = new JButton("Refrescar");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnRefrescar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(buttonPanel, gbc);

        tableModel = new SucursalTableModel();
        tableSucursales = new JTable(tableModel);
        tableSucursales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSucursales.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tableSucursales);
        scrollPane.setBorder(new TitledBorder("Lista de Sucursales"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, scrollPane);
        splitPane.setResizeWeight(0.4);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(350);

        add(splitPane, BorderLayout.CENTER);
    }

    private void configureEvents() {
        btnGuardar.addActionListener(e -> guardarSucursal());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnActualizar.addActionListener(e -> actualizarSucursal());
        btnEliminar.addActionListener(e -> eliminarSucursal());
        btnRefrescar.addActionListener(e -> loadData());

        tableSucursales.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableSucursales.getSelectedRow();
                if (selectedRow >= 0) {
                    sucursalSeleccionada = tableModel.getSucursalAt(selectedRow);
                    cargarDatosSucursal(sucursalSeleccionada);
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    btnGuardar.setEnabled(false);
                } else {
                    sucursalSeleccionada = null;
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

    private void cargarDatosSucursal(Sucursal s) {
        txtCodigoSucursal.setText(s.getCodigoSucursal());
        txtDireccion.setText(s.getDireccion());
        txtTelefono.setText(s.getTelefono());
        txtCiudad.setText(s.getCiudad());
    }

    private void guardarSucursal() {
        if (validarCampos()) {
            try {
                Sucursal sucursal = new Sucursal(
                        txtCodigoSucursal.getText().trim(),
                        txtDireccion.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtCiudad.getText().trim()
                );
                sucursalService.guardarSucursal(sucursal);
                mostrarMensaje("Sucursal guardada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                loadData();
            } catch (Exception e) {
                mostrarMensaje("Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarSucursal() {
        if (sucursalSeleccionada != null && validarCampos()) {
            try {
                sucursalSeleccionada.setCodigoSucursal(txtCodigoSucursal.getText().trim());
                sucursalSeleccionada.setDireccion(txtDireccion.getText().trim());
                sucursalSeleccionada.setTelefono(txtTelefono.getText().trim());
                sucursalSeleccionada.setCiudad(txtCiudad.getText().trim());

                sucursalService.actualizarSucursal(sucursalSeleccionada);
                mostrarMensaje("Sucursal actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                loadData();
            } catch (Exception e) {
                mostrarMensaje("Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarSucursal() {
        if (sucursalSeleccionada != null) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas eliminar esta sucursal?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                try {
                    sucursalService.eliminarSucursal(sucursalSeleccionada.getId());
                    mostrarMensaje("Sucursal eliminada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    loadData();
                } catch (Exception e) {
                    mostrarMensaje("Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void limpiarFormulario() {
        txtCodigoSucursal.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCiudad.setText("");
        tableSucursales.clearSelection();
        sucursalSeleccionada = null;
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(true);
    }

    private boolean validarCampos() {
        if (txtCodigoSucursal.getText().trim().isEmpty() ||
            txtDireccion.getText().trim().isEmpty() ||
            txtTelefono.getText().trim().isEmpty() ||
            txtCiudad.getText().trim().isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void loadData() {
        try {
            List<Sucursal> sucursales = sucursalService.obtenerTodasLasSucursales();
            tableModel.setSucursales(sucursales);
        } catch (Exception e) {
            mostrarMensaje("Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    private void cerrarAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            sucursalService.cerrar();
            System.exit(0);
        }
    }

    private void configureFrame() {
        setSize(900, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 400));
    }
}
