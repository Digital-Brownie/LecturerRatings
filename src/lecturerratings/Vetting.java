/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturerratings;

import java.util.Arrays;
import static lecturerratings.TestVetting.numStudents;
//import static lecturerratings.TestVetting.rates;

/**
 *
 * @author Alex
 */
public class Vetting
{

    public Vetting(int[][] rates, String name)
    {
        this.rates = rates;
        this.name = name;
    }
    int[][] rates;
    String name;

    //stores, increments and prints out the frequency of each
    //rate in the rates array.
    private void rateFrequency()
    {
        String[] frequency = new String[]{"","","","","","","","","","",""};
        for (int i = 0; i < numStudents; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                int rating = rates[i][j];
                frequency[rating] += "*";
            }
        }
        System.out.println("Overall Rate Distribution:");
        for (int i = 0; i < 11; i++)
        {
            System.out.printf("%d: ", i);
            System.out.println(frequency[i]);       
        }
    }

    //Prints the data stored in the rates[][] array with
    //apropriate headings
    protected void outputRates()
    {
        System.out.println("=======================================================================================================");
        System.out.printf("The rates are: %n%n              Assessment 1 Assessment 2 Assessment 3 Assessment 4 Assessment 5 %n");

        for (int i = 0; i < numStudents; i++)
        {
            System.out.printf("Student  %d    %-13d%-13d%-13d%-13d%-13d%n", i + 1, rates[i][0], rates[i][1], rates[i][2], rates[i][3], rates[i][4]);
        }
    }

    //Calls methods associated with processing assessment rates 
    //and displays them
    protected void processRates()
    {
        System.out.printf("%s%n%n", Arrays.toString(getTotal()));
        System.out.println("The last number in the total array is " + getTotal(4));
        System.out.println("Good job on a high score of " + getMax());
        System.out.printf("You can improve on a score of %d%n%n", getMin());
        rateFrequency();
    }

    //Finds the highest rating 
    private int getMax()
    {
        int[] total = new int[5];
        int max = 0;

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < numStudents; j++)
            {
                total[i] += rates[j][i];
            }
            if (total[i] > max)
            {
                max = total[i];
            }
        }
        return max;
    }

    //Finds the lowest rating
    private int getMin()
    {
        int[] total = new int[5];
        int min = 10000;

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < numStudents; j++)
            {
                total[i] += rates[j][i];
            }
            if (total[i] < min)
            {
                min = total[i];
            }
        }
        return min;
    }

    //Finds the total rating for each assessment and
    //returns them in the total[] array
    private int[] getTotal()
    {
        int[] total = new int[5];

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < numStudents; j++)
            {
                total[i] += rates[j][i];
            }
        }
        return total;
    }

    //Finds the total rating for each assessment and returns 
    //a single assessments total based on the assessment parameter
    private int getTotal(int assessment)
    {
        int[] total = new int[5];

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < numStudents; j++)
            {
                total[i] += rates[j][i];
            }
        }
        return total[assessment];
    }

}
