package com.company;

public class Main {



    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BitGraph bg = new BitGraph();
        int[] datas = new int[]{1,13,14,15,7,8,9,13,1,13,14,15,7,8,9,13,2};
        int[] arr = new int[bg.max / 32 + 1];
        System.out.println(arr.length);
        for(int data : datas)
        {
            bg.setBit(arr, data);
        }
        int count = 0;
        for(int i = 0; i < bg.max;i++)
        {
            if(bg.getBit(arr, i) == 1)
            {
                System.out.println(i);
                ++count;
            }
        }
        System.out.println("count" +count);
    }

    public static int numberOf1(int n){
        int count = 0;
        while (n>0){
            count ++;
            n = (n-1) & n;
        }
        return count;
    }
}

