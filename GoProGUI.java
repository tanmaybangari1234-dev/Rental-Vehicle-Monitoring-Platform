import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class GoProGUI extends JFrame {

    // ── Dark Modern Theme Colors ──
    static final Color BG_DARK      = new Color(0x1a, 0x1a, 0x2e);
    static final Color BG_SIDEBAR   = new Color(0x16, 0x21, 0x3e);
    static final Color BG_CARD      = new Color(0x1f, 0x2b, 0x4d);
    static final Color BG_INPUT     = new Color(0x27, 0x37, 0x5e);
    static final Color ACCENT_RED   = new Color(0xe9, 0x45, 0x60);
    static final Color ACCENT_BLUE  = new Color(0x0f, 0x34, 0x60);
    static final Color ACCENT_GREEN = new Color(0x00, 0xc9, 0x7b);
    static final Color ACCENT_GOLD  = new Color(0xff, 0xc1, 0x07);
    static final Color TEXT_WHITE   = new Color(0xea, 0xea, 0xea);
    static final Color TEXT_DIM     = new Color(0x8d, 0x8d, 0xaa);
    static final Color HOVER_BG     = new Color(0x25, 0x35, 0x5a);

    static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 13);
    static final Font FONT_SIDEBAR = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_SIDEBAR_ACTIVE = new Font("Segoe UI", Font.BOLD, 13);

    // ── Shared App State ──
    private final List<User> users = new ArrayList<>();
    private final List<Driver> drivers = new ArrayList<>();
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<RideBooking> bookings = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private final RideMatching matching = new RideMatching();
    private final FareCalculator fareCalc = new FareCalculator();
    private final RideHistory rideHistory = new RideHistory();
    private Wallet wallet;
    private SOSEmergency sos;
    private int userCounter = 1, driverCounter = 1, vehicleCounter = 1, rideCounter = 1, paymentCounter = 1, reviewCounter = 1;

    // ── UI Components ──
    private final JPanel contentPanel;
    private final CardLayout cardLayout;
    private final JPanel sidebarPanel;
    private JButton activeSidebarBtn;

    // Sidebar menu items: label → panel key
    private static final String[][] MENU_ITEMS = {
        {"\u2302", "Dashboard"},        // ⌂
        {"\u263A", "Users"},            // ☺
        {"\u2699", "Drivers"},          // ⚙
        {"\u2708", "Vehicles"},         // ✈
        {"\u279C", "Book Ride"},        // ➜
        {"\u25CE", "Tracking"},         // ◎
        {"\u25B0", "Wallet"},           // ▰
        {"\u25C6", "Payments"},         // ◆
        {"\u2605", "Ratings"},          // ★
        {"\u2630", "History"},          // ☰
        {"\u2709", "Notifications"},    // ✉
        {"\u2764", "Promos"},           // ❤
        {"\u26A0", "SOS"},             // ⚠
        {"\u2691", "Admin"}            // ⚑
    };

    public GoProGUI() {
        super("GoPro - Your Ride, Your Way!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 800);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        // Initialize default data
        initSampleData();

        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        // Sidebar
        sidebarPanel = createSidebar();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);

        // Content area
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BG_DARK);

        contentPanel.add(createDashboardPanel(), "Dashboard");
        contentPanel.add(createUserPanel(), "Users");
        contentPanel.add(createDriverPanel(), "Drivers");
        contentPanel.add(createVehiclePanel(), "Vehicles");
        contentPanel.add(createBookRidePanel(), "Book Ride");
        contentPanel.add(createTrackingPanel(), "Tracking");
        contentPanel.add(createWalletPanel(), "Wallet");
        contentPanel.add(createPaymentPanel(), "Payments");
        contentPanel.add(createRatingPanel(), "Ratings");
        contentPanel.add(createHistoryPanel(), "History");
        contentPanel.add(createNotificationPanel(), "Notifications");
        contentPanel.add(createPromoPanel(), "Promos");
        contentPanel.add(createSOSPanel(), "SOS");
        contentPanel.add(createAdminPanel(), "Admin");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private void initSampleData() {
        wallet = new Wallet("W001", "U001");
        sos = new SOSEmergency("U001", "9111222333");
    }

    // ═══════════════════════════════════════════════
    //  SIDEBAR
    // ═══════════════════════════════════════════════
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0x2a, 0x3a, 0x60)));

        // Logo/Brand
        JPanel brand = new JPanel(new FlowLayout(FlowLayout.CENTER));
        brand.setBackground(BG_SIDEBAR);
        brand.setMaximumSize(new Dimension(180, 60));
        brand.setPreferredSize(new Dimension(180, 60));
        JLabel logo = new JLabel("GO PRO");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(ACCENT_RED);
        brand.add(logo);
        sidebar.add(brand);
        sidebar.add(Box.createVerticalStrut(10));

        // Menu items
        for (int i = 0; i < MENU_ITEMS.length; i++) {
            String icon = MENU_ITEMS[i][0];
            String label = MENU_ITEMS[i][1];
            JButton btn = createSidebarButton(icon + "  " + label, label);
            sidebar.add(btn);
            if (i == 0) {
                activeSidebarBtn = btn;
                btn.setBackground(ACCENT_RED);
                btn.setFont(FONT_SIDEBAR_ACTIVE);
            }
        }

        sidebar.add(Box.createVerticalGlue());

        // Version info at bottom
        JLabel ver = new JLabel("v2.0  |  2026", SwingConstants.CENTER);
        ver.setFont(FONT_SMALL);
        ver.setForeground(TEXT_DIM);
        ver.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(ver);
        sidebar.add(Box.createVerticalStrut(15));

        return sidebar;
    }

    private JButton createSidebarButton(String text, String panelKey) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_SIDEBAR);
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(BG_SIDEBAR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(180, 38));
        btn.setPreferredSize(new Dimension(180, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 10));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn != activeSidebarBtn) btn.setBackground(HOVER_BG);
            }
            public void mouseExited(MouseEvent e) {
                if (btn != activeSidebarBtn) btn.setBackground(BG_SIDEBAR);
            }
        });

        btn.addActionListener(e -> {
            if (activeSidebarBtn != null) {
                activeSidebarBtn.setBackground(BG_SIDEBAR);
                activeSidebarBtn.setFont(FONT_SIDEBAR);
            }
            btn.setBackground(ACCENT_RED);
            btn.setFont(FONT_SIDEBAR_ACTIVE);
            activeSidebarBtn = btn;
            refreshPanel(panelKey);
            cardLayout.show(contentPanel, panelKey);
        });
        return btn;
    }

    // ═══════════════════════════════════════════════
    //  HELPER: Styled Components
    // ═══════════════════════════════════════════════
    private static JPanel makeCard(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x2a, 0x3a, 0x60), 1, true),
            BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));
        if (title != null) {
            JLabel lbl = new JLabel(title);
            lbl.setFont(FONT_SUBTITLE);
            lbl.setForeground(TEXT_WHITE);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(lbl);
            card.add(Box.createVerticalStrut(12));
        }
        return card;
    }

    private static JTextField styledField(String placeholder) {
        JTextField f = new JTextField(18);
        f.setFont(FONT_BODY);
        f.setForeground(TEXT_WHITE);
        f.setBackground(BG_INPUT);
        f.setCaretColor(TEXT_WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x3a, 0x4f, 0x7f), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        f.setToolTipText(placeholder);
        // Placeholder text
        f.setText(placeholder);
        f.setForeground(TEXT_DIM);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(placeholder)) { f.setText(""); f.setForeground(TEXT_WHITE); }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setText(placeholder); f.setForeground(TEXT_DIM); }
            }
        });
        return f;
    }

    private static String getFieldText(JTextField f) {
        String text = f.getText().trim();
        String tip = f.getToolTipText();
        if (text.equals(tip)) return "";
        return text;
    }

    private static JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        btn.addMouseListener(new MouseAdapter() {
            Color original = bg;
            public void mouseEntered(MouseEvent e) { btn.setBackground(bg.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(original); }
        });
        return btn;
    }

    private static JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(FONT_BODY);
        combo.setForeground(TEXT_WHITE);
        combo.setBackground(BG_INPUT);
        combo.setBorder(BorderFactory.createLineBorder(new Color(0x3a, 0x4f, 0x7f)));
        return combo;
    }

    private static JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_DIM);
        return l;
    }

    private static JPanel statCard(String title, String value, Color accent) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, accent),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        card.setPreferredSize(new Dimension(200, 100));
        card.setMaximumSize(new Dimension(220, 110));

        JLabel vLabel = new JLabel(value);
        vLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        vLabel.setForeground(TEXT_WHITE);
        vLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(vLabel);
        card.add(Box.createVerticalStrut(4));

        JLabel tLabel = new JLabel(title);
        tLabel.setFont(FONT_SMALL);
        tLabel.setForeground(TEXT_DIM);
        tLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(tLabel);
        return card;
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_BODY);
        table.setForeground(TEXT_WHITE);
        table.setBackground(BG_CARD);
        table.setGridColor(new Color(0x2a, 0x3a, 0x60));
        table.setSelectionBackground(HOVER_BG);
        table.setSelectionForeground(TEXT_WHITE);
        table.setRowHeight(32);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.getTableHeader().setFont(FONT_BTN);
        table.getTableHeader().setForeground(TEXT_WHITE);
        table.getTableHeader().setBackground(ACCENT_BLUE);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_RED));
        table.setFillsViewportHeight(true);
        return table;
    }

    private JScrollPane styledScrollPane(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        sp.setBackground(BG_CARD);
        sp.getViewport().setBackground(BG_CARD);
        sp.setBorder(BorderFactory.createEmptyBorder());
        return sp;
    }

    private static JPanel formRow(String label, JComponent field) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        row.setBackground(BG_CARD);
        row.setMaximumSize(new Dimension(600, 45));
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_BODY);
        lbl.setForeground(TEXT_DIM);
        lbl.setPreferredSize(new Dimension(120, 25));
        row.add(lbl);
        row.add(field);
        return row;
    }

    private void showMsg(String msg) {
        UIManager.put("OptionPane.background", BG_CARD);
        UIManager.put("Panel.background", BG_CARD);
        UIManager.put("OptionPane.messageForeground", TEXT_WHITE);
        JOptionPane.showMessageDialog(this, msg, "GoPro", JOptionPane.INFORMATION_MESSAGE);
    }

    // ═══════════════════════════════════════════════
    //  PANEL 1: DASHBOARD
    // ═══════════════════════════════════════════════
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Header banner
        JPanel banner = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, ACCENT_RED, getWidth(), getHeight(), ACCENT_BLUE);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setOpaque(false);
        banner.setPreferredSize(new Dimension(0, 120));
        banner.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        JLabel welcomeLabel = new JLabel("Welcome to GO PRO");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        banner.add(welcomeLabel);
        banner.add(Box.createVerticalStrut(6));

        JLabel tagline = new JLabel("Your Ride, Your Way!  \u2022  Manage your ride-hailing platform");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tagline.setForeground(new Color(255, 255, 255, 200));
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);
        banner.add(tagline);

        panel.add(banner, BorderLayout.NORTH);

        // Stats cards
        JPanel statsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        statsRow.setBackground(BG_DARK);
        statsRow.add(statCard("Total Users", String.valueOf(users.size()), ACCENT_BLUE));
        statsRow.add(statCard("Total Drivers", String.valueOf(drivers.size()), ACCENT_GREEN));
        statsRow.add(statCard("Total Rides", String.valueOf(bookings.size()), ACCENT_RED));
        double rev = 0; for (RideBooking b : bookings) if ("COMPLETED".equals(b.getStatus())) rev += b.getFare();
        statsRow.add(statCard("Revenue", "Rs." + String.format("%.0f", rev), ACCENT_GOLD));

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(BG_DARK);
        center.add(statsRow, BorderLayout.NORTH);

        // Quick actions
        JPanel quickCard = makeCard("Quick Actions");
        quickCard.setMaximumSize(new Dimension(900, 160));
        JPanel actionBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        actionBtns.setBackground(BG_CARD);
        actionBtns.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton qBook = styledButton("\u279C  Book a Ride", ACCENT_RED);
        qBook.addActionListener(e -> switchToPanel("Book Ride"));
        JButton qWallet = styledButton("\u25B0  Add Money", ACCENT_GREEN);
        qWallet.addActionListener(e -> switchToPanel("Wallet"));
        JButton qHistory = styledButton("\u2630  Ride History", ACCENT_BLUE);
        qHistory.addActionListener(e -> switchToPanel("History"));
        JButton qAdmin = styledButton("\u2691  Admin Panel", new Color(0x6c, 0x5c, 0xe7));
        qAdmin.addActionListener(e -> switchToPanel("Admin"));
        actionBtns.add(qBook); actionBtns.add(qWallet); actionBtns.add(qHistory); actionBtns.add(qAdmin);
        quickCard.add(actionBtns);

        JPanel quickWrap = new JPanel(new BorderLayout());
        quickWrap.setBackground(BG_DARK);
        quickWrap.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        quickWrap.add(quickCard, BorderLayout.NORTH);
        center.add(quickWrap, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    private void switchToPanel(String panelKey) {
        // Find and activate sidebar button
        for (Component c : sidebarPanel.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                if (btn.getText().contains(panelKey)) {
                    btn.doClick();
                    return;
                }
            }
        }
        cardLayout.show(contentPanel, panelKey);
    }

    // ═══════════════════════════════════════════════
    //  PANEL 2: USERS
    // ═══════════════════════════════════════════════
    private DefaultTableModel userTableModel;
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u263A  User Management");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        // Form card
        JPanel form = makeCard("Register New User");
        JTextField nameF = styledField("Full Name");
        JTextField emailF = styledField("Email");
        JTextField phoneF = styledField("Phone Number");
        JTextField passF = styledField("Password");
        form.add(formRow("Name:", nameF));
        form.add(formRow("Email:", emailF));
        form.add(formRow("Phone:", phoneF));
        form.add(formRow("Password:", passF));
        form.add(Box.createVerticalStrut(10));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(BG_CARD);
        JButton regBtn = styledButton("Register & Verify", ACCENT_RED);
        btnRow.add(regBtn);
        form.add(btnRow);

        // Table
        userTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Verified"}, 0);
        JTable table = styledTable(userTableModel);
        JScrollPane sp = styledScrollPane(table);

        regBtn.addActionListener(e -> {
            String n = getFieldText(nameF), em = getFieldText(emailF), ph = getFieldText(phoneF), pw = getFieldText(passF);
            if (n.isEmpty() || em.isEmpty() || ph.isEmpty() || pw.isEmpty()) { showMsg("Please fill all fields."); return; }
            String id = "U" + String.format("%03d", userCounter++);
            User u = new User(id, n, em, ph, pw);
            u.verifyOTP("123456");
            users.add(u);
            userTableModel.addRow(new Object[]{id, n, em, ph, "Yes"});
            nameF.setText(""); emailF.setText(""); phoneF.setText(""); passF.setText("");
            showMsg("User " + n + " registered successfully!");
        });

        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setBackground(BG_DARK);
        top.add(form, BorderLayout.NORTH);
        top.add(sp, BorderLayout.CENTER);
        panel.add(top, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 3: DRIVERS
    // ═══════════════════════════════════════════════
    private DefaultTableModel driverTableModel;
    private JPanel createDriverPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u2699  Driver Management");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = makeCard("Register New Driver");
        JTextField nameF = styledField("Driver Name");
        JTextField phoneF = styledField("Phone Number");
        JTextField licF = styledField("License Number");
        JTextField locF = styledField("Current Location");
        form.add(formRow("Name:", nameF));
        form.add(formRow("Phone:", phoneF));
        form.add(formRow("License:", licF));
        form.add(formRow("Location:", locF));
        form.add(Box.createVerticalStrut(10));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(BG_CARD);
        JButton regBtn = styledButton("Register & Verify", ACCENT_GREEN);
        JButton onlineBtn = styledButton("Go Online", ACCENT_BLUE);
        btnRow.add(regBtn);
        btnRow.add(onlineBtn);
        form.add(btnRow);

        driverTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "License", "Status", "Rating", "Location"}, 0);
        JTable table = styledTable(driverTableModel);
        JScrollPane sp = styledScrollPane(table);

        regBtn.addActionListener(e -> {
            String n = getFieldText(nameF), ph = getFieldText(phoneF), lic = getFieldText(licF);
            if (n.isEmpty() || ph.isEmpty() || lic.isEmpty()) { showMsg("Please fill Name, Phone and License."); return; }
            String id = "D" + String.format("%03d", driverCounter++);
            Driver d = new Driver(id, n, ph, lic);
            d.verifyDriver();
            drivers.add(d);
            matching.addDriver(d);
            driverTableModel.addRow(new Object[]{id, n, ph, lic, "Verified/Offline", "5.0", "—"});
            showMsg("Driver " + n + " registered and verified!");
        });

        onlineBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { showMsg("Select a driver from the table first."); return; }
            String loc = getFieldText(locF);
            if (loc.isEmpty()) { showMsg("Enter a location."); return; }
            Driver d = drivers.get(row);
            d.goOnline(loc);
            driverTableModel.setValueAt("Online", row, 4);
            driverTableModel.setValueAt(loc, row, 6);

            // Re-add to matching pool if needed
            matching.addDriver(d);
            showMsg(d.getName() + " is now online at " + loc);
        });

        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setBackground(BG_DARK);
        top.add(form, BorderLayout.NORTH);
        top.add(sp, BorderLayout.CENTER);
        panel.add(top, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 4: VEHICLES
    // ═══════════════════════════════════════════════
    private DefaultTableModel vehicleTableModel;
    private JPanel createVehiclePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u2708  Vehicle Management");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = makeCard("Add New Vehicle");
        JComboBox<String> typeCombo = styledCombo(new String[]{"BIKE", "AUTO", "MINI", "SEDAN", "SUV"});
        JTextField numF = styledField("Vehicle Number");
        JTextField modelF = styledField("Model Name");
        JTextField colorF = styledField("Color");
        JComboBox<String> driverCombo = styledCombo(new String[]{"(No drivers registered)"});

        form.add(formRow("Driver:", driverCombo));
        form.add(formRow("Type:", typeCombo));
        form.add(formRow("Number:", numF));
        form.add(formRow("Model:", modelF));
        form.add(formRow("Color:", colorF));
        form.add(Box.createVerticalStrut(10));

        JButton addBtn = styledButton("Add Vehicle", ACCENT_BLUE);
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRow.setBackground(BG_CARD);
        btnRow.add(addBtn);
        form.add(btnRow);

        vehicleTableModel = new DefaultTableModel(new String[]{"ID", "Driver", "Type", "Number", "Model", "Color", "Active"}, 0);
        JTable table = styledTable(vehicleTableModel);
        JScrollPane sp = styledScrollPane(table);

        addBtn.addActionListener(e -> {
            if (drivers.isEmpty()) { showMsg("Register a driver first."); return; }
            String num = getFieldText(numF), model = getFieldText(modelF), color = getFieldText(colorF);
            if (num.isEmpty() || model.isEmpty() || color.isEmpty()) { showMsg("Fill all vehicle details."); return; }
            int driverIdx = driverCombo.getSelectedIndex();
            if (driverIdx < 0 || driverIdx >= drivers.size()) { showMsg("Select a valid driver."); return; }
            String driverId = drivers.get(driverIdx).getDriverId();
            String id = "V" + String.format("%03d", vehicleCounter++);
            String type = (String) typeCombo.getSelectedItem();
            Vehicle v = new Vehicle(id, driverId, type, num, model, color);
            vehicles.add(v);
            vehicleTableModel.addRow(new Object[]{id, driverId, type, num, model, color, "Yes"});
            showMsg("Vehicle " + model + " added!");
        });

        // Tag this combo to refresh when drivers change
        driverCombo.putClientProperty("vehicleDriverCombo", Boolean.TRUE);
        form.putClientProperty("vehicleDriverCombo", driverCombo);

        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setBackground(BG_DARK);
        top.add(form, BorderLayout.NORTH);
        top.add(sp, BorderLayout.CENTER);
        panel.add(top, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 5: BOOK RIDE
    // ═══════════════════════════════════════════════
    private DefaultTableModel bookingTableModel;
    private JLabel fareLabel;
    private JPanel createBookRidePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u279C  Book a Ride");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = makeCard("New Ride");
        JComboBox<String> userCombo = styledCombo(new String[]{"(Register users first)"});
        JTextField pickupF = styledField("Pickup Location");
        JTextField destF = styledField("Destination");
        JTextField distF = styledField("Distance (km)");
        JComboBox<String> typeCombo = styledCombo(new String[]{"BIKE", "AUTO", "MINI", "SEDAN", "SUV"});

        form.add(formRow("User:", userCombo));
        form.add(formRow("Pickup:", pickupF));
        form.add(formRow("Destination:", destF));
        form.add(formRow("Distance:", distF));
        form.add(formRow("Ride Type:", typeCombo));
        form.add(Box.createVerticalStrut(8));

        fareLabel = new JLabel("  Estimated Fare: —");
        fareLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        fareLabel.setForeground(ACCENT_GREEN);
        form.add(fareLabel);
        form.add(Box.createVerticalStrut(10));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btnRow.setBackground(BG_CARD);
        JButton estBtn = styledButton("Estimate Fare", ACCENT_BLUE);
        JButton bookBtn = styledButton("Book Ride", ACCENT_RED);
        btnRow.add(estBtn); btnRow.add(bookBtn);
        form.add(btnRow);

        userCombo.putClientProperty("bookUserCombo", Boolean.TRUE);
        form.putClientProperty("bookUserCombo", userCombo);

        bookingTableModel = new DefaultTableModel(new String[]{"ID", "User", "Pickup", "Destination", "Type", "Status", "Fare", "Driver"}, 0);
        JTable table = styledTable(bookingTableModel);
        JScrollPane sp = styledScrollPane(table);

        estBtn.addActionListener(e -> {
            String distStr = getFieldText(distF);
            if (distStr.isEmpty()) { showMsg("Enter distance in km."); return; }
            try {
                double dist = Double.parseDouble(distStr);
                String type = (String) typeCombo.getSelectedItem();
                double fare = fareCalc.calculateFare(type, dist);
                fareLabel.setText("  Estimated Fare: Rs." + String.format("%.2f", fare));
            } catch (NumberFormatException ex) { showMsg("Invalid distance."); }
        });

        bookBtn.addActionListener(e -> {
            if (users.isEmpty()) { showMsg("Register a user first."); return; }
            String pickup = getFieldText(pickupF), dest = getFieldText(destF), distStr = getFieldText(distF);
            if (pickup.isEmpty() || dest.isEmpty() || distStr.isEmpty()) { showMsg("Fill all ride details."); return; }
            double dist;
            try { dist = Double.parseDouble(distStr); } catch (NumberFormatException ex) { showMsg("Invalid distance."); return; }
            int ui = userCombo.getSelectedIndex();
            if (ui < 0 || ui >= users.size()) { showMsg("Select a user."); return; }
            String type = (String) typeCombo.getSelectedItem();
            String userId = users.get(ui).getUserId();
            String rideId = "R" + String.format("%03d", rideCounter++);

            RideBooking ride = new RideBooking(rideId, userId, pickup, dest, type);
            double fare = fareCalc.calculateFare(type, dist);

            Driver matched = matching.findNearestDriver(type, pickup);
            String driverName = "—";
            if (matched != null) {
                ride.confirmRide(matched.getDriverId(), fare);
                driverName = matched.getName();
            } else {
                ride.confirmRide("—", fare);
            }
            bookings.add(ride);
            rideHistory.addRide(ride);
            bookingTableModel.addRow(new Object[]{rideId, userId, pickup, dest, type, ride.getStatus(), "Rs." + String.format("%.2f", fare), driverName});
            Notification.sendRideUpdate(userId, rideId, ride.getStatus());
            showMsg("Ride " + rideId + " booked! Fare: Rs." + String.format("%.2f", fare) + (matched != null ? " | Driver: " + driverName : ""));
        });

        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setBackground(BG_DARK);
        top.add(form, BorderLayout.NORTH);
        top.add(sp, BorderLayout.CENTER);
        panel.add(top, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 6: RIDE TRACKING
    // ═══════════════════════════════════════════════
    private JPanel createTrackingPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u25CE  Ride Tracking");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel card = makeCard("Track Active Ride");

        JComboBox<String> rideCombo = styledCombo(new String[]{"(No rides yet)"});
        card.add(formRow("Select Ride:", rideCombo));
        card.add(Box.createVerticalStrut(8));

        JLabel statusLabel = new JLabel("  Status: —");
        statusLabel.setFont(FONT_SUBTITLE);
        statusLabel.setForeground(ACCENT_GOLD);
        card.add(statusLabel);
        card.add(Box.createVerticalStrut(6));

        JProgressBar progress = new JProgressBar(0, 100);
        progress.setStringPainted(true);
        progress.setFont(FONT_BODY);
        progress.setForeground(ACCENT_GREEN);
        progress.setBackground(BG_INPUT);
        progress.setMaximumSize(new Dimension(500, 28));
        progress.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(progress);
        card.add(Box.createVerticalStrut(6));

        JLabel locLabel = new JLabel("  Driver Location: —");
        locLabel.setFont(FONT_BODY);
        locLabel.setForeground(TEXT_DIM);
        card.add(locLabel);
        card.add(Box.createVerticalStrut(15));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btnRow.setBackground(BG_CARD);
        JButton startBtn = styledButton("Start Ride", ACCENT_GREEN);
        JButton updateBtn = styledButton("Update Location", ACCENT_BLUE);
        JButton completeBtn = styledButton("Complete Ride", ACCENT_RED);
        btnRow.add(startBtn); btnRow.add(updateBtn); btnRow.add(completeBtn);
        card.add(btnRow);

        rideCombo.putClientProperty("trackRideCombo", Boolean.TRUE);
        card.putClientProperty("trackRideCombo", rideCombo);
        card.putClientProperty("trackStatusLabel", statusLabel);
        card.putClientProperty("trackProgress", progress);
        card.putClientProperty("trackLocLabel", locLabel);

        startBtn.addActionListener(e -> {
            int idx = rideCombo.getSelectedIndex();
            if (idx < 0 || idx >= bookings.size()) { showMsg("Select a ride."); return; }
            RideBooking ride = bookings.get(idx);
            ride.startRide();
            statusLabel.setText("  Status: " + ride.getStatus());
            progress.setValue(0);
            locLabel.setText("  Driver Location: " + ride.getPickup());
            // Update booking table
            refreshBookingTable();
            showMsg("Ride " + ride.getBookingId() + " started!");
        });

        updateBtn.addActionListener(e -> {
            int idx = rideCombo.getSelectedIndex();
            if (idx < 0 || idx >= bookings.size()) return;
            RideBooking ride = bookings.get(idx);
            int cur = progress.getValue();
            int next = Math.min(cur + 33, 90);
            progress.setValue(next);
            locLabel.setText("  Driver Location: En route... " + next + "% complete");
        });

        completeBtn.addActionListener(e -> {
            int idx = rideCombo.getSelectedIndex();
            if (idx < 0 || idx >= bookings.size()) { showMsg("Select a ride."); return; }
            RideBooking ride = bookings.get(idx);
            ride.completeRide();
            statusLabel.setText("  Status: COMPLETED");
            progress.setValue(100);
            locLabel.setText("  Driver Location: " + ride.getDestination() + " (Arrived)");
            Notification.sendRideUpdate(ride.getUserId(), ride.getBookingId(), "COMPLETED");
            refreshBookingTable();
            showMsg("Ride " + ride.getBookingId() + " completed!");
        });

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 7: WALLET
    // ═══════════════════════════════════════════════
    private JLabel walletBalLabel;
    private DefaultTableModel walletTxnModel;
    private JPanel createWalletPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u25B0  Wallet");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel balCard = new JPanel();
        balCard.setLayout(new BoxLayout(balCard, BoxLayout.Y_AXIS));
        balCard.setBackground(BG_CARD);
        balCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, ACCENT_GREEN),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        JLabel balTitle = new JLabel("Wallet Balance");
        balTitle.setFont(FONT_BODY);
        balTitle.setForeground(TEXT_DIM);
        walletBalLabel = new JLabel("Rs. " + String.format("%.2f", wallet.getBalance()));
        walletBalLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        walletBalLabel.setForeground(ACCENT_GREEN);
        balCard.add(balTitle);
        balCard.add(Box.createVerticalStrut(4));
        balCard.add(walletBalLabel);

        JPanel addCard = makeCard("Add Money");
        JTextField amtF = styledField("Amount (Rs.)");
        addCard.add(formRow("Amount:", amtF));
        addCard.add(Box.createVerticalStrut(8));
        JButton addBtn = styledButton("Add to Wallet", ACCENT_GREEN);
        JButton deductBtn = styledButton("Deduct", ACCENT_RED);
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(BG_CARD);
        btnRow.add(addBtn); btnRow.add(deductBtn);
        addCard.add(btnRow);

        walletTxnModel = new DefaultTableModel(new String[]{"#", "Type", "Amount", "Balance After"}, 0);
        JTable txnTable = styledTable(walletTxnModel);
        JScrollPane sp = styledScrollPane(txnTable);

        addBtn.addActionListener(e -> {
            String amtStr = getFieldText(amtF);
            if (amtStr.isEmpty()) { showMsg("Enter amount."); return; }
            try {
                double amt = Double.parseDouble(amtStr);
                wallet.addMoney(amt);
                walletBalLabel.setText("Rs. " + String.format("%.2f", wallet.getBalance()));
                walletTxnModel.addRow(new Object[]{walletTxnModel.getRowCount() + 1, "CREDIT", "+ Rs." + String.format("%.2f", amt), "Rs." + String.format("%.2f", wallet.getBalance())});
                amtF.setText("");
            } catch (NumberFormatException ex) { showMsg("Invalid amount."); }
        });

        deductBtn.addActionListener(e -> {
            String amtStr = getFieldText(amtF);
            if (amtStr.isEmpty()) { showMsg("Enter amount."); return; }
            try {
                double amt = Double.parseDouble(amtStr);
                if (wallet.deductMoney(amt)) {
                    walletBalLabel.setText("Rs. " + String.format("%.2f", wallet.getBalance()));
                    walletTxnModel.addRow(new Object[]{walletTxnModel.getRowCount() + 1, "DEBIT", "- Rs." + String.format("%.2f", amt), "Rs." + String.format("%.2f", wallet.getBalance())});
                } else {
                    showMsg("Insufficient balance!");
                }
                amtF.setText("");
            } catch (NumberFormatException ex) { showMsg("Invalid amount."); }
        });

        JPanel topCards = new JPanel(new GridLayout(1, 2, 15, 0));
        topCards.setBackground(BG_DARK);
        topCards.add(balCard);
        topCards.add(addCard);

        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setBackground(BG_DARK);
        center.add(topCards, BorderLayout.NORTH);
        center.add(sp, BorderLayout.CENTER);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 8: PAYMENTS
    // ═══════════════════════════════════════════════
    private DefaultTableModel paymentTableModel;
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u25C6  Payments");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = makeCard("Process Payment");
        JComboBox<String> rideCombo = styledCombo(new String[]{"(No rides)"});
        JComboBox<String> methodCombo = styledCombo(new String[]{"UPI", "CARD", "WALLET", "CASH"});
        form.add(formRow("Ride:", rideCombo));
        form.add(formRow("Method:", methodCombo));
        form.add(Box.createVerticalStrut(10));

        JButton payBtn = styledButton("Process Payment", ACCENT_GREEN);
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRow.setBackground(BG_CARD);
        btnRow.add(payBtn);
        form.add(btnRow);

        rideCombo.putClientProperty("payRideCombo", Boolean.TRUE);
        form.putClientProperty("payRideCombo", rideCombo);

        paymentTableModel = new DefaultTableModel(new String[]{"Payment ID", "Ride", "Amount", "Method", "Status"}, 0);
        JTable table = styledTable(paymentTableModel);
        JScrollPane sp = styledScrollPane(table);

        payBtn.addActionListener(e -> {
            int idx = rideCombo.getSelectedIndex();
            if (idx < 0 || idx >= bookings.size()) { showMsg("Select a ride."); return; }
            RideBooking ride = bookings.get(idx);
            if (ride.getFare() <= 0) { showMsg("Ride has no fare."); return; }
            String method = (String) methodCombo.getSelectedItem();
            String pid = "P" + String.format("%03d", paymentCounter++);
            Payment p = new Payment(pid, ride.getBookingId(), ride.getUserId(), ride.getFare(), method);
            p.processPayment();
            payments.add(p);
            paymentTableModel.addRow(new Object[]{pid, ride.getBookingId(), "Rs." + String.format("%.2f", ride.getFare()), method, "SUCCESS"});
            Notification.sendPaymentNotification(ride.getUserId(), ride.getFare(), "SUCCESS");
            showMsg("Payment " + pid + " processed: Rs." + String.format("%.2f", ride.getFare()) + " via " + method);
        });

        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setBackground(BG_DARK);
        top.add(form, BorderLayout.NORTH);
        top.add(sp, BorderLayout.CENTER);
        panel.add(top, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 9: RATINGS
    // ═══════════════════════════════════════════════
    private DefaultTableModel reviewTableModel;
    private JPanel createRatingPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u2605  Ratings & Reviews");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = makeCard("Leave a Review");
        JComboBox<String> rideCombo = styledCombo(new String[]{"(No rides)"});
        JComboBox<String> ratingCombo = styledCombo(new String[]{"5 - Excellent", "4 - Good", "3 - Average", "2 - Poor", "1 - Terrible"});
        JTextField commentF = styledField("Write your review...");
        form.add(formRow("Ride:", rideCombo));
        form.add(formRow("Rating:", ratingCombo));
        form.add(formRow("Comment:", commentF));
        form.add(Box.createVerticalStrut(10));

        JButton submitBtn = styledButton("Submit Review", ACCENT_GOLD);
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRow.setBackground(BG_CARD);
        btnRow.add(submitBtn);
        form.add(btnRow);

        rideCombo.putClientProperty("ratingRideCombo", Boolean.TRUE);
        form.putClientProperty("ratingRideCombo", rideCombo);

        // Star display
        JPanel starPanel = makeCard("Rating Stars");
        JLabel starDisplay = new JLabel("\u2606 \u2606 \u2606 \u2606 \u2606");
        starDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        starDisplay.setForeground(ACCENT_GOLD);
        starPanel.add(starDisplay);

        ratingCombo.addActionListener(e -> {
            int sel = ratingCombo.getSelectedIndex();
            int stars = 5 - sel;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5; i++) sb.append(i < stars ? "\u2605 " : "\u2606 ");
            starDisplay.setText(sb.toString());
        });

        reviewTableModel = new DefaultTableModel(new String[]{"Ride", "Reviewer", "Reviewee", "Stars", "Comment"}, 0);
        JTable table = styledTable(reviewTableModel);
        JScrollPane sp = styledScrollPane(table);

        submitBtn.addActionListener(e -> {
            int idx = rideCombo.getSelectedIndex();
            if (idx < 0 || idx >= bookings.size()) { showMsg("Select a ride."); return; }
            RideBooking ride = bookings.get(idx);
            int stars = 5 - ratingCombo.getSelectedIndex();
            String comment = getFieldText(commentF);
            if (comment.isEmpty()) comment = "(No comment)";
            String revId = "REV" + String.format("%03d", reviewCounter++);
            RatingReview review = new RatingReview(revId, ride.getBookingId(), ride.getUserId(),
                ride.getDriverId() != null ? ride.getDriverId() : "—", stars, comment);
            review.submitReview();
            reviewTableModel.addRow(new Object[]{ride.getBookingId(), ride.getUserId(), ride.getDriverId(), stars + " \u2605", comment});
            showMsg("Review submitted: " + stars + " stars!");
        });

        JPanel topArea = new JPanel(new GridLayout(1, 2, 15, 0));
        topArea.setBackground(BG_DARK);
        topArea.add(form);
        topArea.add(starPanel);

        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setBackground(BG_DARK);
        center.add(topArea, BorderLayout.NORTH);
        center.add(sp, BorderLayout.CENTER);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 10: RIDE HISTORY
    // ═══════════════════════════════════════════════
    private DefaultTableModel historyTableModel;
    private JLabel totalSpentLabel;
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u2630  Ride History");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel statsBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        statsBar.setBackground(BG_DARK);
        totalSpentLabel = new JLabel("Total Spent: Rs. 0.00");
        totalSpentLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalSpentLabel.setForeground(ACCENT_GREEN);
        statsBar.add(totalSpentLabel);

        historyTableModel = new DefaultTableModel(new String[]{"Ride ID", "Pickup", "Destination", "Type", "Status", "Fare"}, 0);
        JTable table = styledTable(historyTableModel);
        JScrollPane sp = styledScrollPane(table);

        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setBackground(BG_DARK);
        center.add(statsBar, BorderLayout.NORTH);
        center.add(sp, BorderLayout.CENTER);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 11: NOTIFICATIONS
    // ═══════════════════════════════════════════════
    private DefaultTableModel notifTableModel;
    private JPanel createNotificationPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u2709  Notifications");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel card = makeCard("Send Custom Notification");
        JComboBox<String> userCombo = styledCombo(new String[]{"(No users)"});
        JTextField titleF = styledField("Notification Title");
        JTextField msgF = styledField("Message");
        JComboBox<String> typeCombo = styledCombo(new String[]{"RIDE_UPDATE", "PAYMENT", "PROMO", "SAFETY", "GENERAL"});
        card.add(formRow("User:", userCombo));
        card.add(formRow("Title:", titleF));
        card.add(formRow("Message:", msgF));
        card.add(formRow("Type:", typeCombo));
        card.add(Box.createVerticalStrut(8));

        userCombo.putClientProperty("notifUserCombo", Boolean.TRUE);
        card.putClientProperty("notifUserCombo", userCombo);

        JButton sendBtn = styledButton("Send Notification", ACCENT_BLUE);
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRow.setBackground(BG_CARD);
        btnRow.add(sendBtn);
        card.add(btnRow);

        notifTableModel = new DefaultTableModel(new String[]{"Type", "Title", "Message", "User"}, 0);
        JTable table = styledTable(notifTableModel);
        JScrollPane sp = styledScrollPane(table);

        sendBtn.addActionListener(e -> {
            if (users.isEmpty()) { showMsg("Register a user first."); return; }
            String t = getFieldText(titleF), m = getFieldText(msgF);
            if (t.isEmpty() || m.isEmpty()) { showMsg("Fill title and message."); return; }
            int ui = userCombo.getSelectedIndex();
            String userId = users.get(ui).getUserId();
            String type = (String) typeCombo.getSelectedItem();
            Notification n = new Notification("N" + System.currentTimeMillis(), userId, t, m, type);
            n.send();
            notifTableModel.addRow(new Object[]{type, t, m, userId});
            showMsg("Notification sent!");
        });

        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setBackground(BG_DARK);
        top.add(card, BorderLayout.NORTH);
        top.add(sp, BorderLayout.CENTER);
        panel.add(top, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 12: PROMO CODES
    // ═══════════════════════════════════════════════
    private DefaultTableModel promoTableModel;
    private JPanel createPromoPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u2764  Promo Codes");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = makeCard("Create Promo Code");
        JTextField codeF = styledField("Promo Code");
        JTextField discF = styledField("Discount %");
        JTextField maxF = styledField("Max Discount (Rs.)");
        JTextField minF = styledField("Min Order (Rs.)");
        JTextField limitF = styledField("Usage Limit");
        form.add(formRow("Code:", codeF));
        form.add(formRow("Discount %:", discF));
        form.add(formRow("Max Discount:", maxF));
        form.add(formRow("Min Order:", minF));
        form.add(formRow("Usage Limit:", limitF));
        form.add(Box.createVerticalStrut(10));

        JButton createBtn = styledButton("Create Promo", ACCENT_RED);
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRow.setBackground(BG_CARD);
        btnRow.add(createBtn);
        form.add(btnRow);

        // Apply promo card
        JPanel applyCard = makeCard("Apply Promo Code");
        JTextField applyCodeF = styledField("Enter Promo Code");
        JTextField applyFareF = styledField("Fare Amount (Rs.)");
        JLabel discountLabel = new JLabel("  Discount: —");
        discountLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        discountLabel.setForeground(ACCENT_GREEN);
        applyCard.add(formRow("Code:", applyCodeF));
        applyCard.add(formRow("Fare:", applyFareF));
        applyCard.add(Box.createVerticalStrut(6));
        applyCard.add(discountLabel);
        applyCard.add(Box.createVerticalStrut(8));
        JButton applyBtn = styledButton("Apply", ACCENT_GREEN);
        JPanel applyRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        applyRow.setBackground(BG_CARD);
        applyRow.add(applyBtn);
        applyCard.add(applyRow);

        promoTableModel = new DefaultTableModel(new String[]{"Code", "Discount %", "Max", "Min Order", "Limit", "Used", "Active"}, 0);
        JTable table = styledTable(promoTableModel);
        JScrollPane sp = styledScrollPane(table);

        List<PromoCode> promos = new ArrayList<>();

        createBtn.addActionListener(e -> {
            String code = getFieldText(codeF);
            if (code.isEmpty()) { showMsg("Enter a promo code."); return; }
            try {
                int disc = Integer.parseInt(getFieldText(discF));
                double max = Double.parseDouble(getFieldText(maxF));
                double min = Double.parseDouble(getFieldText(minF));
                int limit = Integer.parseInt(getFieldText(limitF));
                PromoCode p = new PromoCode(code, disc, max, min, limit, LocalDateTime.now().plusDays(30));
                p.addPromo();
                promos.add(p);
                promoTableModel.addRow(new Object[]{code, disc + "%", "Rs." + String.format("%.0f", max), "Rs." + String.format("%.0f", min), limit, 0, "Yes"});
                showMsg("Promo code '" + code + "' created!");
            } catch (NumberFormatException ex) { showMsg("Invalid numbers."); }
        });

        applyBtn.addActionListener(e -> {
            String code = getFieldText(applyCodeF);
            String fareStr = getFieldText(applyFareF);
            if (code.isEmpty() || fareStr.isEmpty()) { showMsg("Enter code and fare."); return; }
            try {
                double fare = Double.parseDouble(fareStr);
                for (PromoCode p : promos) {
                    if (p.getCode().equalsIgnoreCase(code)) {
                        double disc = p.applyPromo(fare);
                        if (disc > 0) {
                            discountLabel.setText("  Discount: Rs." + String.format("%.2f", disc) + " | New fare: Rs." + String.format("%.2f", fare - disc));
                        }
                        return;
                    }
                }
                showMsg("Promo code not found.");
            } catch (NumberFormatException ex) { showMsg("Invalid fare."); }
        });

        JPanel topCards = new JPanel(new GridLayout(1, 2, 15, 0));
        topCards.setBackground(BG_DARK);
        topCards.add(form);
        topCards.add(applyCard);

        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setBackground(BG_DARK);
        center.add(topCards, BorderLayout.NORTH);
        center.add(sp, BorderLayout.CENTER);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 13: SOS EMERGENCY
    // ═══════════════════════════════════════════════
    private JPanel createSOSPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u26A0  SOS Emergency");
        title.setFont(FONT_TITLE);
        title.setForeground(ACCENT_RED);
        panel.add(title, BorderLayout.NORTH);

        JPanel settingsCard = makeCard("Emergency Settings");
        JTextField contactF = styledField("Emergency Contact Number");
        settingsCard.add(formRow("Contact:", contactF));
        settingsCard.add(Box.createVerticalStrut(8));
        JButton updateBtn = styledButton("Update Contact", ACCENT_BLUE);
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.setBackground(BG_CARD);
        row1.add(updateBtn);
        settingsCard.add(row1);

        updateBtn.addActionListener(e -> {
            String c = getFieldText(contactF);
            if (!c.isEmpty()) {
                sos.updateEmergencyContact(c);
                showMsg("Emergency contact updated to " + c);
            }
        });

        // SOS Trigger Panel
        JPanel sosCard = new JPanel();
        sosCard.setLayout(new BoxLayout(sosCard, BoxLayout.Y_AXIS));
        sosCard.setBackground(new Color(0x3d, 0x0c, 0x11));
        sosCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_RED, 2, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        JLabel sosTitle = new JLabel("\u26A0  EMERGENCY SOS");
        sosTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        sosTitle.setForeground(ACCENT_RED);
        sosTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sosCard.add(sosTitle);
        sosCard.add(Box.createVerticalStrut(10));

        JLabel sosDesc = new JLabel("Press the button below to alert emergency services");
        sosDesc.setFont(FONT_BODY);
        sosDesc.setForeground(TEXT_DIM);
        sosDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        sosCard.add(sosDesc);
        sosCard.add(Box.createVerticalStrut(20));

        JButton sosBtn = new JButton("SOS") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public Dimension getPreferredSize() { return new Dimension(120, 120); }
        };
        sosBtn.setFont(new Font("Segoe UI", Font.BOLD, 28));
        sosBtn.setForeground(Color.WHITE);
        sosBtn.setBackground(ACCENT_RED);
        sosBtn.setFocusPainted(false);
        sosBtn.setBorderPainted(false);
        sosBtn.setContentAreaFilled(false);
        sosBtn.setOpaque(false);
        sosBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sosBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusLabel = new JLabel("Status: " + sos.getStatus());
        statusLabel.setFont(FONT_SUBTITLE);
        statusLabel.setForeground(ACCENT_GOLD);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sosBtn.addActionListener(e -> {
            String rideId = bookings.isEmpty() ? "—" : bookings.get(bookings.size() - 1).getBookingId();
            sos.triggerSOS(rideId, "Current GPS Location");
            statusLabel.setText("Status: TRIGGERED  \u26A0");
            statusLabel.setForeground(ACCENT_RED);
            showMsg("SOS TRIGGERED! Emergency contact notified. Help is on the way!");
        });

        sosCard.add(sosBtn);
        sosCard.add(Box.createVerticalStrut(20));

        JPanel resolveRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        resolveRow.setBackground(new Color(0x3d, 0x0c, 0x11));
        JButton ackBtn = styledButton("Acknowledge", ACCENT_GOLD);
        JButton resBtn = styledButton("Resolve", ACCENT_GREEN);
        ackBtn.addActionListener(e -> {
            sos.acknowledgeSOS();
            statusLabel.setText("Status: ACKNOWLEDGED");
            statusLabel.setForeground(ACCENT_GOLD);
        });
        resBtn.addActionListener(e -> {
            sos.resolveSOS("Resolved by user.");
            statusLabel.setText("Status: RESOLVED  \u2714");
            statusLabel.setForeground(ACCENT_GREEN);
        });
        resolveRow.add(ackBtn);
        resolveRow.add(resBtn);
        sosCard.add(resolveRow);
        sosCard.add(Box.createVerticalStrut(15));
        sosCard.add(statusLabel);

        JPanel topCards = new JPanel(new GridLayout(1, 2, 15, 0));
        topCards.setBackground(BG_DARK);
        topCards.add(settingsCard);
        topCards.add(sosCard);
        panel.add(topCards, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  PANEL 14: ADMIN DASHBOARD
    // ═══════════════════════════════════════════════
    private JPanel adminStatsPanel;
    private DefaultTableModel adminDriverModel, adminUserModel;
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("\u2691  Admin Dashboard");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_WHITE);
        panel.add(title, BorderLayout.NORTH);

        // Stats
        adminStatsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        adminStatsPanel.setBackground(BG_DARK);

        // Driver management
        JPanel driverCard = makeCard("All Drivers");
        adminDriverModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Status", "Rating", "Trips"}, 0);
        JTable dTable = styledTable(adminDriverModel);
        JScrollPane dSp = styledScrollPane(dTable);
        dSp.setPreferredSize(new Dimension(0, 150));
        driverCard.add(dSp);

        JPanel dBtnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        dBtnRow.setBackground(BG_CARD);
        JButton blockBtn = styledButton("Block Driver", ACCENT_RED);
        JButton approveBtn = styledButton("Approve Driver", ACCENT_GREEN);
        dBtnRow.add(blockBtn); dBtnRow.add(approveBtn);
        driverCard.add(dBtnRow);

        blockBtn.addActionListener(e -> {
            int row = dTable.getSelectedRow();
            if (row < 0) { showMsg("Select a driver."); return; }
            Driver d = drivers.get(row);
            d.goOffline();
            adminDriverModel.setValueAt("BLOCKED", row, 3);
            showMsg("Driver " + d.getName() + " blocked.");
        });

        approveBtn.addActionListener(e -> {
            int row = dTable.getSelectedRow();
            if (row < 0) { showMsg("Select a driver."); return; }
            Driver d = drivers.get(row);
            d.verifyDriver();
            adminDriverModel.setValueAt("Verified", row, 3);
            showMsg("Driver " + d.getName() + " approved.");
        });

        // User list
        JPanel userCard = makeCard("All Users");
        adminUserModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Verified"}, 0);
        JTable uTable = styledTable(adminUserModel);
        JScrollPane uSp = styledScrollPane(uTable);
        uSp.setPreferredSize(new Dimension(0, 150));
        userCard.add(uSp);

        // Revenue
        JPanel revCard = makeCard("Revenue Report");
        double totalRev = 0; for (RideBooking b : bookings) if ("COMPLETED".equals(b.getStatus())) totalRev += b.getFare();
        JLabel dailyL = new JLabel("  Today:  Rs." + String.format("%.2f", totalRev));
        dailyL.setFont(FONT_SUBTITLE); dailyL.setForeground(ACCENT_GREEN);
        JLabel weeklyL = new JLabel("  This Week:  Rs." + String.format("%.2f", totalRev * 5));
        weeklyL.setFont(FONT_SUBTITLE); weeklyL.setForeground(ACCENT_BLUE);
        JLabel monthlyL = new JLabel("  This Month:  Rs." + String.format("%.2f", totalRev * 22));
        monthlyL.setFont(FONT_SUBTITLE); monthlyL.setForeground(ACCENT_GOLD);
        revCard.add(dailyL); revCard.add(Box.createVerticalStrut(6));
        revCard.add(weeklyL); revCard.add(Box.createVerticalStrut(6));
        revCard.add(monthlyL);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        tablesPanel.setBackground(BG_DARK);
        tablesPanel.add(driverCard);
        tablesPanel.add(userCard);

        JPanel topWithStats = new JPanel(new BorderLayout(0, 15));
        topWithStats.setBackground(BG_DARK);
        topWithStats.add(adminStatsPanel, BorderLayout.NORTH);
        topWithStats.add(tablesPanel, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setBackground(BG_DARK);
        center.add(topWithStats, BorderLayout.CENTER);
        center.add(revCard, BorderLayout.SOUTH);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════
    //  REFRESH LOGIC
    // ═══════════════════════════════════════════════
    private void refreshPanel(String panelKey) {
        switch (panelKey) {
            case "Dashboard": refreshDashboard(); break;
            case "Vehicles": refreshDriverCombo("vehicleDriverCombo"); break;
            case "Book Ride": refreshUserCombo("bookUserCombo"); refreshBookingTable(); break;
            case "Tracking": refreshRideCombo("trackRideCombo"); break;
            case "Wallet": if (walletBalLabel != null) walletBalLabel.setText("Rs. " + String.format("%.2f", wallet.getBalance())); break;
            case "Payments": refreshRideCombo("payRideCombo"); break;
            case "Ratings": refreshRideCombo("ratingRideCombo"); break;
            case "History": refreshHistoryTable(); break;
            case "Notifications": refreshUserCombo("notifUserCombo"); break;
            case "Admin": refreshAdmin(); break;
        }
    }

    private void refreshDashboard() {
        // Rebuild dashboard by swapping card
        contentPanel.remove(0);
        contentPanel.add(createDashboardPanel(), "Dashboard", 0);
        contentPanel.revalidate();
    }

    private void refreshDriverCombo(String key) {
        findCombo(key, combo -> {
            combo.removeAllItems();
            if (drivers.isEmpty()) combo.addItem("(No drivers)");
            else for (Driver d : drivers) combo.addItem(d.getDriverId() + " - " + d.getName());
        });
    }

    private void refreshUserCombo(String key) {
        findCombo(key, combo -> {
            combo.removeAllItems();
            if (users.isEmpty()) combo.addItem("(No users)");
            else for (User u : users) combo.addItem(u.getUserId() + " - " + u.getName());
        });
    }

    private void refreshRideCombo(String key) {
        findCombo(key, combo -> {
            combo.removeAllItems();
            if (bookings.isEmpty()) combo.addItem("(No rides)");
            else for (RideBooking b : bookings) combo.addItem(b.getBookingId() + " | " + b.getPickup() + " → " + b.getDestination() + " [" + b.getStatus() + "]");
        });
    }

    @SuppressWarnings("unchecked")
    private void findCombo(String key, java.util.function.Consumer<JComboBox<String>> action) {
        for (int i = 0; i < contentPanel.getComponentCount(); i++) {
            JComboBox<String> combo = findComboInComponent(contentPanel.getComponent(i), key);
            if (combo != null) { action.accept(combo); return; }
        }
    }

    @SuppressWarnings("unchecked")
    private JComboBox<String> findComboInComponent(Component comp, String key) {
        if (comp instanceof JPanel) {
            JPanel p = (JPanel) comp;
            Object val = p.getClientProperty(key);
            if (val instanceof JComboBox) return (JComboBox<String>) val;
            for (Component c : p.getComponents()) {
                JComboBox<String> result = findComboInComponent(c, key);
                if (result != null) return result;
            }
        }
        return null;
    }

    private void refreshBookingTable() {
        if (bookingTableModel == null) return;
        bookingTableModel.setRowCount(0);
        for (RideBooking b : bookings) {
            String driverName = "—";
            if (b.getDriverId() != null) {
                for (Driver d : drivers) {
                    if (d.getDriverId().equals(b.getDriverId())) { driverName = d.getName(); break; }
                }
            }
            bookingTableModel.addRow(new Object[]{b.getBookingId(), b.getUserId(), b.getPickup(), b.getDestination(),
                b.getRideType(), b.getStatus(), "Rs." + String.format("%.2f", b.getFare()), driverName});
        }
    }

    private void refreshHistoryTable() {
        if (historyTableModel == null) return;
        historyTableModel.setRowCount(0);
        double total = 0;
        for (RideBooking b : bookings) {
            historyTableModel.addRow(new Object[]{b.getBookingId(), b.getPickup(), b.getDestination(),
                b.getRideType(), b.getStatus(), "Rs." + String.format("%.2f", b.getFare())});
            if ("COMPLETED".equals(b.getStatus())) total += b.getFare();
        }
        if (totalSpentLabel != null) totalSpentLabel.setText("Total Spent: Rs. " + String.format("%.2f", total));
    }

    private void refreshAdmin() {
        // Stats
        adminStatsPanel.removeAll();
        adminStatsPanel.add(statCard("Users", String.valueOf(users.size()), ACCENT_BLUE));
        adminStatsPanel.add(statCard("Drivers", String.valueOf(drivers.size()), ACCENT_GREEN));
        adminStatsPanel.add(statCard("Rides", String.valueOf(bookings.size()), ACCENT_RED));
        double rev = 0; for (RideBooking b : bookings) if ("COMPLETED".equals(b.getStatus())) rev += b.getFare();
        adminStatsPanel.add(statCard("Revenue", "Rs." + String.format("%.0f", rev), ACCENT_GOLD));
        adminStatsPanel.revalidate(); adminStatsPanel.repaint();

        // Tables
        adminDriverModel.setRowCount(0);
        for (Driver d : drivers) {
            adminDriverModel.addRow(new Object[]{d.getDriverId(), d.getName(), d.getPhone(),
                d.isAvailable() ? "Online" : (d.isVerified() ? "Verified" : "Unverified"),
                String.format("%.1f", d.getRating()), d.getTotalTrips()});
        }
        adminUserModel.setRowCount(0);
        for (User u : users) {
            adminUserModel.addRow(new Object[]{u.getUserId(), u.getName(), u.getEmail(), u.getPhone(), u.isVerified() ? "Yes" : "No"});
        }
    }

    // ═══════════════════════════════════════════════
    //  MAIN
    // ═══════════════════════════════════════════════
    public static void main(String[] args) {
        // Set dark look and feel overrides
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("Panel.background", BG_DARK);
            UIManager.put("OptionPane.background", BG_CARD);
            UIManager.put("OptionPane.messageForeground", TEXT_WHITE);
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            GoProGUI gui = new GoProGUI();
            gui.setVisible(true);
        });
    }
}
