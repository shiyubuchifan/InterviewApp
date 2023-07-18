package cn.snow.interviewapp.kotlin.kotlin

import cn.snow.interviewapp.kotlin.StudyJava
//import cn.snow.interviewapp.kotlin.javaFun
import cn.snow.interviewapp.kotlin.javaFun as java //使用as 进行函数重命名 解决方法名冲突问题

class KotlinTest {

}

fun main(args: Array<String>) {
    val studyJava = StudyJava()
    studyJava.java()

}

fun javaFun(){

}