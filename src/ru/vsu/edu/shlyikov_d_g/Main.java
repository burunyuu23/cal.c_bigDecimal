package ru.vsu.edu.shlyikov_d_g;

import java.util.Locale;
import java.util.Scanner;
//import java.math.BigDecimal;


public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        Scanner scan = new Scanner(System.in);

//        BigDecimal bd = new BigDecimal(scan.nextLine());
//
//        System.out.println(bd.num + " " + bd.exp);

        String[] tests = {"123.123", "234.123", "234,123",
                "234123e-4", "234123E-4", "-.12345",
                "-0.12345", "-,12345", "+000.000001",
                "+1e-6", "-+123.123", "312000000", "123123123123123213123123123,1"};

//        tests(tests);
        // "234.123" or "234,123" -> [234123][3]
        // "234123e-4" or "234123E-4" -> [234123][4]
        // "-.12345" or "-0.12345" or "-,12345" -> [-12345][5]
        // "+000.000001" or "+1e-6" -> [1][6]

        BigDecimal bg1 = new BigDecimal("54.11"); // 54110
        BigDecimal bg2 = new BigDecimal("3.2222");// 32222
        System.out.println(bg1 + " + " + bg2);
        BigDecimal bg3 = bg1.add("+", bg2);
        System.out.println(" = ");
        System.out.println(bg3);
        System.out.println();

        System.out.println(bg1 + " + " + bg2 + "\n = ");
        bg3 = bg1.plus(bg2);
        System.out.println(bg3);
        System.out.println();

        System.out.println(bg1 + " - " + bg2 + "\n = ");
        bg3 = bg1.minus(bg2);
        System.out.println(bg3);
        System.out.println();

        System.out.println(bg1 + " * " + bg2 + "\n = ");
        bg3 = bg1.multiply(bg2);
        System.out.println(bg3);
        System.out.println();

        System.out.println(bg1 + " / " + bg2 + "\n = ");
        bg3 = bg1.div(bg2);
        System.out.println(bg3);
        System.out.println();

        System.out.println(bg1 + " % " + bg2 + "\n = ");
        bg3 = bg1.mod(bg2);
        System.out.println(bg3);
        System.out.println();

//        bg3 = bg1.add("+", new BigDecimal(tests[11]), new BigDecimal(tests[12]));
//        System.out.println(bg3);

        //SwingUtils.setLookAndFeelByName("Windows");
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        java.awt.EventQueue.invokeLater(() -> new FrameMain().setVisible(true));

    }

    public static void tests(String[] arr){
        for (String s: arr) {
            BigDecimal bd = new BigDecimal(s);
            System.out.println(bd + "\n");
        }
    }


}
