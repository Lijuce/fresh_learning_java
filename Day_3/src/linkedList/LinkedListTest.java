package linkedList;

import java.util.ListIterator;
import java.util.*;

public class LinkedListTest {
    public static void main(String[] args) {
        List<String>a = new  LinkedList<String>();
        a.add("AMy");
        a.add("Carl");
        a.add("Erical");

        List<String>b = new LinkedList<String>();
        b.add("Bob");
        b.add("Doug");
        b.add("Globra");


        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();

        while (bIter.hasNext()){
            if (aIter.hasNext())  // 要访问下一个元素，必须先调用hasNext()确保下一个元素的存在
                aIter.next();
            aIter.add(bIter.next());
        }
        System.out.println(a);

        bIter = b.iterator();
        while (bIter.hasNext()){
            bIter.next();
            if (bIter.hasNext()){
                bIter.next();
                bIter.remove();
            }
        }
        System.out.println(b);

        a.removeAll(b);  // 删除a中所有b包含的元素
        System.out.println(a);
    }

}
