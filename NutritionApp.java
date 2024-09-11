package nutrition;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NutritionApp {
    private static JFrame frame;
    private static JTextField emailField;
    private static JPasswordField passwordField;
    private static JTextField usernameField;
    private static User currentUser;

    public static void main(String[] args) {
        // Initialize main frame
        frame = new JFrame("Nutritionist App");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Show login/register panel
        JPanel panel = new GradientPanel();
        frame.getContentPane().add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(new BorderLayout());

        // Content panel for login/register
        JPanel contentPanel = new GradientPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(contentPanel, BorderLayout.CENTER);

        // GridBagLayout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        contentPanel.add(emailLabel, gbc);

        gbc.gridy++;
        emailField = new JTextField(20);
        emailField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        contentPanel.add(emailField, gbc);

        // Password field
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        contentPanel.add(passwordLabel, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        contentPanel.add(passwordField, gbc);

        // Username field (for registration)
        gbc.gridy++;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        contentPanel.add(usernameLabel, gbc);

        gbc.gridy++;
        usernameField = new JTextField(20);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        contentPanel.add(usernameField, gbc);

        // Login button
        gbc.gridy++;
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        contentPanel.add(loginButton, gbc);

        // Register button
        gbc.gridy++;
        JButton registerButton = new JButton("Register");
        styleButton(registerButton);
        contentPanel.add(registerButton, gbc);

        // Action listeners for login and register buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                UserDAO userDAO = new UserDAO();
                currentUser = userDAO.loginUser(email, password);
                if (currentUser != null) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid email or password.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                UserDAO userDAO = new UserDAO();
                if (userDAO.registerUser(user)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed.");
                }
            }
        });
    }

    private static void showMainMenu() {
        // Clear and repaint frame for main menu
        frame.getContentPane().removeAll();
        frame.repaint();

        // Main panel with gradient background
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());

        // Navigation bar
        JPanel navbar = new JPanel();
        navbar.setBackground(new Color(34, 49, 63));
        navbar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        navbar.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel appName = new JLabel("Nutritionist App");
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Arial", Font.BOLD, 20));
        navbar.add(appName);

        frame.add(navbar, BorderLayout.NORTH);

        // Center panel for main menu buttons
        JPanel centerPanel = new GradientPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons for main menu options
        JButton btnShowHealthRecords = new JButton("Show Health Records");
        JButton btnEditHealthRecords = new JButton("Edit Health Records");
        JButton btnAddRecipe = new JButton("Add Recipe");
        JButton btnShowRecipes = new JButton("Show Recipes");
        JButton btnLogout = new JButton("Logout");

        styleBigButton(btnShowHealthRecords);
        styleBigButton(btnEditHealthRecords);
        styleBigButton(btnAddRecipe);
        styleBigButton(btnShowRecipes);
        styleBigButton(btnLogout);

        // Add buttons to center panel
        centerPanel.add(btnShowHealthRecords, gbc);
        gbc.gridy++;
        centerPanel.add(btnEditHealthRecords, gbc);
        gbc.gridy++;
        centerPanel.add(btnAddRecipe, gbc);
        gbc.gridy++;
        centerPanel.add(btnShowRecipes, gbc);
        gbc.gridy++;
        centerPanel.add(btnLogout, gbc);

        // Add center panel to main panel
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Action listeners for main menu buttons
        btnShowHealthRecords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HealthRecordDAO healthRecordDAO = new HealthRecordDAO();
                HealthRecord record = healthRecordDAO.getHealthRecord(currentUser.getId());
                if (record != null) {
                    JOptionPane.showMessageDialog(frame, "Height: " + record.getHeight() + "\nWeight: " + record.getWeight() + "\nAge: " + record.getAge());
                } else {
                    JOptionPane.showMessageDialog(frame, "No health records found.");
                }
            }
        });

        btnEditHealthRecords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField heightField = new JTextField(5);
                JTextField weightField = new JTextField(5);
                JTextField ageField = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.gridx = 0;
                gbc.gridy = 0;

                myPanel.add(new JLabel("Height:"), gbc);
                gbc.gridx++;
                myPanel.add(heightField, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                myPanel.add(new JLabel("Weight:"), gbc);
                gbc.gridx++;
                myPanel.add(weightField, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                myPanel.add(new JLabel("Age:"), gbc);
                gbc.gridx++;
                myPanel.add(ageField, gbc);

                int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter Height, Weight, and Age", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    float height = Float.parseFloat(heightField.getText());
                    float weight = Float.parseFloat(weightField.getText());
                    int age = Integer.parseInt(ageField.getText());

                    HealthRecord record = new HealthRecord();
                    record.setUserId(currentUser.getId());
                    record.setHeight(height);
                    record.setWeight(weight);
                    record.setAge(age);

                    HealthRecordDAO healthRecordDAO = new HealthRecordDAO();
                    if (healthRecordDAO.addOrUpdateHealthRecord(record)) {
                        JOptionPane.showMessageDialog(frame, "Health record updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to update health record.");
                    }
                }
            }
        });

        btnAddRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField titleField = new JTextField(5);
                JTextField ingredientsField = new JTextField(5);
                JTextField instructionsField = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.gridx = 0;
                gbc.gridy = 0;

                myPanel.add(new JLabel("Title:"), gbc);
                gbc.gridx++;
                myPanel.add(titleField, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                myPanel.add(new JLabel("Ingredients:"), gbc);
                gbc.gridx++;
                myPanel.add(ingredientsField, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                myPanel.add(new JLabel("Instructions:"), gbc);
                gbc.gridx++;
                myPanel.add(instructionsField, gbc);

                int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter Recipe Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String title = titleField.getText();
                    String ingredients = ingredientsField.getText();
                    String instructions = instructionsField.getText();

                    Recipe recipe = new Recipe();
                    recipe.setUserId(currentUser.getId());
                    recipe.setTitle(title);
                    recipe.setIngredients(ingredients);
                    recipe.setInstructions(instructions);

                    RecipeDAO recipeDAO = new RecipeDAO();
                    if (recipeDAO.addRecipe(recipe)) {
                        JOptionPane.showMessageDialog(frame, "Recipe added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to add recipe.");
                    }
                }
            }
        });

        btnShowRecipes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecipeDAO recipeDAO = new RecipeDAO();
                List<Recipe> recipes = recipeDAO.getRecipes(currentUser.getId());
                if (recipes.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No recipes found.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Recipe recipe : recipes) {
                        sb.append("Title: ").append(recipe.getTitle()).append("\n")
                                .append("Ingredients: ").append(recipe.getIngredients()).append("\n")
                                .append("Instructions: ").append(recipe.getInstructions()).append("\n\n");
                    }
                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setEditable(false);
                    JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Recipes", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = null;
                frame.getContentPane().removeAll();
                frame.repaint();
                placeComponents((JPanel) frame.getContentPane());
                frame.setSize(frame.getWidth(), frame.getHeight());
                frame.setVisible(true);
            }
        });
    }

    private static void styleButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219)); // Button color
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(new Color(34, 45, 65)));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(34, 45, 65)); // Hover color
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219)); // Button color
            }
        });
    }

    private static void styleBigButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219)); // Button color
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBorder(BorderFactory.createLineBorder(new Color(34, 45, 65)));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(34, 45, 65)); // Hover color
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219)); // Button color
            }
        });
    }

    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            Color color1 = new Color(70, 130, 180);
            Color color2 = new Color(34, 49, 63);
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }
}
