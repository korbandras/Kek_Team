import javax.swing.*;
import java.awt.*;

public class MainGIU extends JFrame {

    public final String url = "https://img.rawpixel.com/s3fs-private/rawpixel_images/website_content/rm373batch15-217-01.jpg?w=800&dpr=1&fit=default&crop=default&q=65&vib=3&con=3&usm=15&bg=F4F4F3&ixlib=js-2.2.1&s=d8bbc66e02e81095950de55fcc9347f5";
    public final Image image = BackgroundImage.requestImage(url);
    public MainGIU(){
        JMenuBar mb;
        JMenu file = new JMenu("File");
        JMenuItem exit;

        JFrame frame = new JFrame();
        frame.setTitle("Subjects-Grades-Credits");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(mb = new JMenuBar());
        file.add(exit = new JMenuItem("Exit"));
        mb.add(file);

        JPanel panel = new JPanel(new GridBagLayout()){
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        GridBagConstraints constr = new GridBagConstraints();
        constr.insets = new Insets(5,5,5,5);
        constr.anchor = GridBagConstraints.WEST;

        constr.gridx = 0;
        constr.gridy = 0;

        JLabel list = new JLabel("List Subjects and Grades");
        JLabel NameLabel = new JLabel("Add New Subject");
        JLabel pwdLabel = new JLabel("Modify Subject");

        JButton button = new JButton("Modify");
        JButton button0 = new JButton("Add new");
        JButton button1 = new JButton("List all");

        panel.add(NameLabel, constr);
        constr.gridx = 1;
        panel.add(button, constr);
        constr.gridx = 0;
        constr.gridy = 1;
    }
}
