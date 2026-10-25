import javax.swing.*;
import java.awt.*;

public class PanelCatatan extends JPanel{
    
    private JTextArea txtCatatan;

    public PanelCatatan() {
        setLayout(new BorderLayout());

        txtCatatan = new JTextArea();
        txtCatatan.setLineWrap(true);
        txtCatatan.setText("--- CATATAN & EVALUASI FINANSIAL BULANAN ---\n" + 
                           "1. Target Tabungan: Rp 100.000\n" + 
                           "2. Prioritaskan belanja kebutuhan dapur dan kos.\n" + 
                           "3. Hindari belanja impulsif di luar kategori [Tinggi] Primer.");

        add(new JScrollPane(txtCatatan), BorderLayout.CENTER);
    }
}
