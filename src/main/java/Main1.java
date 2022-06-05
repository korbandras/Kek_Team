import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

import static java.lang.System.out;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class Main1 {

    private static final Scanner be = new Scanner(System.in);
    private static final String file = "src/main/resources/grades.xml";

    private static final JFrame frame = new JFrame("Átlag és KKI számoló");

    public static void main(String[] args) {
        AtomicReference<ArrayList<Grades>> grades = new AtomicReference<>(new ArrayList<>());

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
            grades.set(read(file, frame));
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
    private static void deleteGrade(JFrame frame, ArrayList<Grades> grades)
    {
        String name = JOptionPane.showInputDialog("Subject you want to delete: ");
        try
        {
            grades.remove(findGrade(grades, name));
            showMessageDialog(frame, "Subject deleted.");
        }
        catch (IllegalArgumentException e)
        {
            showMessageDialog(frame, e.getMessage());
        }
    }
    private static Grades findGrade(ArrayList<Grades> grade, String sub) throws IllegalArgumentException
    {
        for(Grades grades : grade)
        {
            if(grades.getSubject().equals(sub))
            {
                return grades;
            }
        }
        throw new IllegalArgumentException("No subject with given name: " + sub);
    }
    private static void avg(JFrame frame) {
        double avg = gradesSum()/gradesNo();
        showMessageDialog(frame,"Average of the grades: " + avg);
    }
    private static double gradesSum() {
        double sum = 0;
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            Element rootElement = document.getDocumentElement();
            NodeList childNodeList = rootElement.getChildNodes();
            Node node;

            for(int i = 0; i < childNodeList.getLength(); i++)
            {
                node = childNodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    NodeList childNodesOfGradesTag = node.getChildNodes();
                    String grd ="";
                    for(int j = 0; j < childNodesOfGradesTag.getLength(); j++)
                    {
                        Node childNodeOfGradesTag = childNodesOfGradesTag.item(j);
                        if(childNodeOfGradesTag.getNodeType() == Node.ELEMENT_NODE)
                        {
                            if (childNodeOfGradesTag.getNodeName().equals("Grade"))
                            {
                                grd = childNodeOfGradesTag.getTextContent();
                            }
                        }
                    }
                    sum = sum + Integer.parseInt(grd);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sum;
    }
    private static double gradesNo() {
        double No = 0;
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            Element rootElement = document.getDocumentElement();
            NodeList childNodeList = rootElement.getChildNodes();
            Node node;

            for(int i = 0; i < childNodeList.getLength(); i++)
            {
                node = childNodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    NodeList childNodesOfGradesTag = node.getChildNodes();
                    for(int j = 0; j < childNodesOfGradesTag.getLength(); j++)
                    {
                        Node childNodeOfGradesTag = childNodesOfGradesTag.item(j);
                        if(childNodeOfGradesTag.getNodeType() == Node.ELEMENT_NODE)
                        {
                            if (childNodeOfGradesTag.getNodeName().equals("Grade"))
                            {
                                No++;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return No;
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
    private static void saveGradestoXML(ArrayList<Grades> grade, String filepath1) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("Semester");
            document.appendChild(root);
            for(Grades grades : grade) {
                Element gradeElement = document.createElement("Sub");
                root.appendChild(gradeElement);
                childElement(document, gradeElement, "Subject", grades.getSubject());
                childElement(document, gradeElement, "Credit", String.valueOf(grades.getCredit()));
                childElement(document, gradeElement, "Grade", String.valueOf(grades.getGrade()));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(filepath1));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void childElement(Document document, Element par, String tag, String text) {
        Element element = document.createElement(tag);
        element.setTextContent(text);
        par.appendChild(element);
    }
    private static int inputGrd() {
        int grd = 0;
        while(grd < 1 || grd > 5) {
            grd = Integer.parseInt(JOptionPane.showInputDialog("Enter the grade of the new subject: "));
            try {
                if (grd > 5 || grd < 1) {
                    showMessageDialog(frame, "Grade must be between 1 and 5");
                }
            } catch (InputMismatchException e) {
                showMessageDialog(frame, "Grade must be a number");
            }
        }
        return grd;
    }
    private static int inputCrd() {
        int crd = Integer.parseInt(JOptionPane.showInputDialog("Enter credit value of new subject: "));
        while(crd < 0 || crd > 9) {
            crd = Integer.parseInt(JOptionPane.showInputDialog("Credit value invalid. Please reenter the value"));
        }
        return crd;
    }
    private static String inputSub() {
        String sub = JOptionPane.showInputDialog("Name of new subject: ");
        return sub;
    }
}
