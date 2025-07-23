package com.mycompany.zonecp;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Tomasz, Iga
 */
public class Apok {

    public static void main(String[] args) {
        Okno ok = new Okno();
        ok.setVisible(true);
    }
}
// 

final class Okno extends JFrame implements ActionListener {

    JPanel p, p1, p2, p3;
    JTextArea in, out;
    JButton go, gen, przepisz, linki, tkrypcja;
    JTextField sygn, strona, linijki;
    JLabel lsygn, lstrona, llinijki, info;

    Okno() {
        setSize(1300, 900);
        setTitle("zonecp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //
        this.setLayout(new FlowLayout(20));

        p = new JPanel(new GridLayout(2, 3));

        p1 = new JPanel(new GridLayout(1, 1));
        p1.setBorder(BorderFactory.createTitledBorder("Wejście"));
        p2 = new JPanel(new GridLayout(7, 2));
        p3 = new JPanel(new GridLayout(1, 1));
        p3.setBorder(BorderFactory.createTitledBorder("Wyjście"));

        in = new JTextArea(40, 35);
//przyciski
        go = new JButton("zony");
        gen = new JButton("linie");
        przepisz = new JButton("orig");
        linki = new JButton("linki zdjęć");
        tkrypcja = new JButton("orig i reg");

        sygn = new JTextField(10);
        strona = new JTextField(10);
        linijki = new JTextField(10);
        lsygn = new JLabel("Sygnatura:");
        lstrona = new JLabel("Karta:");
        llinijki = new JLabel("Ilość linii:");
        info = new JLabel("Wers przed transkrypcją\n musi kończyć się \"/\"");

        go.addActionListener(this);
        gen.addActionListener(this);
        przepisz.addActionListener(this);
        linki.addActionListener(this);
        tkrypcja.addActionListener(this);

        out = new JTextArea("Pusto", 40, 50);

        p1.add(in);
        add(p1);

        p2.add(lsygn);
        p2.add(sygn);
        p2.add(lstrona);
        p2.add(strona);
        p2.add(llinijki);
        p2.add(linijki);
        p2.add(go);
        p2.add(gen);
        p2.add(przepisz);
        p2.add(linki);
        p2.add(tkrypcja);
        //p2.add(info);
        add(p2);

        p3.add(new JScrollPane(out));
        add(p3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String tekst = in.getText();
        tekst = tekst + "\n";
        String linia, liniaTranskrypcji;
        String a = "";
        String ssygn = sygn.getText();
        String sstrona = strona.getText();

        try {
            if (e.getSource() == go) {
                int licznik = 1;

                do {
                    linia = tekst.substring(tekst.indexOf("ulx"), tekst.indexOf(">"));
                    tekst = tekst.replaceFirst("<zone " + linia + ">\n", "");
                    if (licznik < 10) {
                        a += "<zone xml:id=\"" + ssygn + "_line_" + sstrona + "_0" + Integer.toString(licznik) + "\" corresp=\"#" + ssygn + "_lb_" + sstrona + "_0" + Integer.toString(licznik) + "\" rend=\"visible\" rendition=\"Line\" " + linia + ">\n";
                    } else {
                        a += "<zone xml:id=\"" + ssygn + "_line_" + sstrona + "_" + Integer.toString(licznik) + "\" corresp=\"#" + ssygn + "_lb_" + sstrona + "_" + Integer.toString(licznik) + "\" rend=\"visible\" rendition=\"Line\" " + linia + ">\n";
                    }

                    //<zone xml:id="701_line_1r_01" corresp="#701_lb_1r_01" rend="visible" rendition="Line" ulx="1980" uly="792" lrx="4185" lry="1062"/>               
                    licznik++;
                } while (tekst.contains("<zone"));
                out.setText(a);

            } else if (e.getSource() == gen) {
                int ilinijki = Integer.parseInt(linijki.getText());
                for (int i = 1; i <= ilinijki; i++) {
                    if (i < 10) {
                        a += "<l n=\"" + Integer.toString(i) + "\">\n" + "    <lb xml:id=\"" + ssygn + "_lb_" + sstrona + "_0" + Integer.toString(i) + "\" facs=\"#" + ssygn + "_line_" + sstrona + "_0" + Integer.toString(i) + "\" n=\"" + Integer.toString(i) + "\"/>\n        <choice>\n            <orig></orig>\n            <reg></reg>\n        </choice>\n</l>\n";
                    } else {
                        a += "<l n=\"" + Integer.toString(i) + "\">\n" + "    <lb xml:id=\"" + ssygn + "_lb_" + sstrona + "_" + Integer.toString(i) + "\" facs=\"#" + ssygn + "_line_" + sstrona + "_" + Integer.toString(i) + "\" n=\"" + Integer.toString(i) + "\"/>\n        <choice>\n            <orig></orig>\n            <reg></reg>\n        </choice>\n</l>\n";

                    }
                }

                /*
                <l n="1">
                    <lb xml:id="701_lb_19v_1" facs="#701_line_19v_1" n="1"/>
                    <choice>
                        <orig></orig>
                        <reg></reg>
                    </choice>
                </l>
                 */
                out.setText(a);

            } else if (e.getSource() == przepisz) {
                //int ilinijki = Integer.parseInt(linijki.getText());
                int i = 1;
                int dlugosc, dlugosctekstu;
                //for (int i=1; i<=ilinijki;i++)
                do {
                    while (tekst.startsWith("\n")) {
                        tekst = tekst.replaceFirst("\n", "");
                    }
                    linia = tekst.substring(0, tekst.indexOf("\n"));
                    dlugosc = linia.length();
                    dlugosctekstu = tekst.length();
                    //tekst = tekst.replaceFirst(linia + "\n", "");
                    tekst = tekst.substring(dlugosc+1, dlugosctekstu);
                    
                      System.out.println(dlugosc);
                    System.out.println("linia:"+linia+"\n");
                    System.out.println("tekst:"+tekst+"\n\n");
                    
                    if (i < 10) {
                        a += "<l n=\"" + Integer.toString(i) + "\">\n" + "    <lb xml:id=\"" + ssygn + "_lb_" + sstrona + "_0" + Integer.toString(i) + "\" facs=\"#" + ssygn + "_line_" + sstrona + "_0" + Integer.toString(i) + "\" n=\"" + Integer.toString(i) + "\"/>\n        <choice>\n            <orig>" + linia + "</orig>\n            <reg></reg>\n        </choice>\n</l>\n";
                    } else {
                        a += "<l n=\"" + Integer.toString(i) + "\">\n" + "    <lb xml:id=\"" + ssygn + "_lb_" + sstrona + "_" + Integer.toString(i) + "\" facs=\"#" + ssygn + "_line_" + sstrona + "_" + Integer.toString(i) + "\" n=\"" + Integer.toString(i) + "\"/>\n        <choice>\n            <orig>" + linia + "</orig>\n            <reg></reg>\n        </choice>\n</l>\n";

                    }
                    i++;
                } while (tekst.contains("\n"));
                out.setText(a);

            } else if (e.getSource() == linki) {
                int ilinijki = Integer.parseInt(linijki.getText());
                for (int i = 1; i <= ilinijki; i++) {
                    if (i < 10) {
                        a += "https://arfi.kul.pl/Scaler/iiif/BUKUL%21Rkp" + ssygn + "%21Image0000" + Integer.toString(i) + "/full/full/0/default.jpg" + "\n";
                    } else if (i >= 10 && i < 100) {
                        a += "https://arfi.kul.pl/Scaler/iiif/BUKUL%21Rkp" + ssygn + "%21Image000" + Integer.toString(i) + "/full/full/0/default.jpg" + "\n";
                    } else {
                        a += "https://arfi.kul.pl/Scaler/iiif/BUKUL%21Rkp" + ssygn + "%21Image00" + Integer.toString(i) + "/full/full/0/default.jpg" + "\n";
                    }
                }
                out.setText(a);
            } else if (e.getSource() == tkrypcja) {
                //int ilinijki = Integer.parseInt(linijki.getText());
                int i = 1;
                //for (int i=1; i<=ilinijki;i++)
                do {
                    while (tekst.startsWith("\n")) {
                        tekst = tekst.replaceFirst("\n", "");
                    }
                    linia = tekst.substring(0, tekst.indexOf("\n"));
                    tekst = tekst.replace(linia + "\n", "");
                    linia = linia.trim();
                    //System.out.println("linia:" + linia + "\n");
                    //System.out.println("tekst:" + tekst + "\n\n");
                    if (linia.endsWith("/")) {
                        //System.out.println("chu");
                        int koniecLinii = linia.length();
                        linia = linia.substring(0, koniecLinii - 1);
                        while (tekst.startsWith("\n")) {
                            tekst = tekst.replaceFirst("\n", "");
                        }
                        liniaTranskrypcji = tekst.substring(0, tekst.indexOf("\n"));
                        tekst = tekst.replace(liniaTranskrypcji + "\n", "");
                        if (i < 10) {
                            a += "<l n=\"" + Integer.toString(i) + "\">\n" + "    <lb xml:id=\"" + ssygn + "_lb_" + sstrona + "_0" + Integer.toString(i) + "\" facs=\"#" + ssygn + "_line_" + sstrona + "_0" + Integer.toString(i) + "\" n=\"" + Integer.toString(i) + "\"/>\n        <choice>\n            <orig>" + linia + "</orig>\n            <reg>" + liniaTranskrypcji + "</reg>\n        </choice>\n</l>\n";
                        } else {
                            a += "<l n=\"" + Integer.toString(i) + "\">\n" + "    <lb xml:id=\"" + ssygn + "_lb_" + sstrona + "_" + Integer.toString(i) + "\" facs=\"#" + ssygn + "_line_" + sstrona + "_" + Integer.toString(i) + "\" n=\"" + Integer.toString(i) + "\"/>\n        <choice>\n            <orig>" + linia + "</orig>\n            <reg>" + liniaTranskrypcji + "</reg>\n        </choice>\n</l>\n";
                        }
                    } else {
                        //System.out.println("jakbfvajkbvjkb");
                        if (i < 10) {
                            a += "<l n=\"" + Integer.toString(i) + "\">\n" + "    <lb xml:id=\"" + ssygn + "_lb_" + sstrona + "_0" + Integer.toString(i) + "\" facs=\"#" + ssygn + "_line_" + sstrona + "_0" + Integer.toString(i) + "\" n=\"" + Integer.toString(i) + "\"/>\n        <choice>\n            <orig>" + linia + "</orig>\n            <reg></reg>\n        </choice>\n</l>\n";
                        } else {
                            a += "<l n=\"" + Integer.toString(i) + "\">\n" + "    <lb xml:id=\"" + ssygn + "_lb_" + sstrona + "_" + Integer.toString(i) + "\" facs=\"#" + ssygn + "_line_" + sstrona + "_" + Integer.toString(i) + "\" n=\"" + Integer.toString(i) + "\"/>\n        <choice>\n            <orig>" + linia + "</orig>\n            <reg></reg>\n        </choice>\n</l>\n";
                        }
                    }
                    i++;
                } while (tekst.contains("\n"));
                out.setText(a);

            }
            //zapis << "<zone xml:id=\"" << sygnatura << "_line_" << strona << "_0" << to_string(licznik) << "\" corresp=\"#" << sygnatura << "_lb_" << strona << "_0" << to_string(licznik) << "\" rend=\"visible\" rendition=\"Line\" ";
        } catch (java.lang.StringIndexOutOfBoundsException w) {
            out.setText("Błędy we wprowadzanych danych");
        } catch (java.lang.NumberFormatException m) {
            out.setText("Ilość linii musi być liczbą całkowitą");
        }
    }

}
