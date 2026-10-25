# Smart Wallet & Priority Planner (Budget Rp1.000.000)

Smart Wallet & Priority Planner** adalah aplikasi manajemen keuangan bulanan berbasis *Desktop Graphical User Interface (GUI) 
menggunakan teknologi Java Swing. Aplikasi ini dirancang khusus untuk membantu mahasiswa atau pengguna dalam mengelola 
anggaran bulanan sebesar Rp1.000.000 secara terencana berdasarkan skala prioritas kebutuhan. Seluruh arsitektur antarmuka dibangun secara 
modular dengan membagi komponen sesuai kaidah GUI Swing Java.

# Tinjauan Teori API & GUI Java 
* API (Application Programming Interface): Seperangkat fungsi standar yang disediakan oleh OS atau Bahasa Pemrograman Java yang
  dikelompokkan ke dalam package. Pada proyek ini, package utama yang diimpor adalah `javax.swing.*` dan `java.awt.*`.
* Swing / JFC (Java Foundation Class): Library GUI Java berbasis turunan AWT yang menyediakan komponen lengkap, mendukung
  Pluggable Look-and-Feel, dan direkomendasikan untuk pembelajaran PBO.

# Analisis Rinci Komponen GUI pada Aplikasi
Aplikasi ini dikembangkan dengan mengimplementasikan 5 pilar utama komponen Swing Java:

## 1. Top-Level Container
Merupakan kontainer dasar atau landasan paling utama tempat komponen-komponen GUI lainnya diletakkan. Setiap 
Top-Level Container memiliki content pane yang berfungsi menampung komponen visual.

* JFrame (AppUtama.java):
  * **Fungsi**: Berfungsi sebagai jendela utama (*main frame*) dari aplikasi[cite: 9, 63].
  * **Pengaturan Close Operation**: Menggunakan `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`[cite: 18, 73]. Berdasarkan materi PBO, mode `EXIT_ON_CLOSE` berfungsi untuk menghentikan eksekusi program secara total serta menghapus frame dari memori (*memory allocation freed*) ketika pengguna menutup jendela utama[cite: 19, 21, 74, 76].
* **`JDialog` via `JOptionPane`**:
  * **Fungsi**: Digunakan untuk menampilkan jendela dialog turunan/pop-up interaktif yang bersifat tidak berdiri sendiri (membutuhkan parent frame)[cite: 22, 77].
  * **Penerapan**:
    * `JOptionPane.showMessageDialog()` dengan `JOptionPane.ERROR_MESSAGE` untuk peringatan input kosong atau bukan angka[cite: 24, 79].
    * `JOptionPane.showMessageDialog()` dengan `JOptionPane.WARNING_MESSAGE` saat pengeluaran melebihi sisa budget Rp1.000.000[cite: 24, 79].
    * `JOptionPane.showMessageDialog()` dengan `JOptionPane.INFORMATION_MESSAGE` untuk menampilkan panduan penggunaan[cite: 24, 79].

### 2. Intermediate Container
Merupakan kontainer perantara yang digunakan untuk mengelompokkan, membagi, dan mengatur tata letak komponen-komponen[cite: 9, 63].

* **`JPanel` (`PanelKeuangan.java` & `PanelCatatan.java`)**:
  * **Fungsi**: Digunakan sebagai wadah pengelompokan elemen secara rinci, seperti mengelompokkan form input, area list pengeluaran, dan panel tombol aksi[cite: 9, 63]. Secara default, `JPanel` memiliki layout manager `FlowLayout` sebelum diubah sesuai kebutuhan[cite: 39, 94].
* **`JTabbedPane`**:
  * **Fungsi**: Digunakan untuk menyusun tampilan antarmuka menjadi tumpukan halaman ber-tab[cite: 9, 41, 63, 96]. Dalam materi PBO, komponen ini memfasilitasi konsep `CardLayout` secara praktis, di mana hanya satu panel yang ditampilkan dalam satu waktu[cite: 41, 96].
  * **Pembagian Tab**:
    1. **Tab 1 ("Pengelola Keuangan & Prioritas")**: Memuat panel input transaksi, sisa anggaran, dan daftar riwayat[cite: 9, 13, 63, 67].
    2. **Tab 2 ("Catatan Finansial")**: Memuat panel evaluasi dan perencanaan belanja[cite: 9, 14, 63, 68].
* **`JScrollPane`**:
  * **Fungsi**: Menyelimuti komponen `JList` dan `JTextArea` agar menyediakan bilah gulung (*scroll bar*) dinamis jika jumlah data pengeluaran atau baris catatan melebihi ukuran area tampilan[cite: 9, 63].

### 3. Atomic Component
Komponen tingkat atomik yang memiliki fungsi spesifik dan digunakan langsung oleh pengguna untuk berinteraksi dengan aplikasi[cite: 9, 63].

