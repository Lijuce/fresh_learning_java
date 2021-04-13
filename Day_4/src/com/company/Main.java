package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] nums = new int[]{22,33,49,47,33,12,68,29};
        quickSort(nums, 0, nums.length - 1);
        for (int i: nums)
            System.out.println(i);
    }

    public static void quickSort(int[] li, int left, int right){
        if (left >= right)
            return;
        int r = right, l = left;
        int base = li[left];

        while (left < right){
            while (left < right && li[right] >= base)  // 先从右向左找起
                right -= 1;
            if (left < right){
                li[left] = li[right];
                left += 1;
            }
            while (left < right && li[left] <= base)
                left ++;
            if (left < right){
                li[right] = li[left];
                right --;
            }

        }
        li[left] = base;
        quickSort(li, l, left - 1);
        quickSort(li, left + 1, r);
    }


}
