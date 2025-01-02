import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RestaurantHomePage {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Restaurant Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set fullscreen
        frame.setLayout(new BorderLayout());

        // Create a panel for the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("back.jpg").getImage(); // Update with your image path
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Scale image to panel size
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center content

        // Add content to the background panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add a welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Our Restaurant!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE); // Set text color
        backgroundPanel.add(welcomeLabel, gbc);

        // Add some vertical spacing
        gbc.gridy++;

        // Add a button to navigate to the order table page
        JButton orderTableButton = new JButton("Order Table");
        orderTableButton.setFont(new Font("Arial", Font.BOLD, 22));

        // Action listener for the button
        orderTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Restaurant Booking Form
                SwingUtilities.invokeLater(() -> RestaurantBookingForm.main(new String[]{}));
                frame.dispose(); // Close the home page
            }
        });
        backgroundPanel.add(orderTableButton, gbc);

        // Add the background panel to the frame
        frame.add(backgroundPanel);

        // Set frame visibility
        frame.setVisible(true);
    }
}