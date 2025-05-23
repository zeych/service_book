package buttom_service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ButtonApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private List<MovingImage> movingImages = new ArrayList<>();

    public ButtonApp() {
        setTitle("Сервисная компания: Управление заявками");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель управления
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(240, 240, 240));
        controlPanel.setLayout(new FlowLayout());

        JButton btnClients = new JButton("Клиенты");
        JButton btnEmployees = new JButton("Сотрудники");
        JButton btnSchedule = new JButton("Расписание");
        JButton btnRequests = new JButton("Заявки");

        controlPanel.add(btnClients);
        controlPanel.add(btnEmployees);
        controlPanel.add(btnSchedule);
        controlPanel.add(btnRequests);

        // Панель анимации
        JPanel animationPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (MovingImage img : movingImages) {
                    if (img.image != null) {
                        g.drawImage(img.image, img.x, img.y, this);
                    }
                }
            }
        };
        animationPanel.setBackground(Color.WHITE);

        add(controlPanel, BorderLayout.NORTH);
        add(animationPanel, BorderLayout.CENTER);

        try {
            // Попробуем загрузить изображения
            Image computerImage = new ImageIcon(getClass().getResource("/computer.png")).getImage();
            //Image toolImage = new ImageIcon(getClass().getResource("/end.png")).getImage();
            
            movingImages.add(new MovingImage(computerImage, -1, -1, -2, 0));
           // movingImages.add(new MovingImage(toolImage, 200, 100, -1, 2));
        } catch (Exception e) {
            System.err.println("Ошибка загрузки изображений: " + e.getMessage());
            
            movingImages.add(new MovingImage(null, 0, 0, 0, 0));
            movingImages.add(new MovingImage(null, 200, 100, -1, 2));
        }

        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (MovingImage img : movingImages) {
                    img.move(animationPanel.getWidth(), animationPanel.getHeight());
                }
                animationPanel.repaint();
            }
        });
        timer.start();
    }

    class MovingImage {
        Image image;
        int x, y;
        int dx, dy;

        public MovingImage(Image image, int x, int y, int dx, int dy) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }

        public void move(int panelWidth, int panelHeight) {
            x += dx;
            y += dy;

            if (x < 0 || (image != null && x + image.getWidth(null) > panelWidth)) dx = -dx;
            if (y < 0 || (image != null && y + image.getHeight(null) > panelHeight)) dy = -dy;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ButtonApp app = new ButtonApp();
            app.setVisible(true);
        });
    }
}