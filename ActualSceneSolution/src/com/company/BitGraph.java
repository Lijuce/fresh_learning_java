package com.company;

import org.junit.Test;

import java.util.BitSet;  // java自带位图法

/**
 * 2021年4月21日
 * 位图法存储海量数据
 * 实践场景：判断海量数据中是否重复存在某数据
 */
public class BitGraph {
    final int BITS_PRE_WORD = 32;
    final static int max = 16;
    void setBit(int[] arr, int n)
    {
        arr[n/BITS_PRE_WORD] |= (1 << (n % BITS_PRE_WORD));
    }
    int getBit(int[] arr, int n)
    {
        return (arr[n/BITS_PRE_WORD] & (1 << (n%BITS_PRE_WORD))) != 0 ? 1 : 0;
    }

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

    @Test
    public void test(){
        /**
         * 杨辉三角
         */
        int[][] arr = new int[10][10];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i){
                    arr[i][j] = 1;
                }else {
                    arr[i][j] = arr[i-1][j-1] + arr[i-1][j];
                }
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
