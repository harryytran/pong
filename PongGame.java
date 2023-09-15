import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PongGame extends JPanel implements ActionListener {
    private int ballX = 150;
    private int ballY = 150;
    private int ballSpeedX = 3;
    private int ballSpeedY = 2;
    private int paddleY = 250;
    private int paddleSpeed = 5;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private int playerScore = 0;

    public PongGame() {
        Timer timer = new Timer(10, this);
        timer.start();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    upPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    downPressed = true;
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    upPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    downPressed = false;
                }
            }
        });
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        // Update ball position
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Ball collision with top and bottom walls
        if (ballY <= 0 || ballY >= 380) {
            ballSpeedY = -ballSpeedY;
        }

        // Ball collision with paddle
        if (ballX <= 50 && ballX >= 40 && ballY >= paddleY && ballY <= paddleY + 100) {
            ballSpeedX = -ballSpeedX;
            playerScore++;
        }

        // Ball out of bounds
        if (ballX <= 0 || ballX >= 600) {
            // Reset ball position
            ballX = 300;
            ballY = 200;
            ballSpeedX = -ballSpeedX;
            playerScore = 0;
        }

        // Move the paddle
        if (upPressed && paddleY > 0) {
            paddleY -= paddleSpeed;
        }
        if (downPressed && paddleY < 300) {
            paddleY += paddleSpeed;
        }

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 400);

        // Draw ball
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, 20, 20);

        // Draw paddle
        g.fillRect(50, paddleY, 10, 100);

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + playerScore, 10, 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame game = new PongGame();
        frame.add(game);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
