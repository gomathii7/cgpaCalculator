/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package webtech.s;

import java.io.*;
import java.math.*;
import java.util.*;
import java.lang.Thread;

interface Details {
    void getDetails();
    void display();
}

class Subject{
    String subjectName;
    String subjectCode;
    String staffName;
    int credits;
}

class Marks extends Subject{
    String grade;
    float internalMarks;
    float externalMarks;
    float totalMarks;
}

class MultiThreadGetMarks implements Runnable{
     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private Marks tempM;
    public MultiThreadGetMarks(Marks m){
        tempM = m;
    }
    public void run()
    {   
        try {
            System.out.println("Subject name: " + tempM.subjectName + "\tSubject marks: "+ tempM.totalMarks);
        }
        catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught: "+e);
        }
        
    }
}

class Results{
    int totalSubjects;
    double cgpa;
    double percentage;
    
    public void calculate(Marks m[])throws Exception{
        double total1 = 0, totalCredits = 0, totalMarks = 0;
        HashMap<String, Integer> points = new HashMap<>();
        points.put("O", 10);
        points.put("A+", 9);
        points.put("A", 8);
        points.put("B+", 7);
        points.put("B", 6);
        points.put("E", 0);
        for(int i = 0; i<totalSubjects; i++){
            if(!(m[i].grade).equals("E")){
                totalMarks += m[i].totalMarks;
                int val = points.get(m[i].grade);
                total1 += val*(m[i].credits);
                totalCredits += m[i].credits;
            }
        }
        cgpa = total1/totalCredits;
        percentage = totalMarks/totalSubjects;
    }
}

class Student extends Results implements Details{
    String studentName;
    String regNumber;
    int semester;
    int year;
    
    public void getDetails(){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try{
            
            System.out.print("Enter Student Name: ");
            studentName = in.readLine();
            System.out.print("Enter Student Registration Number: ");
            regNumber = in.readLine();
            System.out.print("Enter year: ");
            year = Integer.parseInt(in.readLine());
            System.out.print("Enter semester: ");
            semester = Integer.parseInt(in.readLine());
            System.out.print("Enter total number of subjects: ");
            totalSubjects = Integer.parseInt(in.readLine());
            Marks[] m = new Marks[totalSubjects];
            MultiThreadGetMarks mtgm[] = new MultiThreadGetMarks[totalSubjects];
            Thread object[] = new Thread[totalSubjects];
            for(int i = 0; i<totalSubjects; i++){
                m[i] = new Marks();
                System.out.print("\nEnter Subject Name: ");
                m[i].subjectName = in.readLine();
                System.out.print("\nEnter Subject Code: ");
                m[i].subjectCode = in.readLine();
                System.out.print("\nEnter Staff Name: ");
                m[i].staffName = in.readLine();
                System.out.print("\nEnter credits for the subject: ");
                m[i].credits = Integer.parseInt(in.readLine());
                System.out.print("\nEnter marks for the subject: " + m[i].subjectCode);
                System.out.print("\nEnter Internal Marks (50): ");
                m[i].internalMarks = Float.parseFloat(in.readLine());
                System.out.print("\nEnter External Marks (100): ");
                m[i].externalMarks = Float.parseFloat(in.readLine());
                m[i].totalMarks = m[i].internalMarks + (m[i].externalMarks/2);
                if(m[i].totalMarks > 90){
                    m[i].grade = "O";
                }
                else if(m[i].totalMarks > 80){
                    m[i].grade = "A+";
                }
                else if(m[i].totalMarks > 70){
                    m[i].grade = "A";
                }
                else if(m[i].totalMarks > 60){
                    m[i].grade = "B+";
                }
                else if(m[i].totalMarks > 50){
                    m[i].grade = "B";
                }
                else{
                    m[i].grade = "E";
                }
                System.out.println("Grade is: "+m[i].grade);
                System.out.println("\n\n*************************************");        
            }
            System.out.println("\n*******************************\n");
            System.out.println("\nDisplaying total marks of all subjects using threads\n");
            System.out.println("\n*******************************\n");
            for(int i = 0; i<totalSubjects; i++){
                mtgm[i] = new MultiThreadGetMarks(m[i]);
                object[i] = new Thread(mtgm[i]);
                object[i].start();
                object[i].join();
            }
            System.out.println("\n*******************************\n");
            calculate(m);
        }
        catch(Exception e){
            System.out.println("Exception caught: " + e);
        }
    }
    public void display(){
        System.out.println("\n*******************************\n");
        System.out.println("\t\tResults");
        System.out.println("\n*******************************\n");
        System.out.println("Student Name: " + studentName);
        System.out.println("Student Name: " + regNumber);
        System.out.println("CGPA : " + cgpa);
        System.out.println("Percentage : " + percentage);
    }
    
}

class Solution {        
    public static void main (String[] args)throws IOException {
    try{
        System.out.println("*****************************************************\n");
        System.out.println("\t\tWelcome to CGPA calculator");
        System.out.println("\n*****************************************************\n");
        Student s = new Student();
        s.getDetails();
        s.display();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    }
}