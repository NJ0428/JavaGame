package AwtSwing;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class DynamicBeat extends JFrame {

    private Image screenImage;
    private Graphics screenGraphic;
    
    private final ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
    private final ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
    private final ImageIcon startButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/startButtonEntered.png"));
    private final ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
    private final ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/quitButtonEntered.png"));
    private final ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/quitButtonBasic.png"));
    private final ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png"));
    private final ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
    private final ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButtonEntered.png"));
    private final ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));
    private final ImageIcon easyButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/easyButtonEntered.png"));
    private final ImageIcon easyButtonBasicImage = new ImageIcon(Main.class.getResource("../images/easyButtonBasic.png"));
    private final ImageIcon hardButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/hardButtonEntered.png"));
    private final ImageIcon hardButtonBasicImage = new ImageIcon(Main.class.getResource("../images/hardButtonBasic.png"));
    private final ImageIcon backButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/backButtonEntered.png"));
    private final ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));
    
    private Image background = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg")).getImage();
    private final JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));

    private final JButton exitButton = new JButton(exitButtonBasicImage);
    private final JButton startButton = new JButton(startButtonBasicImage);
    private final JButton quitButton = new JButton(quitButtonBasicImage);
    private final JButton leftButton = new JButton(leftButtonBasicImage);
    private final JButton rightButton = new JButton(rightButtonBasicImage);
    private final JButton easyButton = new JButton(easyButtonBasicImage);
    private final JButton hardButton = new JButton(hardButtonBasicImage);
    private final JButton backButton = new JButton(backButtonBasicImage);
    
    private int mouseX, mouseY;
    private boolean isMainScreen = false;
    private boolean isGameScreen = false;
    private boolean isPaused = false;

    private final ArrayList<Track> trackList = new ArrayList<>();
    private Image titleImage;
    private Image selectedImage;
    private Music selectedMusic;
    private final Music introMusic = new Music("introMusic.mp3", true);
    private int nowSelected = 0;

    private static Game game;
    private JSlider volumeSlider;

    public DynamicBeat() {
        initialize();
    }

    private void initialize() {
        setupFrame();
        setupMenuBar();
        setupButtons();
        setupVolumeControl();
        introMusic.start();
        populateTrackList();
    }

    private void setupFrame() {
        setUndecorated(true);
        setTitle("Dynamic Beat");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        addKeyListener(new KeyListener());
    }

    private void setupMenuBar() {
        menuBar.setBounds(0, 0, 1280, 30);
        menuBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        menuBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });
        add(menuBar);
    }

    private void setupButtons() {
        configureButton(exitButton, exitButtonBasicImage, exitButtonEnteredImage, 1245, 0, 30, 30, e -> System.exit(0));
        configureButton(startButton, startButtonBasicImage, startButtonEnteredImage, 40, 200, 400, 100, e -> enterMain());
        configureButton(quitButton, quitButtonBasicImage, quitButtonEnteredImage, 40, 330, 400, 100, e -> System.exit(0));
        configureButton(leftButton, leftButtonBasicImage, leftButtonEnteredImage, 140, 310, 60, 60, e -> selectLeft());
        configureButton(rightButton, rightButtonBasicImage, rightButtonEnteredImage, 1080, 310, 60, 60, e -> selectRight());
        configureButton(easyButton, easyButtonBasicImage, easyButtonEnteredImage, 375, 580, 250, 67, e -> gameStart(nowSelected, "Easy"));
        configureButton(hardButton, hardButtonBasicImage, hardButtonEnteredImage, 655, 580, 250, 67, e -> gameStart(nowSelected, "Hard"));
        configureButton(backButton, backButtonBasicImage, backButtonEnteredImage, 20, 50, 60, 60, e -> backMain());
        
        leftButton.setVisible(false);
        rightButton.setVisible(false);
        easyButton.setVisible(false);
        hardButton.setVisible(false);
        backButton.setVisible(false);
    }

    private void setupVolumeControl() {
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setBounds(20, 100, 200, 50);
        volumeSlider.setVisible(false);
        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            Music.setVolume(volume);
        });
        add(volumeSlider);
    }

    private void configureButton(JButton button, ImageIcon basicImage, ImageIcon enteredImage, int x, int y, int width, int height, MouseListener mouseListener) {
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(enteredImage);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                new Music("buttonEnteredMusic.mp3", false).start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(basicImage);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                mouseListener.mouseClicked(e);
            }
        });
        add(button);
    }

    private void populateTrackList() {
        trackList.add(new Track("Mighty Love Title Image.png", "Mighty Love Start Image.png",
                "Mighty Love Game Image.jpg", "Mighty Love Selected.mp3", "Joakim Karud - Mighty Love.mp3", "Joakim Karud - Mighty Love"));
        trackList.add(new Track("Wild Flower Title Image.png", "Wild Flower Start Image.png",
                "Wild Flower Game Image.jpg", "Wild Flower Selected.mp3", "Joakim Karud - Wild Flower.mp3", "Joakim Karud - Wild Flower"));
        trackList.add(new Track("Energy Title Image.png", "Energy Start Image.png",
                "Energy Game Image.png", "Energy Selected.mp3", "Bensound - Energy.mp3", "Bensound - Energy"));
    }

    public void paint(Graphics g) {
        screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = screenImage.getGraphics();
        screenDraw((Graphics2D) screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    }

    public void screenDraw(Graphics2D g) {
        g.drawImage(background, 0, 0, null);
        if (isMainScreen) {
            g.drawImage(selectedImage, 340, 100, null);
            g.drawImage(titleImage, 340, 70, null);
        }
        if (isGameScreen) {
            game.screenDraw(g);
        }
        paintComponents(g);
        this.repaint();
    }

    public void selectTrack(int nowSelected) {
        if (selectedMusic != null) {
            selectedMusic.close();
        }
        titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage())).getImage();
        selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
        selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
        selectedMusic.start();
    }

    public void selectLeft() {
        if (nowSelected == 0) {
            nowSelected = trackList.size() - 1;
        } else {
            nowSelected--;
        }
        selectTrack(nowSelected);
    }

    public void selectRight() {
        if (nowSelected == trackList.size() - 1) {
            nowSelected = 0;
        } else {
            nowSelected++;
        }
        selectTrack(nowSelected);
    }

    public void gameStart(int nowSelected, String difficulty) {
        if (selectedMusic != null) {
            selectedMusic.close();
        }
        isMainScreen = false;
        leftButton.setVisible(false);
        rightButton.setVisible(false);
        easyButton.setVisible(false);
        hardButton.setVisible(false);
        backButton.setVisible(true);
        background = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage())).getImage();
        isGameScreen = true;
        game = new Game(trackList.get(nowSelected).getTitleName(), difficulty, trackList.get(nowSelected).getGameMusic());
        game.start();
        setFocusable(true);
    }

    public void backMain() {
        isMainScreen = true;
        leftButton.setVisible(true);
        rightButton.setVisible(true);
        easyButton.setVisible(true);
        hardButton.setVisible(true);
        backButton.setVisible(false);
        background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
        selectTrack(nowSelected);
        isGameScreen = false;
        game.close();
    }

    public void enterMain() {
        startButton.setVisible(false);
        quitButton.setVisible(false);
        background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
        isMainScreen = true;
        leftButton.setVisible(true);
        rightButton.setVisible(true);
        easyButton.setVisible(true);
        hardButton.setVisible(true);
        backButton.setVisible(false);
        selectTrack(0);
    }

    public void togglePause() {
        if (isGameScreen) {
            isPaused = !isPaused;
            game.setPaused(isPaused);
        }
    }

    public static void main(String[] args) {
        new DynamicBeat();
    }

    class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                togglePause();
            } else if (isGameScreen && !isPaused) {
                game.pressKey(e.getKeyCode());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (isGameScreen && !isPaused) {
                game.releaseKey(e.getKeyCode());
            }
        }
    }
}
