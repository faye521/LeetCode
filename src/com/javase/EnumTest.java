package com.javase;

/**
 * @author faye
 * @className EnumTest
 * @Description TODO
 * @Date 2022/8/8 18:07
 * @Version 1.0
 */
public class EnumTest {
    public static void main(String[] args) {
        test(SeasonEnum.Spring);
        //
        for(SeasonEnum s:SeasonEnum.values()){
            System.out.print(s+" ");
        }
        //默认有一个values方法可以获取所有实例
        SeasonEnum.values()[0].param();
        //获取索引
        System.out.println(SeasonEnum.Winter.ordinal());
    }
    public static void test(SeasonEnum s){
        switch (s){
            case Autumn:
                System.out.println("秋天到了");
                break;
            case Spring:
                System.out.println("春天到了");
                break;
            case Summer:
                System.out.println("夏天到了");
                break;
            case Winter:
                System.out.println("冬天到了");
                break;
            default:
                System.out.println("别瞎写");
                break;
        }
    }
}


/**
 * Java5.0之后新特性，与class interface用法相同
 * 如季节 红绿灯，实体类种类有限的情况下可以使用枚举类增强程序的健壮性，避免创建对象的随意性
 * 1.Enum默认继承java.lang.Enum而不是Object 不能使用Object里的方法
 * 2.使用enum定义的非抽象枚举类默认被final修饰，也就说所有的非抽象enum都不能被继承
 * 3.枚举类的构造器只能使用private访问控制符
 * 4.枚举类的实例必须在第一行列出，否则这个枚举类就是0实例。实例默认给上public static final修饰符 逗号分割，分号结束
 */
enum SeasonEnum{
    Spring,Summer,Autumn,Winter;
    public void param(){
        System.out.println("xxvvxxvv");
    }
}
