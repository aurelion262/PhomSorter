/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author ADMIN
 */
public class CountPhomLogic {
    
    private ArrayList<Card> deck;
    
    public ArrayList<Card> getRandomHand()
    {
        ArrayList<Card> hand = new ArrayList<>();
        for(int i=0;i<10;i++)
        {            
            Card c = new Card((int)(Math.random()*13+1),(int)(Math.random()*4+1));
            if(thisHandContainsThisCard(hand, c))
            {
                i--;
            }
            else
            {
                hand.add(c);
            }
        }
        return hand;
    }
    
    // Kiểm tra xem 2 là bài có giống nhau không. Trả về true nếu cả giá trị và chất bằng nhau
    public boolean these2CardsAreEqual(Card c1, Card c2)
    {
        return (c1.getValue() == c2.getValue() && c1.getSuit() == c2.getSuit());
    }
    
    // Kiểm tra xem trong hand truyền vào có chứa lá bài c hay không
    public boolean thisHandContainsThisCard(ArrayList<Card> hand, Card c)
    {
        for(Card c1 : hand)
        {
            if(these2CardsAreEqual(c1, c))
            {
                return true;
            }
        }
        return false;
    }
    
    // Trả về index của lá bài c trong list. Nếu hand không chứa c, trả về -1
    public int indexOfThisCardInThisHand(ArrayList<Card> hand, Card c)
    {
        for(int i=0;i<hand.size();i++)
        {
            if(these2CardsAreEqual(hand.get(i),c))
            {
                return i;
            }
        }
        return -1;
    }
    
    // Xóa lá bài c khỏi hand
    public void removeThisCardFromThisHand(ArrayList<Card> hand, Card c)
    {
        if(thisHandContainsThisCard(hand, c))
        {
            hand.remove(indexOfThisCardInThisHand(hand, c));
        }
    }
    
    //Sắp xếp hand theo thứ tự quân bài từ nhỏ tới lớn, nếu nhiều quân bài giá trị bằng nhau thì xếp theo thứ tự bích, dô, tép, cơ
    public void sortHandByValueThenSuit(ArrayList<Card> hand)
    {
        hand.sort
        (
            new Comparator<Card>()
            {
                @Override
                public int compare(Card c1, Card c2)
                {
                    if(c1.getValue()==c2.getValue())
                    {
                        if(c1.getSuit()<c2.getSuit())
                        {
                            return -1;
                        }
                        else
                        {
                            return 1;
                        }
                    }
                    else if(c1.getValue()<c2.getValue())
                    {
                        return -1;
                    }
                    else
                    {
                        return 1;
                    }
                }                
            }
        );
    }
    
    //Sắp xếp hand theo thứ tự chất bích, dô, tép, cơ, nếu nhiều quân bài có chất giống nhau thì xếp theo giá trị từ nhỏ đến lớn
    public void sortHandBySuitThenValue(ArrayList<Card> hand)
    {
        hand.sort
        (
            new Comparator<Card>()
            {
                @Override
                public int compare(Card c1, Card c2)
                {
                    if(c1.getSuit()==c2.getSuit())
                    {
                        if(c1.getValue()<c2.getValue())
                        {
                            return -1;
                        }
                        else
                        {
                            return 1;
                        }
                    }
                    else if(c1.getSuit()<c2.getSuit())
                    {
                        return -1;
                    }
                    else
                    {
                        return 1;
                    }
                }                
            }
        );
    }
    
