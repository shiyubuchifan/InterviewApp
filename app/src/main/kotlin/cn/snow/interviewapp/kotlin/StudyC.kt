@file:JvmName("TestJvmName")

package cn.snow.interviewapp.kotlin

import java.util.TreeMap

class StudyC {
    fun javaTest(params1: String = "String", params2: Int = -1, params3: Boolean = false): Int {

        println("$params1 $params2 $params3")
        return 1
    }
}

class People(val name: String, var isOld: Boolean) {
    val isOldString: String
        get() = if (isOld) "${name}老了" else "${name}还年轻"
}

enum class COLOR(val R: Int, val G: Int, val B: Int) {
    RED(255, 0, 0), BLUE(0, 0, 255), YELLOW(255, 255, 0), PINK(
        255, 192, 203
    ); //这个分号”；“ 是kotlin 中唯一的分号使用 就是枚举类型中的最后一个常量后面

    fun RGB() = ("$R $G $B")

}

interface Love
class TureLove(val name: String) : Love
class FalseLove(val name: String) : Love

fun getColor(color: COLOR) = when (color) {
    COLOR.RED -> "红色"
    COLOR.PINK -> "粉色"
    COLOR.BLUE, COLOR.YELLOW -> "蓝黄色" //两个不同的条件执行同一个语句 用逗号”，“ 隔开就行
}

fun isEqual(color1: COLOR, color2: COLOR) =
    when (setOf(color1, color2)) { //when的表达式实参可以是任何对象 这里是创建了一个Set集合
        setOf(COLOR.RED, COLOR.PINK) -> "说明集合里有红粉两个颜色 谁在前后不重要"
        else -> "没有匹配颜色集合"
    }


fun main(args: Array<String>) {
    println("hello world")
    println(max(5, 6))
    println(max1(5, 6))

    //val (value)  不可变引用  对应的是java 中的final
    val a = 1
    val aa: Int = 11 //显式
    val b: Int
    b = 3
    val c = 0.1e6 //Double
    val list = arrayListOf<String>("Java") //val 引用不变 但是它指向的对象可能是可变的
    list.add("Kotlin")
    list.removeAt(0)


    //var (variable) 可变引用
    var d = 4
    d = 5

    //$ 字符串模板 {}表达式
    println("Hello ,$a!") //$a 变量名 编译后的代码创建了一个StringBuilder对象
    println("Hello ,${list[0]}!") //${} 表达式
    println("Hello ,${if (a > b) "A" else "B"}!")


    val people = People("Bob", false)
    println("${people.name} \t ${people.isOld} \t ${people.isOldString}")
    people.isOld = true
    println("${people.name} \t ${people.isOld} \t ${people.isOldString}")

    println(getColor(COLOR.RED) + COLOR.RED.RGB())
    println(getColor(COLOR.PINK))
    println(getColor(COLOR.BLUE))
    println(getColor(COLOR.YELLOW))
    println(isEqual(COLOR.PINK, COLOR.RED))
    println(isEqual(COLOR.PINK, COLOR.BLUE))

    val love1: Love
    val love2: Love
    love1 = TureLove("真爱")
    love2 = FalseLove("假爱")

    println(loveName(love1))
    loveName(love2)

    //until是半闭合区间
    for (snow in 0 until 100) {

    }

    //..是闭合区间 ‘A’..'Z' 还可以创建字符区间
    for (snow in 0..100 - 1) {

    }

    //遍历map
    val naryReps = TreeMap<Char, String>()
    for (x in 'A'..'F') {
        val binary = Integer.toBinaryString(x.code.toInt())
        naryReps[x] = binary
    }

    for ((letter, binary) in naryReps) {
        println("$letter = $binary")
    }

    val listTest = listOf<String>("1", "snow", "rain")
    println(listTest)//调用toString[1, snow, rain]
    println(listTest.joinToString("、", "(", ")"))//修改toString // 分割字符，前置字符和后置字符
    println(
        listTest.joinToString(
            separator = "：", prefix = "{", postfix = "}"
        )
    )//显式的标明一些参数名称 加强程序的可读性 但是如果在调用函数时，指明了一个参数的名称，为了避免混淆，那它之后的所有参数都要标明名称


    //集合创建
    val map = hashMapOf(1 to "1", 2 to "2") //to 是中缀调用 等价 1.to("1")
    val set = setOf(1, 2, 3)
    val hashset = hashSetOf(1, 3, 2)
    hashset.max() //扩展函数


    javaTest()

    println("Java".lastChar())

    //可变参数传参
    val listarg = arrayOf("1", "2", "3")
    varargFun("1", "2", "3")
    varargFun(*listarg) //不能直接使用listarg 要进行解包处理 加星号*

    //解构声明 调用中缀函数xo 返回一个Pair
    val (key,value) = 1 xo "one"
    println("key=$key value=$value")

    stringEmpty("","1","snow","","rain")

}