* **`JLabel`**: Digunakan untuk menampilkan teks stasis/label petunjuk (`lblNama`, `lblNominal`, `lblPrioritas`), teks status di bagian bawah frame (`lblStatus`), serta teks dinamis penunjuk sisa budget (`lblSisaBudget`)[cite: 9, 63, 69]. Method yang digunakan meliputi `setText()` dan `setForeground()`[cite: 69].
* **`JTextField`**: Wadah untuk menerima masukan teks satu baris dari pengguna, yaitu nama pengeluaran (`txtPengeluaran`) dan nominal angka (`txtNominal`)[cite: 9, 16, 63, 71]. Memanfaatkan method `getText()` untuk mengambil nilai dan `setText("")` untuk pengosongan form[cite: 16, 71].
* **`JComboBox`**: Komponen *drop-down selection* yang memberikan opsi tingkat prioritas[cite: 13, 67]:
  * `[Tinggi] Primer`
  * `[Sedang] Sekunder`
  * `[Rendah] Tersier`
* **`JButton`**: Tombol eksekusi aksi interaktif[cite: 9, 15, 63, 70]:
  * `btnTambah`: Memproses kalkulasi dan menambah item pengeluaran.
  * `btnHapus`: Menghapus item terpilih dan mengembalikan saldo anggaran.
  * `btnInfo`: Memunculkan pop-up `JDialog` panduan aplikasi[cite: 9, 22, 63, 77].
* **`JList` & `DefaultListModel`**: Menampilkan daftar komponen entri riwayat pengeluaran yang telah dimasukkan pengguna secara sekuensial[cite: 13, 67].
* **`JTextArea`**: Area input teks multi-baris pada tab catatan finansial untuk mengetik evaluasi anggaran bulanan[cite: 9, 14, 63, 68]. Menggunakan method `setLineWrap(true)` agar teks otomatis berpindah baris saat mencapai batas tepi.

### 4. Layout Manager
Berfungsi untuk mengatur posisi, tata letak, dan ukuran komponen secara otomatis di dalam suatu kontainer[cite: 10, 29, 64, 84].

* **`BorderLayout`**:
  * **Konsep Teori**: Membagi kontainer menjadi 5 daerah utama: `NORTH`, `SOUTH`, `EAST`, `WEST`, dan `CENTER`[cite: 33, 88].
  * **Penerapan**:
    * Digunakan pada `JFrame` utama untuk meletakkan `JTabbedPane` di wilayah `CENTER` dan `lblStatus` di wilayah `SOUTH`[cite: 33, 88].
    * Digunakan pada `PanelKeuangan` untuk menempatkan form input di `NORTH`, list di `CENTER`, dan tombol di `EAST`[cite: 33, 88].
* **`GridLayout`**:
  * **Konsep Teori**: Menyusun komponen dalam matriks baris dan kolom yang berukuran sama rata[cite: 44, 99].
  * **Penerapan**:
    * Form input disusun menggunakan `GridLayout(3, 2, 5, 5)` (3 baris, 2 kolom dengan gap 5px)[cite: 44, 99].
    * Panel tombol aksi disusun menggunakan `GridLayout(3, 1, 5, 5)` secara vertikal seragam[cite: 44, 99].
* **`FlowLayout`**:
  * **Konsep Teori**: Menyusun komponen secara sekuensial berurutan dari kiri ke kanan[cite: 39, 94]. Digunakan secara internal oleh panel dasar.

### 5. Event Handling (Minimal 2 Interface Java)
Mekanisme untuk menangani dan merespons aksi/kejadian (*event*) yang dilakukan oleh pengguna[cite: 4, 10, 57, 64]. Aplikasi mengimplementasikan **2 Interface Listener standar Java**:

1. **`java.awt.event.ActionListener`**:
   * **Deskripsi**: Menangani event pemicu berupa klik mouse pada tombol `JButton`[cite: 10, 64].
   * **Method Implementasi**: `public void actionPerformed(ActionEvent e)`.
   * **Alur Kerja Code**: Memeriksa sumber event (`e.getSource()`)[cite: 4, 57]. Jika tombol `btnTambah` diklik, sistem mengambil teks nominal, mengonversi ke integer, mengurangi `sisaBudget`, serta mengupdate tampilan list dan label saldo secara *real-time*[cite: 4, 10, 64].
2. **`java.awt.event.KeyListener`**:
   * **Deskripsi**: Menangani event interaksi papan ketik (*keyboard*) pada komponen `JTextField`[cite: 10, 64].
   * **Method Implementasi**:
     * `public void keyPressed(KeyEvent e)`: Mendeteksi penekanan tombol `VK_ENTER` pada field nominal untuk menambahkan pengeluaran secara cepat tanpa menekan tombol "Tambah".
     * `public void keyReleased(KeyEvent e)`: Membaca setiap karakter yang selesai diketikkan pengguna untuk memperbarui teks pada `lblStatus` secara *real-time*.
     * `public void keyTyped(KeyEvent e)`: Dipasang sesuai kontrak wajib interface.

