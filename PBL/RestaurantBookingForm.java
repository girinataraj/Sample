import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class RestaurantBookingForm {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Restaurant Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.add(new BackgroundPanel(), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    static class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel() {
            backgroundImage = new ImageIcon("C:\\Users\\Monish S\\Pictures\\Screenshots\\images.jpeg").getImage();
            setLayout(new BorderLayout());
            add(createMainPanel(), BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createHistoryPanel(), BorderLayout.SOUTH);
        return mainPanel;
    }

    private static JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Restaurant Booking");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        JTextField emailField = new JTextField(20);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setForeground(Color.WHITE);
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);

        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setForeground(Color.WHITE);
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);

        JLabel durationLabel = new JLabel("Duration:");
        durationLabel.setForeground(Color.WHITE);
        JTextField durationField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone no:");
        phoneLabel.setForeground(Color.WHITE);
        JTextField phoneField = new JTextField(20);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.WHITE);
        JTextField addressField = new JTextField(20);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(dateSpinner.getValue());
                String time = new SimpleDateFormat("HH:mm:ss").format(timeSpinner.getValue());
                String duration = durationField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();

                try (Connection conn = getConnection()) {
                    String sql = "INSERT INTO Bookings (name, email, date, time, duration, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.setString(3, date);
                    stmt.setString(4, time);
                    stmt.setString(5, duration);
                    stmt.setString(6, phone);
                    stmt.setString(7, address);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Booking saved successfully!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving booking: " + ex.getMessage());
                }

                nameField.setText("");
                emailField.setText("");
                durationField.setText("");
                phoneField.setText("");
                addressField.setText("");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(dateSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(timeSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(durationLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(durationField, gbc);
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        formPanel.add(submitButton, gbc);

        return formPanel;
    }

    private static JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel();
        historyPanel.setOpaque(false);
        historyPanel.setPreferredSize(new Dimension(0, 50));
        JButton viewHistoryButton = new JButton("View Booking History");
        JButton clearHistoryButton = new JButton("Clear Booking History");

        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection conn = getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM Bookings")) {

                    StringBuilder history = new StringBuilder();
                    while (rs.next()) {
                        history.append("ID: ").append(rs.getInt("id")).append("\n");
                        history.append("Name: ").append(rs.getString("name")).append("\n");
                        history.append("Email: ").append(rs.getString("email")).append("\n");
                        history.append("Date: ").append(rs.getDate("date")).append("\n");
                        history.append("Time: ").append(rs.getTime("time")).append("\n");
                        history.append("Duration: ").append(rs.getString("duration")).append("\n");
                        history.append("Phone: ").append(rs.getString("phone")).append("\n");
                        history.append("Address: ").append(rs.getString("address")).append("\n\n");
                    }

                    if (history.length() == 0) {
                        JOptionPane.showMessageDialog(null, "No bookings found!");
                    } else {
                        JOptionPane.showMessageDialog(null, history.toString());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error retrieving bookings: " + ex.getMessage());
                }
            }
        });

        clearHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection conn = getConnection();
                     Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("DELETE FROM Bookings");
                    JOptionPane.showMessageDialog(null, "Booking history cleared!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error clearing history: " + ex.getMessage());
                }
            }
        });

        historyPanel.add(viewHistoryButton);
        historyPanel.add(clearHistoryButton);

        return historyPanel;
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/RestaurantDB";
        String user = "kishore";
        String password = "kishore007";
        return DriverManager.getConnection(url, user, password);
    }
}
