import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelKeuangan extends JPanel implements ActionListener,
KeyListener {

    private JTextField txtPengeluaran, txtNominal;
    private JComboBox<String> cmbPrioritas;
    private JButton btnTambah, btnHapus, btnInfo;
    private DefaultListModel<String> modelList;
    private JList<String> listPengeluaran;
    private JLabel lblSisaBudget;

    private JFrame parentFrame;
    private JLabel lblStatus;

    private int sisaBudget = 1000000;

    public PanelKeuangan(JFrame parentFrame, JLabel lblStatus) {
        this.parentFrame = parentFrame;
        this.lblStatus = lblStatus;

        setLayout(new BorderLayout(5, 5));

        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel lblNama = new JLabel(" Nama Pengeluaran:");
        txtPengeluaran = new JTextField();

        JLabel lblNominal = new JLabel(" Nominal (Rp):");
        txtNominal = new JTextField();

        JLabel lblPrioritas = new JLabel(" Tingkat Prioritas:");
        String[] opsiPrioritas = {"[Tinggi] Primer", "[Sedang] Sekunder", "[Rendah] Tersier"};
        cmbPrioritas = new JComboBox<>(opsiPrioritas);

        panelInput.add(lblNama);
        panelInput.add(txtPengeluaran);
        panelInput.add(lblNominal);
        panelInput.add(txtNominal);
        panelInput.add(lblPrioritas);
        panelInput.add(cmbPrioritas);

        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));

        lblSisaBudget = new JLabel("Sisa Budget Bulan Ini: Rp 1.000.000", SwingConstants.CENTER);
        lblSisaBudget.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblSisaBudget.setForeground(new Color(0, 128, 0));

        modelList = new DefaultListModel<>();
        listPengeluaran = new JList<>(modelList);
        JScrollPane scrollList = new JScrollPane(listPengeluaran);

        panelTengah.add(lblSisaBudget, BorderLayout.NORTH);
        panelTengah.add(scrollList, BorderLayout.CENTER);

        JPanel panelAksi = new JPanel(new GridLayout(3, 1, 5, 5));
        btnTambah = new JButton("Tambah");
        btnHapus = new JButton("Hapus");
        btnInfo = new JButton("Info Budget");

        panelAksi.add(btnTambah);
        panelAksi.add(btnHapus);
        panelAksi.add(btnInfo);

        add(panelInput, BorderLayout.NORTH);
        add(panelTengah, BorderLayout.CENTER);
        add(panelAksi, BorderLayout.EAST);

        btnTambah.addActionListener(this);
        btnHapus.addActionListener(this);
        btnInfo.addActionListener(this);

        txtNominal.addKeyListener(this);
    }

    @Override 
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTambah) {
            tambahPengeluaran();
        } else if (e.getSource() == btnHapus) {
            hapusPengeluaran();
        } else if (e.getSource() == btnInfo) {
            JOptionPane.showMessageDialog(parentFrame,
                "Sistem Pengelola Keuangan Bulanan\n" +
                "- Budget Awal: Rp 1.000.000\n" +
                "- Utamakan Pengeluaran dengan prioritas [Tinggi] Primer.\n" +
                "- Gunakan tombol 'Hapus' jika ada pembatalan transaksi.",
                "Informasi Keuangan", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override 
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            tambahPengeluaran();
        }
    }
    
    @Override 
    public void keyTyped(KeyEvent e) {}

    @Override 
    public void keyReleased(KeyEvent e) {
        lblStatus.setText(" Status Mengetik nominal Rp " + txtNominal.getText() + "...");
    } 

    private void tambahPengeluaran() {
        String nama = txtPengeluaran.getText().trim();
        String strNominal = txtNominal.getText().trim();
        String prioritas = (String) cmbPrioritas.getSelectedItem();

        if (nama.isEmpty() || strNominal.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame,
                "Nama pengeluaran dan nominal tidak boleh kosong!",
                "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int nominal = Integer.parseInt(strNominal);

            if (nominal > sisaBudget) {
                JOptionPane.showMessageDialog(parentFrame,
                    "Pengeluaran melebibi sisa budget bulan ini!",
                    "Peringatan Anggaran", JOptionPane.WARNING_MESSAGE);
                return;
            }

            sisaBudget -= nominal;
            String item = prioritas + " - " + nama + " (Rp " + nominal + ")";
            modelList.addElement(item);

            updateTampilanBudget();
            txtPengeluaran.setText("");
            txtNominal.setText("");
            lblStatus.setText(" Status: Berhasil menambah '" + nama + "' sebesar Rp " + nominal);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parentFrame, 
                "Nominal Pengeluaran harus berupa angka!", 
                "Error Fprmat", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusPengeluaran() {
        int selectedIndex = listPengeluaran.getSelectedIndex();
        if (selectedIndex != -1) {
            String item = modelList.getElementAt(selectedIndex);

            try {
                String strNominal = item.substring(item.lastIndexOf("Rp ") + 3, item.length() - 1);
                int nominalKembali = Integer.parseInt(strNominal);
                sisaBudget += nominalKembali;
            } catch (Exception ex) {}

            modelList.remove(selectedIndex);
            updateTampilanBudget();
            lblStatus.setText(" Status: Pengeluaran berhasil dihapus dan budget dikembalikan.");
        } else {
            JOptionPane.showMessageDialog(parentFrame, 
                "Pilih item pengeluaran yang ingin dihapus!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTampilanBudget() {
        lblSisaBudget.setText("Sisa Budget Bulan Ini: Rp " + String.format("%,d", sisaBudget));
        if (sisaBudget < 200000) {
            lblSisaBudget.setForeground(Color.RED);
        } else {
            lblSisaBudget.setForeground(new Color(0, 128, 0));
        }
    }
}
