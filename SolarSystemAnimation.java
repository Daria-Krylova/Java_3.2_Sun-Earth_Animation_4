import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.image.BufferedImage;


public class SolarSystemAnimation extends JPanel implements ActionListener {

    private static final int WIDTH = 800;   // Ширина окна
    private static final int HEIGHT = 600;  // Высота окна

    private ImageIcon sunAnimation;
    private BufferedImage earthImage;
    private int sunX, sunY;
    private int earthX, earthY;
    private int earthRadius;
    private double angle;
    private Timer timer;

    public SolarSystemAnimation() {
        // Загрузка анимированного GIF солнца и изображения Земли (замените на свои пути)
        sunAnimation = new ImageIcon("Sun_resize.gif");
        earthImage = loadImage("Earth.jpeg");

        // Уменьшение размера Земли
        int imageSize = 100;  // Новый размер для Земли
        earthImage = resizeImage(earthImage, imageSize, imageSize);

        // Начальная позиция солнца (в центре окна)
        sunX = (WIDTH - sunAnimation.getIconWidth()) / 2;
        sunY = (HEIGHT - sunAnimation.getIconHeight()) / 2;

        // Начальная позиция Земли и радиус орбиты
        earthRadius = 200;
        angle = 0;
        updateEarthPosition();

        // Создание таймера для анимации
        timer = new Timer(10, this);
        timer.start();
    }

    private BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private void updateEarthPosition() {
        earthX = sunX + (int) (earthRadius * Math.cos(angle));
        earthY = sunY + (int) (earthRadius * Math.sin(angle));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Заливка фона черным цветом
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Отображение анимированного GIF солнца
        sunAnimation.paintIcon(this, g, sunX, sunY);

        // Отображение Земли на орбите
        g.drawImage(earthImage, earthX, earthY, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Обработка движения Земли по орбите

        // Увеличение угла для движения по окружности
        angle += 0.01;

        // Пересчет позиции Земли
        updateEarthPosition();

        // Перерисовка панели
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Солнечная система");
        SolarSystemAnimation solarSystem = new SolarSystemAnimation();
        frame.add(solarSystem);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
