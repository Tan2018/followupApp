package com.ry.fu.followup.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.*;
import java.util.Random;

/**
 * Created by jackson on 2018/4/12.
 */
public class PdfTest {
    public static void main(String[] args) {
        int len = 10;
        int[] ary = new int[len];
        Random random = new Random();
        for (int j = 0; j < len; j++) {
            ary[j] = random.nextInt(1000);
        }
        binaryInsert(ary);
        /*
         * 复杂度分析： 最佳情况，即都已经排好序，则无需右移，此时时间复杂度为：O(n lg n) 最差情况，全部逆序，此时复杂度为O(n^2)
         *  无法将最差情况的复杂度提升到O(n|logn)。
         */
        // 打印数组
        printArray(ary);
    }
    /**
     * 插入排序
     * @param ary
     */
    private static void binaryInsert(int[] ary) {
        int setValueCount = 0;
        // 从数组第二个元素开始排序，因为第一个元素本身肯定是已经排好序的
        for (int j = 1; j < ary.length; j++) {// 复杂度 n
            // 保存当前值
            int key = ary[j];
            // ∆ 利用二分查找定位插入位置
//          int index = binarySearchAsc(ary, ary[j], 0, j - 1);// 复杂度：O(logn)
//          int index = binarySearchDesc(ary, ary[j], 0, j - 1);// 复杂度：O(logn)
            int index = binarySearchDesc2(ary, ary[j], 0, j - 1);// 复杂度：O(logn)
            printArray(ary);
            System.out.println("第" + j +"个索引上的元素要插入的位置是：" + index);
            // 将目标插入位置，同时右移目标位置右边的元素
            for (int i = j; i > index; i--) {// 复杂度,最差情况：(n-1)+(n-2)+...+n/2=O(n^2)
                ary[i] = ary[i - 1]; //i-1 <==> index
                setValueCount++;
            }
            ary[index] = key;
            setValueCount++;
        }
        System.out.println("\n 设值次数(setValueCount)=====> " + setValueCount);
    }

    /**
     * 二分查找 升序 递归
     *
     * @param ary
     *            给定已排序的待查数组
     * @param target
     *            查找目标
     * @param from
     *            当前查找的范围起点
     * @param to
     *            当前查找的返回终点
     * @return 返回目标在数组中，按顺序应在的位置
     */
    private static int binarySearchAsc(int[] ary, int target, int from, int to) {
        int range = to - from;
        // 如果范围大于0，即存在两个以上的元素，则继续拆分
        if (range > 0) {
            // 选定中间位
            int mid = (to + from) / 2;
            // 如果临界位不满足，则继续二分查找
            if (ary[mid] > target) {
                /*
                 * mid > target, 升序规则，target较小，应交换位置 前置， 即target定位在mid位置上，
                 * 根据 查找思想， 从from到 mid-1认为有序， 所以to=mid-1
                 */
                return binarySearchAsc(ary, target, from, mid - 1);
            } else {
                /*
                 * mid < target, 升序规则，target较大，不交换位置，查找比较的起始位置应为mid+1
                 */
                return binarySearchAsc(ary, target, mid + 1, to);
            }
        } else {
            if (ary[from] > target) {//如 5,4, 要插入的是4
                return from;
            } else {
                return from + 1;
            }
        }
    }
    /**
     * 二分查找 降序， 递归
     */
    private static int binarySearchDesc(int[] ary, int target, int from, int to) {
        int range = to - from;
        if (range > 0) {
            int mid = (from + to) >>> 1;
            if (ary[mid] > target) {
                return binarySearchDesc(ary, target, mid + 1, to);
            } else {
                return binarySearchDesc(ary, target, from, mid - 1);
            }
        } else {
            if (ary[from] > target) {//如 5,4, 要插入的是4
                return from + 1;
            } else {
                return from;
            }
        }
    }

    /**
     * 二分查找 降序， 非递归
     */
    private static int binarySearchDesc2(int[] ary, int target, int from, int to) {
//      while(from < to) {
        for (; from < to; ) {
            int mid = (from + to) >>> 1;
            if (ary[mid] > target) {
                from = mid + 1;
            } else {
                to  = mid -1;
            }
        }
        //from <==> to;
        if (ary[from] > target) {//如 5,4, 要插入的是4
            return from + 1;
        } else {
            return from;
        }
    }

    private static void printArray(int[] ary) {
        for (int i : ary) {
            System.out.print(i + " ");
        }
    }
}
