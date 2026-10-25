import javax.swing.*;
import java.awt.*;

public class AppUtama extends JFrame {
    private PanelKeuangan panelKeuangan;
    private PanelCatatan panelCatatan;
    private JLabel lblStatus;

    public AppUtama() {
        super("Smart Wallet & Priority Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout(10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();

        lblStatus = new JLabel(" Status: Anggaran awal bulan Rp1.000.000 siap digunakan.");
        lblStatus.setPreferredSize(new Dimension(getWidth(), 25));

        panelKeuangan = new PanelKeuangan(this, lblStatus);
        panelCatatan = new PanelCatatan();

        tabbedPane.addTab("Pengelola Keuangan & Prioritas", panelKeuangan);
        tabbedPane.addTab("Catatan Finansial", panelCatatan);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(lblStatus, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppUtama app = new AppUtama();
            app.setVisible(true);
        });
    }
}