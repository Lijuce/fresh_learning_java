package enums;

import java.util.*;

public class EnumTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a size: ");
        String input = in.next().toUpperCase();
        Size size = Enum.valueOf(Size.class, input);
        System.out.println("size=" + size);
        System.out.println("abbre=" + size.getAbbreviation());
        if (size == Size.EXTRA_LARGE)  // 比较枚举类型的值，直接使用“==”符号，无需调用equals
            System.out.println("Good job");
    }
}


enum Size{
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");
    private String abbreviation;

    private Size(String abbreviation) {  // 构造器
        this.abbreviation = abbreviation;
    }
    public String getAbbreviation(){
        return this.abbreviation;
    }

}