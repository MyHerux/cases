package com.heroxu.casses.algorithm;

public class EgyptianFraction {

    public static void main(String[] args) {
       egyptFraction(31,311);
    }

    public static void egyptFraction(int numerator, int denominator) {

        int total=0; //计数器
        System.out.print(numerator + "/" + denominator + "=");
        while(true) {
            /* 如果分子能够整除分母,则对原分数进行约分 , 保持分子为1 */
            if (denominator % numerator == 0) {
                denominator = denominator / numerator;
                numerator = 1;
            }

            /* 分子为1时,直接输出结果,中止循环 */
            if (numerator == 1) {
                System.out.print(numerator + "/" + denominator);
                total++;
                break;
            } else {
                /* 保留原有的分子与分母的值 */
                int d = denominator;
                int n = numerator;

                /* 得到比当前分数小的最大埃及分数的分母,并输出找到的最大埃及数 */
                int num = denominator / numerator + 1;
                System.out.print(1 + "/" + num + "+");
                total++;

                /* 得到余数的分子与分母,继续循环 */
                denominator = d * num;
                numerator = n * num - d;
            }
        }
        System.out.println();
        System.out.println("可分解为埃及数的个数为: " + total);
    }


    public static int maxComDiv(int a, int b) {
        int remaind = 0;
        while (b != 0) {
            remaind = a % b;
            a = b;
            b = remaind;
        }
        return a;
    }

}
