/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travelagency.vista;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.mycompany.travelagency.modelo.Turista;

/**
 *
 * @author enriq
 */
public class TuristaTableModel extends AbstractTableModel {

    private final String[] columnNames = {
    "ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Fecha de Nacimiento", "Correo", "Teléfono"};
    private List<Turista> turistas;

    public TuristaTableModel() {
        this.turistas = new ArrayList<>();
    }

    public TuristaTableModel(List<Turista> turistas) {
        this.turistas = turistas != null ? turistas : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return turistas.size();
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
        Turista turista = turistas.get(rowIndex);
        switch (columnIndex) {
            case 0: return turista.getId();
            case 1: return turista.getNombre();
            case 2: return turista.getApellidoPaterno();
            case 3: return turista.getApellidoMaterno();
            case 4: return turista.getFechaNacimiento();
            case 5: return turista.getCorreo();
            case 6: return turista.getTelefono();
            default: return null;
        }
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Long.class;
            case 4: return java.time.LocalDate.class;
            default: return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setTuristas(List<Turista> turistas) {
        this.turistas = turistas != null ? turistas : new ArrayList<>();
        fireTableDataChanged();
    }

    public Turista getTuristaAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < turistas.size()) {
            return turistas.get(rowIndex);
        }
        return null;
    }

    public void addTurista(Turista turista) {
        turistas.add(turista);
        fireTableRowsInserted(turistas.size() - 1, turistas.size() - 1);
    }

    public void removeTurista(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < turistas.size()) {
            turistas.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
}
