package cn.snow.interviewapp.kotlin;

import androidx.annotation.NonNull;

public class StudyJava {
    public StudyJava() {
    }

    public static void main(String[] args) {
//        StudyCKt.javaTest("p1",2,true);
//        StudyCKt.javaTest("p1");
//        StudyCKt.javaTest("p1",33);

        //@file:JvmName("TestJvmName") 注解 要改变包含 Kotlin 顶层函数的生成的类的名称，需要为这个文件添加
        //JvmName 的注解，将其放到这个文件的开头，位于包名的前面
        TestJvmName.javaTest("p1", 2, true);
        TestJvmName.javaTest("p1");
        TestJvmName.javaTest("p1", 33);
        TestJvmName.setTestStaticX(2);
        System.out.println(TestJvmName.getTestStaticX());

        TestJvmName.javaFun(new StudyJava()); //需要传入一个扩展类型的对象

    }

    @NonNull
    @Override
    public String toString() {
        return "！StudyJava.toString()！";
    }
}
