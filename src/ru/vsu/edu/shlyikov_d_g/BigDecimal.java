package ru.vsu.edu.shlyikov_d_g;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class BigDecimal {
    String num = "0";
    int exp = 0;

    public BigDecimal(int s){
        new BigDecimal(String.valueOf(s));
    }

    public BigDecimal(double s){
        new BigDecimal(String.valueOf(s));
    }

    public BigDecimal(float s){
        new BigDecimal(String.valueOf(s));
    }

    public BigDecimal(String s) {
        String[] bd = convert(s);
        String num = bd[0];
        String exp = bd[1];
        if (num == null || exp == null) {
            System.out.println("Can not create BigDecimal.");
        } else {
            this.num = num;
            this.exp = parseInt(exp);
        }
    }

    private String getNum() {
        return num;
    }

    private int getExp() {
        return exp;
    }

    public String toString() {
//        return  num + (exp == 0 ? "" : "e" + (exp > 0 ? "-" + exp : "+" + -exp));
        return toFull(this.getNum(), this.getExp());
    }

    public BigDecimal multiply(BigDecimal b2) {
        return prepare("*", b2);
    }

    public BigDecimal multiply(String b2) {
        return prepare("*", new BigDecimal(b2));
    }

    public BigDecimal div(BigDecimal b2) {
        return prepare("/", b2);
    }

    public BigDecimal div(String b2) {
        return prepare("/", new BigDecimal(b2));
    }

    public BigDecimal mod(BigDecimal b2) {
        return prepare("%", b2);
    }

    public BigDecimal mod(String b2) {
        return prepare("%", new BigDecimal(b2));
    }

    public BigDecimal plus(BigDecimal b2) {
        return add("+", b2);
    }

    public BigDecimal plus(String b2) {
        return add("+", new BigDecimal(b2));
    }

    public BigDecimal minus(BigDecimal b2) {
        return add("-", b2);
    }

    public BigDecimal minus(String b2) {
        return add("-", new BigDecimal(b2));
    }

    public BigDecimal add(String sign, BigDecimal b2) {
        return prepare(sign, b2);
    }

    public BigDecimal add(Character sign, BigDecimal b2) {
        return prepare(sign.toString(), b2);
    }

    public BigDecimal add(BigDecimal b2) {
        return plus(b2);
    }

    public BigDecimal add(Character sign, String b2) {
        return prepare(sign.toString(), new BigDecimal(b2));
    }

    public BigDecimal add(String b2) {
        return plus(b2);
    }

//    private BigDecimal multiply(BigDecimal b2){
//        BigDecimal result;
//
//        for (int i = 0; i < ; i++) {
//
//        }
//
//        return result;
//    }

    private BigDecimal prepare(String sign, BigDecimal b2) {
        BigDecimal b1 = this;

        String num1 = b1.getNum();
        StringBuilder num2prepare = new StringBuilder((sign.equals("-") ? "-" : "") + b2.getNum());

        if (num2prepare.length() > 1 && num2prepare.charAt(0) == '-' && num2prepare.charAt(1) == '-') {
            num2prepare.delete(0, 2);
        }

        String num2 = num2prepare.toString();

//        System.out.println(num1 + "  " + num2);

        if (num1.equals("") || num2.equals("")) {
            return null;
        }

        int exp1 = b1.getExp();
        int exp2 = b2.getExp();
        int expTemp = 0;

        if (sign.equals("+") || sign.equals("-")) {
            if (exp1 >= exp2) {
                exp2 -= exp1;
                expTemp += exp1;
                exp1 = 0;
            } else {
                exp1 -= exp2;
                expTemp += exp2;
                exp2 = 0;
            }
        }

//        System.out.println(num1 + "  " + num2);
//        System.out.println(exp1 + "  " + exp2);

        boolean negative1 = (num1.charAt(0) == '-');
        boolean negative2 = (num2.charAt(0) == '-');
        boolean negativeOr = negative1 || negative2;
        boolean negativeAnd = negative1 && negative2;
        boolean negativeMax;

        if (negative1) {
            num1 = num1.substring(1);
        }
        if (negative2) {
            num2 = num2.substring(1);
        }

//        System.out.println(num1 + " " + exp1 + "   " + num2 + " " + exp2);

        if (exp1 < 0) {
            num1 = toFull(num1, exp1);
            exp1 = 0;
        }
        if (exp2 < 0) {
            num2 = toFull(num2, exp2);
            exp2 = 0;
        }

        int len1 = num1.length();
        int len2 = num2.length();

//        System.out.println(num1 + "  " + num2);
//        System.out.println(len1 + "  " + len2);

        int minLen = Math.min(len1, len2);
        int maxLen = Math.max(len1, len2);

        String min;
        String max;

        if (compareTo(num1, num2, exp1, exp2)) {
            min = num2;
            max = num1;
            maxLen = len1;
            minLen = len2;
            negativeMax = negative1;
        } else {
            min = num1;
            max = num2;
            maxLen = len2;
            minLen = len1;
            negativeMax = negative2;
        }

//        System.out.println(len1 + " " + len2);

        return switch (sign) {
            case "+", "-" -> doPlus(expTemp, maxLen, minLen, max, min, negativeAnd, negativeOr, negativeMax);
            case "*" -> doMultiply(exp1 + exp2, maxLen, minLen, max, min, negativeAnd, negativeOr, negativeMax);
            case "/" -> doDivide(b1, b2, true);
            case "%" -> doDivide(b1, b2, false);
            default -> null;
        };

    }

    private BigDecimal doPlus(int expTemp, int maxLen, int minLen, String max, String min, boolean nAnd, boolean nOr, boolean nMax) {
        int temp = 0;
        int tempNew;
        StringBuilder numR = new StringBuilder();

        boolean minus = !nAnd && nOr;

        for (int i = maxLen - 1, j = minLen - 1; i >= 0 || j >= 0; i--, j--) {
            tempNew = (i < 0 ? 0 : Character.getNumericValue(max.charAt(i)))
                    + (minus ? -1 : 1) *
                    (j < 0 ? 0 : Character.getNumericValue(min.charAt(j))) + temp;
            temp = 0;
            if (tempNew < 0) {
                temp -= 1;
                tempNew += 10;
            }
            temp += tempNew / 10;
            tempNew %= 10;

            numR.append(tempNew);
        }

        if (temp != 0) {
            numR.append(temp);
        }

        if (nAnd || ((nOr) && nMax)) {
            numR.append("-");
        }

        numR.reverse();

//        System.out.println(numR);

        return new BigDecimal(numR + (expTemp != 0 ? ("e" + -expTemp) : ""));
    }

    private BigDecimal doMultiply(int expTemp, int maxLen, int minLen, String max, String min, boolean nAnd, boolean nOr, boolean nMax) {
        BigDecimal result = new BigDecimal("1");
//        System.out.println(result);

        int tempNew;
        int temp = 0;

//        System.out.println(minLen + " AAAAAAAAAAA " + maxLen);
//        System.out.println(min    + " AAAAAAAAAAA " + max);

        // СМ НА ОБОРОТЕ

        for (int i = minLen - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder();

            for (int j = maxLen - 1; j >= 0; j--) {
                tempNew = Character.getNumericValue(max.charAt(j))
                        * Character.getNumericValue(min.charAt(i)) + temp;
                temp = tempNew / 10;
                tempNew %= 10;
                sb.append(tempNew);
//                System.out.println("///////\n" + i + " " + j);
//                System.out.println(min.charAt(i) + " _ " + max.charAt(j));
//                System.out.println(tempNew + "  " + temp + "\n///////");
//                System.out.println(sb);
            }

            if (temp != 0) {
                sb.append(temp);
            }

//            System.out.println("sb= " + sb);

            sb.reverse();

//            System.out.println("sbRevers= " + sb);
//
//            System.out.println("minLen= " + minLen);
//            System.out.println("i== " + i);
//            System.out.println("expTemp= " + expTemp);

            if (nOr && !nAnd) {
                result = result.minus(sb + "e" + (minLen - 1 - i - expTemp));
            } else {
                result = result.plus(new BigDecimal(sb + "e" + (minLen - 1 - i - expTemp)));
            }

//            System.out.println("resultt= " + result);

            temp = 0;
        }

        result = new BigDecimal(toFull(result.getNum(), result.getExp()));
//        System.out.println(result + " num= " + result.getNum() + " exp= " + result.getExp());

        return result.minus("1");
    }

    private BigDecimal doDivide(BigDecimal bd1, BigDecimal bd2, boolean div) {

        int exp1 = bd1.getExp();
        int exp2 = bd2.getExp();

        int avgExp = Math.max(Math.max(0, exp1), Math.max(0, exp2));

//        System.out.println("avgExp= " + avgExp);
//
        BigDecimal test1 = toFullView(bd1, avgExp);
        BigDecimal test2 = toFullView(bd2, avgExp);

//        System.out.println(test1 + " numSUPER1= " + test1.getNum() + " expSUPER1= " + test1.getExp());
//        System.out.println(test2 + " numSUPER2= " + test2.getNum() + " expSUPER2= " + test2.getExp());

        String num1 = test1.toString();
        String num2 = test2.toString() ;

        boolean nOr = false;
        boolean nAnd = false;

        if (num1.charAt(0) == '-'){
            num1 = num1.substring(1);
            nOr = true;
        }
        if (num2.charAt(0) == '-'){
            num2 = num2.substring(1);
            nAnd = nOr;
            nOr = true;
        }

//        System.out.println(num1 + "    " + num2);

        BigDecimal temp = new BigDecimal(num1.substring(0, num2.length()));
        BigDecimal sec;
        BigDecimal tempPlus = new BigDecimal(num2);

        StringBuilder sb = new StringBuilder();

        int j;

        for (int qi = num2.length() - 1; qi < num1.length(); qi++) {
            sec = new BigDecimal("0");
            j = 0;

            if (qi >= num2.length()){
//                System.out.println("i= " + qi);
//                System.out.println("temp= " + temp);
                temp = temp.multiply("10").plus(new BigDecimal(Character.toString(num1.charAt(qi))));
//                System.out.println("char= " + Character.toString(num1.charAt(qi)));
//                System.out.println("temp= " + temp);
//                System.out.println("tempNum= " + temp.getNum());
//                System.out.println("tempExp= " + temp.getExp());
//                System.out.println("num1= " + num1);
            }

            while (compareTo(temp, sec)){
                j++;
                sec = sec.plus(tempPlus.toString());
//                System.out.println("j= " + j + " sec= " + sec);
            }

            if (compareTo(sec, temp) != compareTo(temp, sec)){
//                System.out.println("IM HERE");
                j--;
                sec = sec.minus(tempPlus);
            }

//            System.out.println("sec= " + sec);
//            System.out.println("temp= " + temp);
//            System.out.println("j= " + j);

            temp = temp.minus(sec);

//            System.out.println("temp= " + temp);

            if (qi == num2.length() - 1 && j == 0) {
            }
            else{
                sb.append(j);
            }

//            System.out.println("sb= " + sb);
//            System.out.println();
        }

        return div ? new BigDecimal((!nAnd && nOr ? "-" : "") + sb) : new BigDecimal((!nAnd && nOr ? "-" : "") + temp + "e" + -avgExp);
    }

        private BigDecimal doDivideOld(BigDecimal bd1, BigDecimal bd2, boolean div) {
        BigDecimal temp = new BigDecimal(0);

        String num1 = bd1.getNum();
        String num2 = bd2.getNum();

        boolean nAnd = false;
        boolean nOr = false;

        if (num1.charAt(0) == '-' || num2.charAt(0) == '-'){
            nOr = true;
            if (num1.charAt(0) == '-' && num2.charAt(0) == '-'){
                nAnd = true;
            }
        }

        int i = 0;

        while (compareTo(bd1.getNum(), temp.getNum(), bd1.getExp(), temp.getExp())){
            if(!nAnd && nOr){
                i--;
                temp = temp.minus(bd2);
            }
            else{
                i++;
                temp = temp.plus(bd2);
            }
        }

        if(!compareTo(bd1.getNum(), temp.getNum(), bd1.getExp(), temp.getExp())
                && compareTo(temp.getNum(), bd1.getNum(), temp.getExp(),  bd1.getExp())) {
            if (!nAnd && nOr) {
                i++;
                temp = temp.plus(bd2);
            } else {
                i--;
                temp = temp.minus(bd2);
            }
        }

        return div ? new BigDecimal(String.valueOf(i)) : bd1.minus(temp);
    }

    private String toFull(String num, int exp) {
        StringBuilder sb;

        if (exp < 0) {
            sb = new StringBuilder(num);
            sb.append("0".repeat(-exp));
        } else {
            int diff = num.length() - exp;
//            System.out.println(diff);
//            System.out.println(num.length());
//            System.out.println(num);
//            System.out.println(exp);
            sb = new StringBuilder();
            for (int i = -1; i < num.length(); i++) {
//                System.out.println("i= " + i);
                if (diff <= 0 && i == -1 && exp != 0) {
                    sb.append("0");
                    sb.append(".");
                    sb.append("0".repeat(Math.abs(diff)));
                } else if (i > -1 && num.charAt(i) != '.') {
                    if (i == diff && exp != 0) {
                        sb.append(".");
                    }
                    sb.append(num.charAt(i));
                }
            }
        }
        return sb.toString();
    }

    private BigDecimal toFullView(String num, int exp, int expAdd){
        return toFullView(new BigDecimal(num + "e" + exp), expAdd);
    }

    private BigDecimal toFullView(BigDecimal bd, int exp){
        BigDecimal result = bd;

//        System.out.println(bd);
//        System.out.println(result);
//        System.out.println();

        while (exp > 0){
            result = result.multiply(new BigDecimal("10"));
            exp -= 1;
        }

//        System.out.println(result + " num= " + result.getNum() + " exp= " + exp);

        return result;
    }

    private boolean compareTo(BigDecimal bd1, BigDecimal bd2){
        return compareTo(bd1.getNum(), bd2.getNum(), bd1.getExp(), bd2.getExp());
    }

    private boolean compareTo(String num1, String num2, int exp1, int exp2) {
        if (num1.charAt(0) == '-') {
            num1 = num1.substring(1);
        }
        if (num2.charAt(0) == '-') {
            num2 = num2.substring(1);
        }

        int a = num1.length() - exp1;
        int b = num2.length() - exp2; // ALARM

        int maxLen = Math.max(a, b);
        int minLen = Math.min(a, b);

        int avgLen = maxLen - minLen;

        boolean firstMax = maxLen == a;

        int avgExp = Math.max(Math.max(0, exp1), Math.max(0, exp2));

//        System.out.println("YEnum1= " + num1);
//        System.out.println("YEexp1= " + exp1);
//        System.out.println("YEnum2= " + num2);
//        System.out.println("YEexp2= " + exp2);
//
//        System.out.println("YEavgExp= " + avgExp);

        num1 = toFullView(num1, exp1, avgExp).toString();
        num2 = toFullView(num2, exp2, avgExp).toString();

//        System.out.println("NOnum1= " + num1);
//        System.out.println("NOexp1= " + exp1);
//        System.out.println("NOnum2= " + num2);
//        System.out.println("NOexp2= " + exp2);

        a = num1.length();
        b = num2.length();

        maxLen = Math.max(a, b);

        int i = 0;

//        System.out.println("num1= " + num1 + "    exp1= " + exp1 + " fmAx " + firstMax);
//        System.out.println("num2= " + num2 + "    exp2= " + exp2);
//        System.out.println("maxLen=   " + maxLen + "  minLen= " + minLen);
//        System.out.println(avgLen + " - eto avg");

        while (i < maxLen) { // ПЕРЕПИСАТЬ
//            System.out.println("i= " + i);
            if (firstMax) {
                if (i - avgLen >= 0) {
                    if (num1.charAt(i) > num2.charAt(i - avgLen)) {
//                        System.out.println("CODE " + 1);
                        return true;
                    } else if (num1.charAt(i) == num2.charAt(i - avgLen)) {
//                        System.out.println("CODE " + 2);
                        i++;
                    } else {
//                        System.out.println("CODE " + 3);
                        return false;
                    }
                } else {
//                    System.out.println("CODE " + 4);
                    return true;
                }
            } else {
                if (i - avgLen >= 0) {
                    if (num2.charAt(i) > num1.charAt(i - avgLen)) {
//                        System.out.println("CODE " + 5);
                        return false;
                    } else if (num2.charAt(i) == num1.charAt(i - avgLen)) {
//                        System.out.println("CODE " + 6);
                        i++;
                    } else {
//                        System.out.println("CODE " + 7);
                        return true;
                    }
                } else {
//                    System.out.println("CODE " + 8);
                    return false;
                }
            }
        }
//        System.out.println("CODE " + "DEFAULT");
        return true;
    }

    private String[] convert(String s) {
        // "234.123" or "234,123" -> [234123][-3]
        // "234123e-4" or "234123E-4" -> [234123][-4]
        // "-.12345" or "-0.12345" or "-,12345" -> [-12345][-5]
        // "+000.000001" or "+1e-6" -> [1][-6]

        StringBuilder num = new StringBuilder();
        StringBuilder exp = new StringBuilder();
        String eDict = "eE";

        boolean expFlag = false;
        boolean pointFlag = false;
        boolean zeroInARowStart = true;
        boolean zeroInARowSEnd = false;

        int zeroInARow = 0;

        int expInt = 0;

        String pattern = "[-+]?([0-9]*[.,])?[0-9]+([eE][-+]?\\d+)?";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
//        System.out.println(m.matches() + "\n" + s);
        if (!m.matches()) {
            System.out.println("Something went wrong!");
            return new String[]{"0", "0"};
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            char pCh = i > 0 ? s.charAt(i - 1) : ch;

            switch (ch) {
                case '-', '+':
                    if (i == 0) {
                        if (ch == '-') {
                            num.append("-");
                        }
                    } else if (expFlag && eDict.contains(String.valueOf(pCh))) {
                        if (ch == '-') {
                            exp.append(ch);
                        }
                    }
                    break;
                case '.', ',':
                    pointFlag = true;
                    break;
                case 'e', 'E':
                    expFlag = true;
                    break;
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                    if (expFlag) {
                        exp.append(ch);
                        pointFlag = false;
                    } else {
                        if (ch == '0') {
                            zeroInARow++;
                        } else {
                            if (zeroInARowStart) {
                                zeroInARowStart = false;
                            } else {
                                num.append("0".repeat(zeroInARow));
                            }
                            zeroInARow = 0;
                            num.append(ch);
                        }
                    }
                    if (pointFlag) {
                        expInt--;
                    }
                    break;
            }
        }

        if (exp.toString().equals("")) {
            exp.append(0);
        }

        if (num.length() == 0) {
            num.append(0);
            expInt = 0;
            exp.delete(0, exp.length());
            exp.append(0);
            zeroInARow = 0;
        } else if (num.charAt(num.length() - 1) == '0') {
            num.delete(num.length() - zeroInARow, num.length() - 1);
        }

        int numLen = num.length();

//        System.out.println(s + " aaa " + numLen + " bbb " + zeroInARow + " ccc " + num);

        if (num.toString().equals("") || num.toString().equals("-")) {
            num.delete(0, num.length());
            num.append("0");
            expInt = 0;
            exp.delete(0, exp.length());
            exp.append("0");
            zeroInARow = 0;
        }

//        if (numLen > 0 && !s.equals("0") && zeroInARow > 0 ){
//                num.delete(numLen - zeroInARow, numLen);
////            System.out.println(s + " aaa " + numLen + " bbb " + zeroInARow + " ccc " + num);
////            System.out.println("WELL I AM HERE");
////            System.out.println(zeroInARow);
//        }

        if (zeroInARow > 0 && !pointFlag){
            num.append("0".repeat(zeroInARow));
            zeroInARow = 0;
        }

        expInt = -(parseInt(exp.toString()) + expInt + zeroInARow);

        exp.delete(0, exp.length());
        exp.append(expInt);

//        System.out.println(num + "  " + exp);

//        System.out.println("\nnum= " + num + "\nexp= " + exp + "\nexpInt= " + expInt + "\n");
//        System.out.println("eFlag= " + expFlag + "\npFlag= " + pointFlag);

//        System.out.println("num= " + num + "\nexp= " + exp);

        return new String[]{num.toString(), exp.toString()
        };
    }
}