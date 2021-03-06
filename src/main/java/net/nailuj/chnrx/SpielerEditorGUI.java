/*
 * Copyright (C) 2018 Zisis Relas, Florian Rost, Julian Blazek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.nailuj.chnrx;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author Julian
 */
public class SpielerEditorGUI extends JDialog {

    // Anfang Attribute
    private JLabel lblName = new JLabel();
    private JTextField txtName = new JTextField();
    private JLabel lFarbe = new JLabel();
    private JColorChooser clColor = new JColorChooser();
    private JButton bFarbeauswahlen = new JButton();
    private JCheckBox cbIstAI = new JCheckBox();
    private JLabel lblAI = new JLabel();
    private JButton btnSpeichern = new JButton();
    private JButton btnLoschen = new JButton();
    private Spieler spieler;
    private Steuerung steu;
    private Color choosencol = Color.BLUE;
// Ende Attribute

    public SpielerEditorGUI(Steuerung steuerungsobj, boolean bearbeiten) {
        steu = steuerungsobj;
        init(bearbeiten);
    } // end of public SpielerEditorGUI

    SpielerEditorGUI(Steuerung steuerung, boolean b, Spieler ausgewspieler) {
        steu = steuerung;
        spieler = ausgewspieler;
        init(b);
    }

    // Anfang Methoden
    public void init(boolean bearbeiten) {
        // Frame-Initialisierung
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 247;
        int frameHeight = 167;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("Spieler");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten

        lblName.setBounds(8, 8, 62, 20);
        lblName.setText("Name:");
        lblName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblName.setHorizontalTextPosition(SwingConstants.RIGHT);
        cp.add(lblName);
        txtName.setBounds(80, 8, 150, 20);
        cp.add(txtName);
        lFarbe.setBounds(8, 32, 62, 20);
        lFarbe.setText("Farbe:");
        lFarbe.setHorizontalAlignment(SwingConstants.RIGHT);
        cp.add(lFarbe);
        bFarbeauswahlen.setBounds(80, 32, 147, 25);
        bFarbeauswahlen.setText("");
        bFarbeauswahlen.setMargin(new Insets(2, 2, 2, 2));
        bFarbeauswahlen.addActionListener((ActionEvent evt) -> {
            bFarbeauswahlen_ActionPerformed(evt);
        });
        bFarbeauswahlen.setBackground(Color.BLUE);
        bFarbeauswahlen.setContentAreaFilled(true);
        bFarbeauswahlen.setForeground(Color.YELLOW);
        cp.add(bFarbeauswahlen);
        cbIstAI.setBounds(80, 64, 36, 20);
        cbIstAI.setOpaque(false);
        cbIstAI.setText("");
        cbIstAI.setEnabled(false);
        cp.add(cbIstAI);
        lblAI.setBounds(8, 64, 62, 20);
        lblAI.setText("Ist AI:");
        lblAI.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAI.setEnabled(false);
        cp.add(lblAI);
        btnSpeichern.setBounds(16, 96, 99, 25);
        btnSpeichern.setText("Speichern");
        btnSpeichern.setMargin(new Insets(2, 2, 2, 2));
        btnSpeichern.addActionListener((ActionEvent evt) -> {
            btnSpeichern_ActionPerformed(evt);
        });
        cp.add(btnSpeichern);
        btnLoschen.setBounds(128, 96, 99, 25);
        btnLoschen.setText("Löschen");
        btnLoschen.setMargin(new Insets(2, 2, 2, 2));
        btnLoschen.addActionListener((ActionEvent evt) -> {
            btnLoschen_ActionPerformed(evt);
        });
        cp.add(btnLoschen);
        // Ende Komponenten
        if (spieler != null) {
            txtName.setText(spieler.getName());
            choosencol = spieler.getFarbe();
            bFarbeauswahlen.setBackground(choosencol);
            cbIstAI.setSelected(spieler.istAI());
        }
        if (bearbeiten == false) {
            btnLoschen.setEnabled(false);
        } // end of if
        setVisible(true);
    }

    public Color clColor_getColor() {
        return JColorChooser.showDialog(this, "", Color.BLUE);
    }

    public void bFarbeauswahlen_ActionPerformed(ActionEvent evt) {
        choosencol = clColor_getColor();
        bFarbeauswahlen.setBackground(choosencol);
    } // end of bFarbeauswahlen_ActionPerformed

    public void btnSpeichern_ActionPerformed(ActionEvent evt) {
        if (spieler == null) {
            spieler = new Spieler(choosencol, txtName.getText(), cbIstAI.isSelected());
        } else {
            spieler.setName(txtName.getText());
            spieler.setFarbe(choosencol);
            spieler.setIstAI(cbIstAI.isSelected());
        } // end of if
        steu.addSpieler(spieler);
        steu.aktualisiereSpielerListe();
        this.dispose();
    } // end of btnSpeichern_ActionPerformed

    public void btnLoschen_ActionPerformed(ActionEvent evt) {
        steu.delSpieler(spieler);
        steu.aktualisiereSpielerListe();
        this.dispose();
    } // end of btnLoschen_ActionPerformed

    public void setzeSpieler(Spieler spielerobj) {
        spieler = spielerobj;
        txtName.setText(spieler.getName());
        choosencol = spieler.getFarbe();
        bFarbeauswahlen.setBackground(choosencol);
        cbIstAI.setSelected(spieler.istAI());
    }

    // Ende Methoden
}
