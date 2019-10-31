package com.hd.test.atomic.integer;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AtomicInteger
 * 成员变量 : private volatile int value
 * 函数演示  等 ， 演示类
 */
public class TestMethod {

    public static void main(String[] args) {
        // 无参构造函数
        AtomicBoolean integer = new AtomicBoolean();

        System.out.println("选择排序");
        int[] ints = {5,1, 3, 5, 7, 11, 12, 33, 22, 2};
        for (int i = 0; i < ints.length -1; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                if (ints[i] > ints[j]) {
                    int in = ints[j];
                    ints[j] = ints[i];
                    ints[i] = in;
                }
            }
            System.out.println(Arrays.toString(ints));
        }
        System.out.println("冒泡排序");
        int[] numbers = {1, 3, 5, 7, 11, 12, 33, 22, 2};
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
            System.out.println(Arrays.toString(numbers));
        }

        int[] arr = new int[] { 5, 3, 6, 2, 10, 2, 1 };
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i; // 用来记录最小值的索引位置，默认值为i
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j; // 遍历 i+1~length 的值，找到其中最小值的位置
                }
            }
            // 交换当前索引 i 和最小值索引 minIndex 两处的值
            if (i != minIndex) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
            // 执行完一次循环，当前索引 i 处的值为最小值，直到循环结束即可完成排序
            System.out.println(Arrays.toString(arr));
        }
    }
}
