/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CountPhomLogic;
import Model.Card;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class MainEntrance {
    public static void main(String[] args) {
        CountPhomLogic countPhomLogic = new CountPhomLogic();
        String option, temp;
        Scanner sc = new Scanner(System.in);
        int sophom=0;
        do
        {
            System.out.println("=====================");
            System.out.println("Nhập 1 để tự nhập hand mới");
            System.out.println("Nhập 2 để được phát 1 hand ngẫu nhiên");
            System.out.println("Nhập 0 để thoát");
            System.out.println("=====================");
            System.out.println("Lựa chọn của bạn : ");
            do
            {
                option = sc.nextLine();
            }
            while(!(option.equals("0")||option.equals("1")||option.equals("2")));
            
            if(option.equals("1"))
            {
                int tempValue, tempSuit;
                ArrayList<Card> myCards = new ArrayList<>();
                for(int i=0;i<10;i++)
                {
                    System.out.println("Mời nhập giá trị của lá bài thứ " + (i+1) + "(A=1, J=11, Q=12, K=13) :");
                    tempValue = sc.nextInt();
                    System.out.println("Mời nhập chất của lá bài thứ " + (i+1) + "(1 - bích, 2 - tép, 3 - dô, 4 - cơ) :");
                    tempSuit = sc.nextInt();
                    myCards.add(new Card(tempValue,tempSuit));
                }
                
                System.out.println("Các lá bài của bạn là : ");
                countPhomLogic.printHand(myCards);
                System.out.println("");            

                sophom = countPhomLogic.countPhom(myCards);
                System.out.println("===> Trong tay bạn có " + sophom + " phỏm");
                System.out.println("");
                
                System.out.println("Bài sau khi đã sắp xếp : ");
                countPhomLogic.printHand(myCards);
                System.out.println("");
            }
            
            if(option.equals("2"))
            {
                ArrayList<Card> myCards = countPhomLogic.getRandomHand(); 
                
                System.out.println("Các lá bài của bạn là : ");
                countPhomLogic.printHand(myCards);
                System.out.println("");            

                System.out.println("Trong tay bạn có " + countPhomLogic.countPhom(myCards) + " phỏm");
                System.out.println("Bài sau khi đã sắp xếp : ");
                countPhomLogic.printHand(myCards);
                System.out.println("");
            }
        }
        while(option!="0");
    }
   
    
}
