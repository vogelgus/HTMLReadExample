import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class HTMLRead implements ActionListener {

    private ArrayList <String> links = new ArrayList<>();

    public JFrame mainFrame;

    public JPanel mainPanel;
    public JPanel headerPanel;
    public JPanel inputPanel;


    public JTextArea linksResult;
    private JScrollPane scroller;

    public JLabel headerText;

    public JButton searchButton;

    public JTextField searchLink;
    public JTextField searchTerm;

    public Font BTN_FNT = new Font(Font.SERIF,Font.BOLD,45);
    public Font OTHER_FNT = new Font(Font.SERIF,Font.BOLD,20);
    public Color periwinkle = new Color(126, 157, 132, 255);




    public HTMLRead() {

        mainFrame = new JFrame();

        mainFrame.pack();
        mainFrame.setMinimumSize(mainFrame.getPreferredSize());


        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(600, 150));
        headerPanel.setBackground(periwinkle);


        mainPanel = new JPanel();
        mainPanel.setBackground(periwinkle);

        inputPanel = new JPanel();
        inputPanel.setBackground(periwinkle);


        linksResult = new JTextArea("Your results will show here!");
        linksResult.setEditable(false);
        linksResult.setForeground(periwinkle);
        linksResult.setFont(OTHER_FNT);
        scroller = new JScrollPane(linksResult);


        headerText = new JLabel("Welcome to the link puller🌐!");
        headerText.setFont(BTN_FNT);
        headerText.setForeground(Color.white);

        searchButton = new JButton("Go");
        searchButton.addActionListener(this);
        searchButton.setForeground(Color.white);
        searchButton.setFont(OTHER_FNT);
        searchButton.setBackground(periwinkle);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);



        searchLink = new JTextField("Input your link!");
        searchLink.setForeground(periwinkle);
        searchLink.setFont(OTHER_FNT);

        searchTerm = new JTextField("Input your search term!");
        searchTerm.setForeground(periwinkle);
        searchTerm.setFont(OTHER_FNT);



        inputPanel.setLayout(new GridLayout(3, 1));
        inputPanel.add(searchLink);
        inputPanel.add(searchTerm);
        inputPanel.add(searchButton);

        headerPanel.setLayout(new GridLayout(2, 1));
        headerPanel.add(headerText);
        headerPanel.add(inputPanel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scroller, BorderLayout.CENTER);


        mainFrame.add(mainPanel);
        mainFrame.setSize(600, 600);


        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();


        if (buttonClicked == searchButton) {
            links.clear();
            linksResult.setText("");
            HashSet<String> set = getLinks();
            for (String val : set){
                linksResult.append(val+"\n");
            }


        }

    }

    public HashSet<String> getLinks() {
        String linkList = " ";
        try {
            URL url = new URL(searchLink.getText());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<a href=\"")) {
                    int startingIndex = line.indexOf("<a href=\""); //finding where the link starts
                    String temp = line.substring(startingIndex); //creating a substring that starts with the start of the link
                    int start = temp.indexOf("\""); //setting the a href to be start of substring
                    String test = temp.substring(start + 1);
                    int end = test.indexOf("\""); //setting ">" to be the end of the substring
                    String newSearchTerm = searchTerm.getText();
                    if (test.contains(newSearchTerm)) {
                        System.out.println(test.substring(0, end));
                        linkList = linkList + test.substring(0, end) + "\n";
                        links.add(temp.substring(9, end+9));
                    }

                }

            }

            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        HashSet<String> hashSet = new HashSet<>(links);
        return hashSet;
    }

    public static void main(String[] args) {
        HTMLRead html = new HTMLRead();
    }


}


