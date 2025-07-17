package com.mycompany.travelagency.vista;

import com.mycompany.travelagency.modelo.Sucursal;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SucursalTableModel extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Código", "Dirección", "Teléfono", "Ciudad"};
    private List<Sucursal> sucursales;

    public SucursalTableModel() {
        this.sucursales = new ArrayList<>();
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales != null ? sucursales : new ArrayList<>();
        fireTableDataChanged();
    }

    public Sucursal getSucursalAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < sucursales.size()) {
            return sucursales.get(rowIndex);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return sucursales.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sucursal s = sucursales.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return s.getId();
            case 1:
                return s.getCodigoSucursal();
            case 2:
                return s.getDireccion();
            case 3:
                return s.getTelefono();
            case 4:
                return s.getCiudad(); // Nueva columna
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Long.class;
        } else {
            return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