    //Trả về số phỏm có trong hand truyền vào và sắp xếp lại hand theo phỏm
    //Sẽ có 2 cách xếp phỏm dựa theo cách dùng quân A. Thực hiện lần lượt 2 cách xếp và so sánh kết quả để quyết định chọn cách tốt hơn
    public int countPhom(ArrayList<Card> hand)
    {    
        
        //*****************************************************************************************************************************
        //*****************************Xếp phỏm cách 1 : Các cây A nếu được dùng trong dây đều đứng trước 2****************************
        //*****************************************************************************************************************************
        
        //Đếm và lưu các bộ 3, tứ quý
        ArrayList<ArrayList<Card>> sets1 = new ArrayList<>();
        sortHandByValueThenSuit(hand);
        int currentValue = hand.get(0).getValue(), currentSetLength=1;        
        for(int i=1;i<hand.size();i++)
        {
            if(hand.get(i).getValue()==currentValue)
            {
                currentSetLength++;
            }
            else
            {
                if(currentSetLength>=3)
                {
                    ArrayList<Card> set = new ArrayList<>();
                    for(int j=i-currentSetLength;j<=i-1;j++)
                    {
                        set.add(hand.get(j));
                    }
                    sets1.add(set);
                }
                currentSetLength=1;
                currentValue=hand.get(i).getValue();
                continue;
            }
            if(i==hand.size()-1)
            {
                if(currentSetLength>=3)
                {
                    ArrayList<Card> set = new ArrayList<>();
                    for(int j=i+1-currentSetLength;j<=i;j++)
                    {
                        set.add(hand.get(j));
                    }
                    sets1.add(set);
                }
            }
        }        
        
        //Đếm và lưu các dây
        ArrayList<ArrayList<Card>> straights1 = new ArrayList<>(); 
        sortHandBySuitThenValue(hand);
        currentValue = hand.get(0).getValue();
//        boolean aceAdded = false;
        int currentSuit = hand.get(0).getSuit(), currentStraightLength=1;
        for(int i=1;i<hand.size();i++)
        {
            
            if(hand.get(i).getSuit()!=currentSuit || hand.get(i).getValue()!=currentValue+1)
            {
                if(currentStraightLength>=3)
                {
                    ArrayList<Card> straight = new ArrayList<>();
                    for(int j=i-currentStraightLength;j<=i-1;j++)
                    {
                        straight.add(hand.get(j));
                    }
                    straights1.add(straight);
                }
                currentValue = hand.get(i).getValue();
                currentSuit = hand.get(i).getSuit();
                currentStraightLength = 1;
            }
            if(hand.get(i).getValue()==currentValue+1 && hand.get(i).getSuit() == currentSuit)
            {
                currentValue = hand.get(i).getValue();
                currentStraightLength++;
            }
            if(i==hand.size()-1)
            {
                if(currentStraightLength>=3)
                {
                    ArrayList<Card> straight = new ArrayList<>();
                    for(int j=i+1-currentStraightLength;j<=i;j++)
                    {
                        straight.add(hand.get(j));
                    }
                    straights1.add(straight);
                }
            }
        }
        
        // Tách dây to thành các dây nhỏ ( vd: 1,2,3,4,5,6 -> 1,2,3 và 4,5,6 )
        Iterator<ArrayList<Card>> straights1Iter = straights1.listIterator();
        ArrayList<ArrayList<Card>> straights1ToAdd = new ArrayList<>();
        while(straights1Iter.hasNext())
        {
            ArrayList<Card> straight = straights1Iter.next();
            Iterator<Card> straightIter = straight.listIterator();
            while(straight.size()/3>1)
            {
                ArrayList<Card> tempStraight = new ArrayList<>();
                for(int i=1;i<=3 && straightIter.hasNext();i++)
                {
                    Card cStraight = straightIter.next();
                    tempStraight.add(cStraight);
                    straightIter.remove();
                }
                straights1ToAdd.add(tempStraight);
            }
        }
        straights1.addAll(straights1ToAdd);
        
        //Tìm những lá bài đang được dùng chung cho cả dây và bộ và quyết định lá bài đó sẽ dùng cho dây hay bộ
        int numberOfStraightsWillGetIfDeleteThisSet = 0, numberOfSetsWillGetIfDeleteThisStraight = 0;
        boolean deleteStraight=false;
        straights1Iter = straights1.listIterator();
        while(straights1Iter.hasNext())
        {
            ArrayList<Card> straight = straights1Iter.next();
            Iterator<Card> straightIter = straight.listIterator();
            while(straightIter.hasNext())
            {
                Card cStraight = straightIter.next();
                Iterator<ArrayList<Card>> sets1Iter = sets1.listIterator();
                while(sets1Iter.hasNext())
                {
                    ArrayList<Card> set = sets1Iter.next();
                    Iterator<Card> setIter = set.listIterator();
                    if(thisHandContainsThisCard(set, cStraight))
                    {
                        if(straight.indexOf(cStraight)%3<straight.size()%3)
                        {
                            straightIter.remove();
                        }
                        else if(set.size()==4)
                        {
                            removeThisCardFromThisHand(set, cStraight);
                        }
                        else if(set.size()==3)
                        {
                            while(setIter.hasNext())
                            {
                                Card cSet = setIter.next();
                                for(ArrayList<Card> straight2 : straights1)
                                {
                                    if(thisHandContainsThisCard(straight2, cSet))
                                    {
                                        numberOfStraightsWillGetIfDeleteThisSet++;
                                        break;
                                    }
                                }
                            }
                            for(Card cStraight2 : straight)
                            {
                                for(ArrayList<Card> set2 : sets1)
                                {
                                    if(thisHandContainsThisCard(set2, cStraight2))
                                    {
                                        numberOfSetsWillGetIfDeleteThisStraight++;
                                    }
                                }
                            }
                            if(numberOfStraightsWillGetIfDeleteThisSet>=numberOfSetsWillGetIfDeleteThisStraight)
                            {
                                sets1Iter.remove();
                            }
                            else
                            {
                                deleteStraight=true;
                                break;
                            }
                        }
                    }
                }
                if(deleteStraight)
                {
                    break;
                }
            }
            if(deleteStraight)
            {
                straights1Iter.remove();
            }
        } 
        
        // Loại các phần tử thừa của dây ( vd: dây 1,3,4,5 sẽ bỏ 1 )
        straights1Iter = straights1.listIterator();
        while(straights1Iter.hasNext())
        {
            ArrayList<Card> straight = straights1Iter.next();
            Iterator<Card> straightIter = straight.listIterator();
            while(straightIter.hasNext())
            {
                Card cStraight = straightIter.next();
                if(indexOfThisCardInThisHand(straight, cStraight)==0 && cStraight.getValue()+1 != straight.get(1).getValue())
                {
                    straightIter.remove();
                    break;
                }
                if(indexOfThisCardInThisHand(straight, cStraight)==straight.size()-1 && cStraight.getValue()-1 != straight.get(straight.size()-2).getValue())
                {                    
                    straightIter.remove();
                    break;
                }
            }
        }
        
        //*****************************************************************************************************************************
        //*****************************Xếp phỏm cách 2 : Các cây A nếu được dùng trong dây đều đứng sau K******************************
        //*****************************************************************************************************************************
        
        //Đếm và lưu các bộ 3, tứ quý
        ArrayList<ArrayList<Card>> sets2 = new ArrayList<>();
        sortHandByValueThenSuit(hand);
        currentValue = hand.get(0).getValue();
        currentSetLength=1;        
        for(int i=1;i<hand.size();i++)
        {
            if(hand.get(i).getValue()==currentValue)
            {
                currentSetLength++;
            }
            else
            {
                if(currentSetLength>=3)
                {
                    ArrayList<Card> set = new ArrayList<>();
                    for(int j=i-currentSetLength;j<=i-1;j++)
                    {
                        set.add(hand.get(j));
                    }
                    sets2.add(set);
                }
                currentSetLength=1;
                currentValue=hand.get(i).getValue();
                continue;
            }
            if(i==hand.size()-1)
            {
                if(currentSetLength>=3)
                {
                    ArrayList<Card> set = new ArrayList<>();
                    for(int j=i+1-currentSetLength;j<=i;j++)
                    {
                        set.add(hand.get(j));
                    }
                    sets2.add(set);
                }
            }
        }        
        
        //Đếm và lưu các dây
        ArrayList<ArrayList<Card>> straights2 = new ArrayList<>(); 
        boolean aceAdded = false;
        sortHandBySuitThenValue(hand);
        currentValue = hand.get(0).getValue();
        currentSuit = hand.get(0).getSuit();
        currentStraightLength=1;
        for(int i=1;i<hand.size();i++)
        {
            if(hand.get(i).getSuit()!=currentSuit || hand.get(i).getValue()!=currentValue+1)
            {
                if(currentStraightLength>=3)
                {
                    ArrayList<Card> straight = new ArrayList<>();
                    for(int j=i-currentStraightLength;j<=i-1;j++)
                    {
                        straight.add(hand.get(j));
                    }
                    if(aceAdded)
                    {
                        straight.add(new Card(1,currentSuit));
                        aceAdded = false;
                    }
                    straights2.add(straight);
                }
                currentValue = hand.get(i).getValue();
                currentSuit = hand.get(i).getSuit();
                currentStraightLength = 1;
            }
            if(hand.get(i).getValue()==currentValue+1 && hand.get(i).getSuit() == currentSuit)
            {
                if(currentValue==1)
                {
                    if(currentStraightLength>=3)
                    {
                        ArrayList<Card> straight = new ArrayList<>();
                        for(int j=i-currentStraightLength;j<=i-1;j++)
                        {
                            straight.add(hand.get(j));
                        }
                        if(aceAdded)
                        {
                            straight.add(new Card(1,currentSuit));
                            aceAdded = false;
                        }
                        straights2.add(straight);
                    }
                    currentValue = hand.get(i).getValue();
                    currentSuit = hand.get(i).getSuit();
                    currentStraightLength = 1;
                }
                else
                {
                    currentValue = hand.get(i).getValue();
                    currentStraightLength++;
                }
            }
            if(hand.get(i).getValue()==13 && thisHandContainsThisCard(hand, new Card(1,currentSuit)))
            {
                currentStraightLength++;
                aceAdded = true;
            }
            if(i==hand.size()-1)
            {
                if(currentStraightLength>=3)
                {
                    ArrayList<Card> straight = new ArrayList<>();
                    for(int j=i+1-currentStraightLength;j<=i;j++)
                    {
                        straight.add(hand.get(j));
                    }
                    if(aceAdded)
                    {
                        straight.add(new Card(1,currentSuit));
                        aceAdded = false;
                    }
                    straights2.add(straight);
                }
            }
        }
        
        // Tách dây to thành các dây nhỏ ( vd: 1,2,3,4,5,6 -> 1,2,3 và 4,5,6 )
        Iterator<ArrayList<Card>> straights2Iter = straights2.listIterator();
        ArrayList<ArrayList<Card>> straights2ToAdd = new ArrayList<>();
        while(straights2Iter.hasNext())
        {
            ArrayList<Card> straight = straights2Iter.next();
            Iterator<Card> straightIter = straight.listIterator();
            while(straight.size()/3>1)
            {
                ArrayList<Card> tempStraight = new ArrayList<>();
                for(int i=1;i<=3 && straightIter.hasNext();i++)
                {
                    Card cStraight = straightIter.next();
                    tempStraight.add(cStraight);
                    straightIter.remove();
                }
                straights2ToAdd.add(tempStraight);
            }
        }
        straights2.addAll(straights2ToAdd);
        
        //Tìm những lá bài đang được dùng chung cho cả dây và bộ và quyết định lá bài đó sẽ dùng cho dây hay bộ
        numberOfStraightsWillGetIfDeleteThisSet = 0;
        numberOfSetsWillGetIfDeleteThisStraight = 0;
        deleteStraight=false;
        straights2Iter = straights2.listIterator();
        while(straights2Iter.hasNext())
        {
            ArrayList<Card> straight = straights2Iter.next();
            Iterator<Card> straightIter = straight.listIterator();
            while(straightIter.hasNext())
            {
                Card cStraight = straightIter.next();
                Iterator<ArrayList<Card>> sets2Iter = sets2.listIterator();
                while(sets2Iter.hasNext())
                {
                    ArrayList<Card> set = sets2Iter.next();
                    Iterator<Card> setIter = set.listIterator();
                    if(thisHandContainsThisCard(set, cStraight))
                    {
                        if(straight.indexOf(cStraight)%3<straight.size()%3)
                        {
                            straightIter.remove();
                        }
                        else if(set.size()==4)
                        {
                            removeThisCardFromThisHand(set, cStraight);
                        }
                        else if(set.size()==3)
                        {
                            while(setIter.hasNext())
                            {
                                Card cSet = setIter.next();
                                for(ArrayList<Card> straight2 : straights2)
                                {
                                    if(thisHandContainsThisCard(straight2, cSet))
                                    {
                                        numberOfStraightsWillGetIfDeleteThisSet++;
                                        break;
                                    }
                                }
                            }
                            for(Card cStraight2 : straight)
                            {
                                for(ArrayList<Card> set2 : sets2)
                                {
                                    if(thisHandContainsThisCard(set2, cStraight2))
                                    {
                                        numberOfSetsWillGetIfDeleteThisStraight++;
                                    }
                                }
                            }
                            if(numberOfStraightsWillGetIfDeleteThisSet>=numberOfSetsWillGetIfDeleteThisStraight)
                            {
                                sets2Iter.remove();
                            }
                            else
                            {
                                deleteStraight=true;
                                break;
                            }
                        }
                    }
                }
                if(deleteStraight)
                {
                    break;
                }
            }
            if(deleteStraight)
            {
                straights2Iter.remove();
            }
        } 
        
        // Loại các phần tử thừa của dây ( vd: dây 1,3,4,5 sẽ bỏ 1 )
        straights2Iter = straights2.listIterator();
        while(straights2Iter.hasNext())
        {
            ArrayList<Card> straight = straights2Iter.next();
            Iterator<Card> straightIter = straight.listIterator();
            while(straightIter.hasNext())
            {
                Card cStraight = straightIter.next();
                if(indexOfThisCardInThisHand(straight, cStraight)==0 && cStraight.getValue()+1 != straight.get(1).getValue())
                {
                    straightIter.remove();
                    break;
                }
                if(indexOfThisCardInThisHand(straight, cStraight)==straight.size()-1 && cStraight.getValue()-1 != straight.get(straight.size()-2).getValue())
                {
                    if(!(cStraight.getValue()==1 && straight.get(straight.size()-2).getValue()==13))
                    {
                        straightIter.remove();
                        break;
                    }
                }
            }
        }
        
        //Sắp xếp lại hand theo phỏm
        if(straights1.size()+sets1.size()>=straights2.size()+sets2.size())
        {
            for(ArrayList<Card> straight : straights1)
            {
                System.out.println("Bạn có dây : ");
                for(Card c : straight)
                {
                    System.out.print(c.toString());
                    removeThisCardFromThisHand(hand, c);
                }
                System.out.println("");
            }

            for(ArrayList<Card> set : sets1)
            {
                System.out.println("Bạn có bộ : ");
                for(Card c : set)
                {
                    System.out.print(c.toString());
                    removeThisCardFromThisHand(hand, c);
                }
                System.out.println("");
            }

            for(ArrayList<Card> straight : straights1)
            {
                for(Card c : straight)
                {
                    hand.add(c);
                }
            }

            for(ArrayList<Card> set : sets1)
            {
                for(Card c : set)
                {
                    hand.add(c);
                }
            }
        
            return straights1.size()+sets1.size();
        }
        else
        {
            for(ArrayList<Card> straight : straights2)
            {
                System.out.println("Bạn có dây : ");
                for(Card c : straight)
                {
                    System.out.print(c.toString());
                    removeThisCardFromThisHand(hand, c);
                }
                System.out.println("");
            }

            for(ArrayList<Card> set : sets2)
            {
                System.out.println("Bạn có bộ : ");
                for(Card c : set)
                {
                    System.out.print(c.toString());
                    removeThisCardFromThisHand(hand, c);
                }
                System.out.println("");
            }

            for(ArrayList<Card> straight : straights2)
            {
                for(Card c : straight)
                {
                    hand.add(c);
                }
            }

            for(ArrayList<Card> set : sets2)
            {
                for(Card c : set)
                {
                    hand.add(c);
                }
            }
        
            return straights2.size()+sets2.size();
        }
    }
    
    // In ra console hand được truyền vào
    public void printHand(ArrayList<Card> hand)
    {
        for(Card c : hand)
        {
            System.out.print(c.toString());
        }
    }
}
