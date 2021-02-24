package com.company.structuresalgorithms;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Sort {
    public static void main(String[] args) {
        int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48,11,99,789,0,7897,888,666,41,45,87,64,31,85,52,65,74,12,8,9,33,44,55,66,77,88};


        System.out.println("--------------基数排序-------------");
        long baseStart = System.currentTimeMillis();
        radixSort(array);
        System.out.println(Arrays.toString(array));
        System.out.println("基数排序耗时： " +(System.currentTimeMillis() - baseStart) + "ms");

        System.out.println("--------------冒泡排序-------------");
        long bubbleStart = System.currentTimeMillis();
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
        System.out.println("基数排序耗时： " +(System.currentTimeMillis() - bubbleStart) + "ms");
    }
    /**
     * Description: 基数排序
     *
     */
    public static void radixSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;

        // 每位数字范围0~9，基为10
        int radix = 10;
        int[] aux = new int[length];
        int[] count = new int[radix + 1];
        // 以关键字来排序的轮数，由位数最多的数字决定，其余位数少的数字在比较高位时，自动用0进行比较
        // 将数字转换成字符串，字符串的长度就是数字的位数，字符串最长的那个数字也拥有最多的位数
        int x = Arrays.stream(array).map(s -> String.valueOf(s).length()).max().getAsInt();

        // 共需要d轮计数排序, 从d = 0开始，说明是从个位开始比较，符合从右到左的顺序
        for (int d = 0; d < x; d++) {
            // 1. 计算频率，在需要的数组长度上额外加1
            for (int i = 0; i < length; i++) {
                // 使用加1后的索引，有重复的该位置就自增
                count[digitAt(array[i], d) + 1]++;
            }
            // 2. 频率 -> 元素的开始索引
            for (int i = 0; i < radix; i++) {
                count[i + 1] += count[i];
            }

            // 3. 元素按照开始索引分类，用到一个和待排数组一样大临时数组存放数据
            for (int i = 0; i < length; i++) {
                // 填充一个数据后，自增，以便相同的数据可以填到下一个空位
                aux[count[digitAt(array[i], d)]++] = array[i];
            }
            // 4. 数据回写
            for (int i = 0; i < length; i++) {
                array[i] = aux[i];
            }
            // 重置count[]，以便下一轮统计使用
            for (int i = 0; i < count.length; i++) {
                count[i] = 0;
            }

        }
    }

    /**
     * 冒泡排序
     */
    public static void bubbleSort(int[] array){
        if (array == null || array.length <= 1){
            return;
        }

        // 外层循环控制比较轮数i
        for (int i = 0; i < array.length; i++) {
            // 内层循环控制每一轮比较次数，每进行一轮排序都会找出一个较大值
      		// (array.length - 1)防止索引越界，(array.length - 1 - i)减少比较次数
            for (int i1 = 0; i1 < array.length -1 -i; i1++) {
                // 前面的数大于后面的数就进行交换
                if (array[i1] > array[i1 + 1]){
                    int temp = array[i1 + 1];
                    array[i1 + 1] = array[i1];
                    array[i1] = temp;
                }

            }
        }
    }


    /**
     * Description: 根据d，获取某个值的个位、十位、百位等，d = 0取出个位，d = 1取出十位，以此类推。对于不存在的高位，用0补
     *
     */
    private static int digitAt(int value, int d) {
        return (value / (int) Math.pow(10, d)) % 10;
    }

}
