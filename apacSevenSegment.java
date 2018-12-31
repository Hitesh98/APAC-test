import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class apacSevenSegment {

    private final static String[] DIGITCOMBINATIONS = { "1111110",
                                                        "0110000",
                                                        "1101101",
                                                        "1111001",
                                                        "0110011",
                                                        "1011011",
                                                        "1011111",
                                                        "1110000",
                                                        "1111111",
                                                        "1111011" };

    private static int[] INERROR = new int[7];
    private static int updates = 0;

    private static boolean match(String digit, int number) {
        String numString = DIGITCOMBINATIONS[number];
        return apacSevenSegment.match(digit, numString);
    }

    private static boolean match(String digit, String numString) {
        int k =0;
        for(; k<7; k++) {
            if ((numString.charAt(k) == '0' && digit.charAt(k) == '1') ||
                (numString.charAt(k) == '1' && ((digit.charAt(k) == '0' && INERROR[k] == 2) || (digit.charAt(k) == '1' && INERROR[k] == 1))))
            {
                break;
            }
        }
        return k==7;
    }

    private static ArrayList<Integer> findDigits(String toConvert) {
        ArrayList<Integer> converted = new ArrayList<>();
        for(int j =0; j< 10; j++) {
            if(apacSevenSegment.match(toConvert, j))
                converted.add(j);
        }
        return converted;
    }

    private static ArrayList<Integer> decreaseList(ArrayList<Integer> a) {
        for(int i =0; i< a.size(); i++) {
            a.set(i, ((a.get(i)-1)+100)%10);
        }
        return a;
    }

    private int work(String[] digits) {
        ArrayList<Integer> firstList = findDigits(digits[0]);
        updateError(firstList, digits[0]);
        for(int i =1; i< digits.length; i++) {
            decreaseList(firstList);
            if (firstList.isEmpty()) {
                return -1;
            }
            int sz = firstList.size();
            for (int j =0; j< sz; j++) {
                if(!match(digits[i] , firstList.get(j))) {
                    firstList.remove(firstList.get(j));
                    j--;
                    sz--;
                }
            }
            updateError(firstList, digits[i]);
        }
        for (int x : firstList) {
            System.out.print(x + " ");
        }
        if (firstList.size() == 1) {
            return decreaseList(firstList).get(0);
        }
        return -1;
    }

    private void findError(int answer, String[] initialList) {
        String[] newList = new String[initialList.length];
        for(int i = initialList.length-1; i>=0 && updates < 7; i--) {
            newList[i] = DIGITCOMBINATIONS[((++answer)+100)%10];
            for(int j =0; j< 7; j++) {
                if(newList[i].charAt(j) == '1' && initialList[i].charAt(j) == '0' && INERROR[j] == 0) {
                    INERROR[j] = 1;
                    updates++;
                }
                else if(newList[i].charAt(j) == '1' && initialList[i].charAt(j) == '1' && INERROR[j] == 0) {
                    INERROR[j] = 2;
                    updates++;
                } else if (newList[i].charAt(j) == '0' && initialList[i].charAt(j) == '1' && INERROR[j] == 0) {
                    INERROR[j] = 3;
                    updates++;
                }
            }
        }
    }

    private static void updateError(ArrayList<Integer> arr, String output) {
        for (int i = 0; i<7; i++) {
            int k =0, sz = arr.size()-1;
            for (int j = 0; j< sz; j++) {
                if (DIGITCOMBINATIONS[arr.get(j)].charAt(i) != DIGITCOMBINATIONS[arr.get(j+1)].charAt(i)) {
                    k=1;
                    break;
                }
            }
            if (k == 0) {
                if (DIGITCOMBINATIONS[arr.get(arr.size() - 1)].charAt(i) != output.charAt(i)) {
                    if(DIGITCOMBINATIONS[arr.get(arr.size() - 1)].charAt(i) == '0') {
                        INERROR[i] = 3;
                        updates++;
                    } else {
                        INERROR[i] = 1;
                        updates++;
                    }
                } else if (output.charAt(i) == '1') {
                    INERROR[i] = 2;
                    updates++;
                }
            }
        }
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int trial = sc.nextInt(), count = 1;
        while(trial-- >0) {
            Arrays.fill(INERROR, 0);
            int n = sc.nextInt();
            String[] s = new String[n];
            int index=0;
            while(n-->0) {
                s[index++] = sc.next();
            }
            apacSevenSegment a = new apacSevenSegment();
            int answer = (a.work(s));
            if (answer == -1) {
                System.out.println("Case #" + count++ +": " + "ERROR!");
            } else {
                StringBuilder str = new StringBuilder(DIGITCOMBINATIONS[answer]);
                a.findError(answer, s);
                for (int i = 0; i < 7; i++) {
                    if (INERROR[i] == 3) {
                        System.out.println("Case #" + count++ +": " + "ERROR!");
                        break;
                    }
                    if (str.charAt(i) == '1' && INERROR[i] == 1) {
                        str.setCharAt(i, '0');
                    }
                }
                System.out.println("Case #" + count++ +": " + str);
            }
        }
    }/*
        apacSevenSegment a = new apacSevenSegment();
        for (int x : apacSevenSegment.INERROR) {
            System.out.print(x + " ");
        }
        System.out.println();
        int updates = 3;
        String output = "0000100";
        ArrayList<Integer> l = new ArrayList<>();
        l.add(0);
        l.add(2);
        l.add(6);
        l.add(8);
        apacSevenSegment.updateError(l, output);
        for (int x : apacSevenSegment.INERROR) {
            System.out.print(x + " ");
        }
        System.out.println(apacSevenSegment.match("0000000", 9));
    }*/
}
