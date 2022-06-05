import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class Main2 {

    private static final String file = "src/main/resources/grades.xml";
    private static final JFrame frame = new JFrame("Átlag és KKI számoló");

    public static void main(String[] args) {
        ArrayList<Grades> grades = new ArrayList<>();

        JMenuBar mb = new JMenuBar();
        JMenu file0 = new JMenu("Fájl");

        JMenuItem exit,save,open,add, modify, list, kki, atlag, delete;
        JMenu out  = new JMenu("Metódusok");

        mb.add(file0);
        mb.add(out);
        mb.add(exit=new JMenuItem("Exit"));

        out.add(list = new JMenuItem("List Subjects and Grades"));
        //out.add(addmodify = new JMenuItem("Add or Modify"));
        JMenu addmodify = new JMenu("Add or Modify");
        out.add(addmodify);

        addmodify.add(add = new JMenuItem("Add new subject"));
        addmodify.add(modify = new JMenuItem("Modify subject"));
        addmodify.add(delete = new JMenuItem("Delete subject"));

        out.add(atlag = new JMenuItem("Average Calculator"));
        out.add(kki = new JMenuItem("KKI Calculator"));

        file0.add(open=new JMenuItem("Open"));
        file0.add(save=new JMenuItem("Save"));

        open.addActionListener(e -> {
            Grades grade1 = grades.set(read(file, frame));
            //showMessageDialog(frame, grades);
        });

        save.addActionListener(e ->{
            saveGradestoXML(grades.get(), file);
        });

        list.addActionListener(e->{
            showMessageDialog(frame, grades);
        });

        add.addActionListener(e->{
            Grades newgrade = new Grades(inputSub(), inputCrd(), inputGrd());
            //grades.
        });

        modify.addActionListener(e->{
            //modifygrades(frame, grades);
        });

        atlag.addActionListener(e->{
            avg(frame);
        });

        kki.addActionListener(e->{

        });
        delete.addActionListener(e->{
            deleteGrade(frame, grades);
        });
        exit.addActionListener(e -> System.exit(0));
        frame.setJMenuBar(mb);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
    }

    private static ArrayList<Grades> read(String file0, JFrame frame) {
        ArrayList<Grades> grade = new ArrayList<>();
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file0);

            Element rootElement = document.getDocumentElement();
            NodeList childNodeList = rootElement.getChildNodes();
            Node node;

            for(int i = 0; i < childNodeList.getLength(); i++)
            {
                node = childNodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    NodeList childNodesOfGradesTag = node.getChildNodes();
                    String sub = "", crd = "", grd ="";
                    for(int j = 0; j < childNodesOfGradesTag.getLength(); j++)
                    {
                        Node childNodeOfGradesTag = childNodesOfGradesTag.item(j);
                        if(childNodeOfGradesTag.getNodeType() == Node.ELEMENT_NODE)
                        {
                            switch (childNodeOfGradesTag.getNodeName()){
                                case "Subject" -> sub = childNodeOfGradesTag.getTextContent();
                                case "Credit" -> crd = childNodeOfGradesTag.getTextContent();
                                case "Grade" -> grd = childNodeOfGradesTag.getTextContent();
                            }
                        }
                    }
                    grade.add(new Grades(sub, Integer.parseInt(crd), Integer.parseInt(grd)));
                    //out.println(grade);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //showMessageDialog(frame, grade);
        showMessageDialog(frame,"Read complete!");
        return grade;
    }

    private static int inputGrd() {

    }

    private static int inputCrd() {

    }

    private static String inputSub() {

    }

    private static void avg(JFrame frame) {

    }

    private static void deleteGrade(JFrame frame, ArrayList<Grades> grades) {

    }
}
