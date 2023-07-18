package cn.snow.interviewapp.algorithm;

public class Sort {
    public static void main(String[] args) {

        int[] arr = new int[]{50, 7, 37, 7, 37, 14, 47, 49, 17, 29, 45, 25, 27, 33};
//        arr = sortMaoPao(arr);
//        arr = sortXuanze(arr);
        arr = sortCharu(arr);
        for (int i : arr) {
            System.out.print(i);
            if (i != arr.length - 1) System.out.print(",");
        }
    }

    public static int[] sortMaoPao(int[] array) {
        long beginTime = System.nanoTime();
        if (array == null || array.length == 0) return null;

        int temp;
        boolean flag = true;
        for (int i = 0; i < array.length && flag; i++) {
            flag = false;
            for (int j = 1; j < array.length - i; j++) {
                if (array[j] < array[j - 1]) {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                    flag = true;
                }
            }
        }

        System.out.print("time:" + (System.nanoTime() - beginTime) + " ");

        return array;
    }

    /**
     * 选择排序
     * <p>
     * 重复（元素个数-1）次
     * <p>
     * 把第一个没有排序过的元素设置为最小值
     * <p>
     * 遍历每个没有排序过的元素
     * <p>
     * 如果元素 < 现在的最小值
     * <p>
     * 将此元素设置成为新的最小值
     * <p>
     * 将最小值和第一个没有排序过的位置交换
     *
     * @param array
     * @return
     */
    public static int[] sortXuanze(int[] array) {
        long beginTime = System.nanoTime();
        int temp, index;

        for (int i = 0; i < array.length; i++) {
            index = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[index]) {
                    index = j;
                }
            }
            temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }

        System.out.print("time:" + (System.nanoTime() - beginTime) + " ");

        return array;
    }

    /**
     * 插入排序
     * 将第一个元素标记为已排序
     * <p>
     * 对于每一个未排序的元素 X
     * <p>
     * “提取” 元素 X
     * <p>
     * i = 最后排序过元素的索引 到 0 的遍历
     * <p>
     * 如果当前元素 j > X
     * <p>
     * 将排序过的元素向右移一格
     * <p>
     * 跳出循环并在此插入 X
     *
     * @param array
     * @return
     */
    public static int[] sortCharu(int[] array) {
        long beginTime = System.nanoTime();

        int temp;

        for (int i = 1; i < array.length; i++) {

            temp = array[i];
            int j;
            for (j = i; j > 0; j--) {
                if (array[j - 1] > temp) {
                    array[j] = array[j - 1];
                } else break;

            }
            array[j] = temp;
        }
        System.out.print("time sortCharu:" + (System.nanoTime() - beginTime) + " ");

        return array;
    }


}
