package com.company.leetcode;

import java.util.HashMap;
import java.util.Map;

public class ArrayQuestion {
    public static void main(String[] args) {


        // 数组问题1
        System.out.println("--------------数组问题-最长（连续）子数组的长度-------------");
        int[] A = {1,1,1,0,0,0,1,1,1,1,0};
        int K = 2;
        System.out.println(longestOnes(A, 2));

        // 数组问题2
        System.out.println("--------------数组问题-数组的度-------------");
        int[] B = {1,4,1,0,3,0,1,2,2,2,0};
        System.out.println(findShortestSubArray(B));

        // 数组问题3
        System.out.println("--------------数组问题-托普利茨矩阵-------------");
        int[][] C = {{1,2,3,4},{5,1,2,3},{9,5,1,2}};
        System.out.println(isToeplitzMatrix(C));

        // 数组问题4
        System.out.println("--------------数组问题-反转图像-------------");
        int[][] D = {{1,1,0},{1,0,1},{0,0,0}};
        int[][] arr = flipAndInvertImage(D);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (j != 0)
                    System.out.print(" ");
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }

        // 数组问题5
        System.out.println("--------------数组问题-最长公共前缀-------------");
        String[] E = {"flower","flow","flight"};
        System.out.println(longestCommonPrefix(E));


    }

    /**
     * 数组问题1
     * @param A
     * @param K
     * @return
     */
    public static int longestOnes(int[] A, int K) {
    //    给定一个由若干 0 和 1 组成的数组 A，我们最多可以将 K 个值从 0 变成 1 。
    //    返回仅包含 1 的最长（连续）子数组的长度。
    //题意转换: 把「最多可以把 K 个 0 变成 1，求仅包含 1 的最长子数组的长度」转换为 「找出一个最长的子数组，该子数组内最多允许有 K 个 0 」
    /* 思路：使用 left 和 right 两个指针，分别指向滑动窗口的左右边界。
            right 主动右移：right 指针每次移动一步。当 A[right] 为 0，说明滑动窗口内增加了一个 0；
            left 被动右移：判断此时窗口内 0 的个数，如果超过了 K，则 left 指针被迫右移，直至窗口内的 0 的个数小于等于 KK 为止。
            滑动窗口长度的最大值就是所求。
     */

        // 数组长度
        int N = A.length;
        int res = 0;
        int left = 0, right = 0;
        // 零的个数
        int zeros = 0;
        // 控制循环范围
        while (right < N) {
            if (A[right] == 0)
                zeros ++;
            while (zeros > K) {
                if (A[left++] == 0)
                    zeros --;
            }
            res = Math.max(res, right - left + 1);
            right ++;
        }
        return res;

    }

    /**
     * 数组问题2
     * @param nums
     * @return
     */
    public static int findShortestSubArray(int[] nums) {

        /*给定一个非空且只包含非负数的整数数组 nums，数组的度的定义是指数组里任一元素出现频数的最大值。
        你的任务是在 nums 中找到与 nums 拥有相同大小的度的最短连续子数组，返回其长度。
        */

        //记录每个数字的度
        Map<Integer, Integer> degreeMap = new HashMap<>();
        //记录每个数字在数组中第一次出现的位置
        Map<Integer, Integer> first = new HashMap<>();
        int res = 0, maxDegree = 0;
        for (int i = 0; i < nums.length; ++i) {
            //如果A[i]在数组中第一次出现，要记录下他的位置
            if (!first.containsKey(nums[i]))
                first.put(nums[i], i);
            //目前数字的度（数组还没有遍历完，这个度并不是最终的）
            int degree = degreeMap.getOrDefault(nums[i], 0) + 1;
            degreeMap.put(nums[i], degree);
            //如果小于最大的度，直接跳过
            if (degree < maxDegree)
                continue;
            //求出距离
            int distance = i - first.get(nums[i]) + 1;
            //如果目前的度最大，直接更新距离
            if (degree > maxDegree) {
                maxDegree = degree;
                res = distance;
            } else if (degree == maxDegree) {
                //和之前出现的最大的度一样，只需要取距离比较小的即可。
                res = Math.min(res, distance);
            }
        }
        return res;


    }

    /**
     * 数组问题3
     * @param arr
     * @return
     */
    public static boolean isToeplitzMatrix(int[][] arr) {
        /*
        给你一个 m x n 的矩阵 matrix 。如果这个矩阵是托普利茨矩阵，返回 true ；否则，返回 false 。
        如果矩阵上每一条由左上到右下的对角线上的元素都相同，那么这个矩阵是 托普利茨矩阵 。
        */

        //思路： 每一个位置都要跟其右下角的元素相等。
        for (int i = 0; i < arr.length - 1; ++i) {
            for (int j = 0; j < arr[0].length - 1; ++j) {
                if (arr[i][j] != arr[i + 1][j + 1])
                    return false;
            }
        }
        return true;
    }

    /**
     * 数组问题4
     * @param A
     * @return
     */
    public static int[][] flipAndInvertImage(int[][] A) {
        int row = A.length;
        int col = A[0].length;
        for (int r = 0; r < row; r++) { //遍历矩阵行

            // 第一步：水平翻转
            for (int c = 0; c < col / 2; c++) {
                int temp = A[r][c];
                A[r][c] = A[r][col - c - 1];
                A[r][col - c - 1] = temp;
            }
            // 第二步：0替换成1 1替换成0 ==> 异或逻辑
            for (int i = 0; i < col; i++) {
                A[r][i] = A[r][i] ^ 1;
            }
        }
        return A;
    }


    /**
     * 数组问题5
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        //判空
        if (strs.length == 0) {
            return "";
        }
        String str = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(str) != 0) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;



    }

}
