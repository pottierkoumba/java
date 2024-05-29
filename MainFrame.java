import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
    public static void main(String[] args) {
        // Créez la fenêtre principale (JFrame)
        JFrame frame = new JFrame("Gestion des Employés");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Modèle de données pour la table
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Téléphone", "Département"}, 0);
        JTable table = new JTable(model);

        // Barre de défilement pour la table
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panneau pour les champs de texte et les boutons
        JPanel panel = new JPanel(new GridLayout(6, 2));

        // Champs de texte
        JTextField idField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField telField = new JTextField();
        JTextField depField = new JTextField();

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nom Employe:"));
        panel.add(nomField);
        panel.add(new JLabel("Prenom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Numéro de telephone:"));
        panel.add(telField);
        panel.add(new JLabel("Departement:"));
        panel.add(depField);

        // Bouton Envoyer
        JButton sendButton = new JButton("Envoyer");
        panel.add(sendButton);

        // Ajouter le panneau en bas de la fenêtre
        frame.add(panel, BorderLayout.SOUTH);

        // Ajouter un panneau pour les boutons Modifier et Supprimer
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton modifyButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        JButton searchButton = new JButton("Chercher un employe");

        actionPanel.add(modifyButton);
        actionPanel.add(deleteButton);
        actionPanel.add(searchButton);

        frame.add(actionPanel, BorderLayout.NORTH);

        // Action pour le bouton Envoyer
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String tel = telField.getText();
                String dep = depField.getText();

                // Vérifier si l'employé existe déjà
                boolean exists = false;
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(id)) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    model.addRow(new Object[]{id, nom, prenom, tel, dep});
                } else {
                    JOptionPane.showMessageDialog(frame, "L'employe existe deja!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action pour le bouton Supprimer
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action pour le bouton Modifier
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.setValueAt(nomField.getText(), selectedRow, 1);
                    model.setValueAt(prenomField.getText(), selectedRow, 2);
                    model.setValueAt(telField.getText(), selectedRow, 3);
                    model.setValueAt(depField.getText(), selectedRow, 4);
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne à modifier", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action pour le bouton Chercher
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = nomField.getText().toLowerCase();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 1).toString().toLowerCase().contains(searchName)) {
                        table.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        });

        // Rendez la fenêtre visible
        frame.setVisible(true);
    }
}