var testStaticX = -1
val const1 = 1     //private
const val const2 = 2 //public

fun loveName(name: Love) = when (name) {
    is TureLove -> {
        println(name.name)
        "执行了真爱when" //when “代码块中最后的表达式就是结果” 还有try catch 中一样的  但是对其他常规函数不适用（一个函数要么具有不是代码块的表达式函数体，要么具有包含显式 return 语句的代码块函数体。）
    }

    is FalseLove -> {
        println(name.name)
    }

    else -> throw IllegalArgumentException("Unknown expression")
}

fun max1(a: Int, b: Int): Int = if (a > b) a else b

fun max(a: Int, b: Int) = if (a > b) a else b //省略了返回类型 类型推导 编译器分析函数体的表达式类型 并作为返回值类型

val a = 1


/**
 * 默认参数函数
 * 考虑到 Java 没有参数默认值的概念，当你从 Java 中调用 Kotlin 函数的时候，
必须显式地指定所有参数值 如果需要从 Java 代码中做频繁的调用，而且希
它能对 Java 的调用者更简便，可以用＠ JvmOverloads 注解它 。这个指示编译
器 生成 Java 重载函数 ，从最后一个开始省略每个参数。
 */
@JvmOverloads
fun javaTest(params1: String = "String", params2: Int = -1, params3: Boolean = false): Int {

    println("$params1 $params2 $params3")
    val studyJava = StudyJava()
    studyJava.javaFun()
    return 1
}

fun String.lastChar(): Char = this.get(this.length - 1)


/**
 * 扩展函数
 * StudyJava 是要扩展的类
 * this 扩展类的对象StudyJava（） 并且这个this 可以省略
 * 如果子类和父类都扩展了同一个函数 和重写父类函数不一样的是
 * 调用的时候是看引用是什么静态类型决定调用哪个函数的 并不是看运行时的类型
 * 而重写函数调用的时候看的是运行时的类型
 * 比如 View 和 TextView 都扩展了addFun（）函数
 * View v = new TextView()
 * v.addFun（）
 * //这里会调用的是View的扩展函数//
 *
 */
fun StudyJava.javaFun() = println(this.toString())

//扩展属性
val StudyJava.addValX: String
    get() = toString()

//扩展可变属性
var StudyJava.addVarX: Int
    get() = 1
    set(value) {
        toString()
    }

/**
 * vararg 关键字声明一个可变参数 类似于java中的"..." String...
 * 区别是java中调用有vararg可变参数的函数可以直接传入数组或者以分开的形式传入参数
 * 而kotlin中只能以分开的形式传入参数，如果有已经包装好的数组需要将数组用星号（*）解包（展开运算符）传入
 */
fun varargFun(vararg stringList: String) {
    println(stringList.toString())
    val newlist = listOf("新list的第一个元素", *stringList) // listof可变参数 "*"将包装好的数组解包
    val newlist1 = listOf("新list1的第一个元素", newlist) // listof可变参数 没有解包整个数组变为第二个元素
    println(newlist.toString() + newlist.size)
    println(newlist1.toString() + newlist1.size)


}

/**
 * 中缀调用函数前加入infix
 * 中缀函数可以和只有一个参数的函数一起使用
 */
infix fun Any.xo (other : Any) = Pair(this,other)

/**
 * 局部函数
 */
fun stringEmpty(vararg str :String){
    fun  isEmpty(s:String){
        if(s.isEmpty())
            print("空")
        else
            print(s)
    }

    for (s in str){
        isEmpty(s)
    }
    println()
}
