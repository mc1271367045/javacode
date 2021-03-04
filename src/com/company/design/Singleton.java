package com.company.design;

/**
 * 单例模式
 */
public class Singleton {
    public static void main(String[] args) {


    }
    //饿汉式①：
    static class Singleton1 {
        //构造器私有化
        private Singleton1(){
        }
        public static final Singleton1 INSTANCE = new Singleton1();
    }

    //饿汉式②：枚举类型：表示该类型的对象是有限的几个
    enum Singleton2{
        INSTANCE
    }

    //饿汉式③：静态代码块(使用场景：从配置文件中获取参数，较复杂的处理)
    static class Singleton3 {
        //构造器私有化
        private Singleton3(){
        }
        public static final Singleton3 INSTANCE;
        static {
            INSTANCE = new Singleton3();
        }

    }

    //懒汉式① 适用于单线程（存在线程安全问题，可用加同步锁解决线程安全问题）
    static class Singleton4 {
        private static Singleton4 instance;
        //构造器私有化
        private Singleton4(){
        }
        public static Singleton4 getInstance(){
            if(instance ==null){
                instance = new Singleton4();
            }
            return instance;
        }
    }

    //懒汉式② 适用于多线程（线程安全）
    static class Singleton5 {
        private static Singleton5 instance;
        //构造器私有化
        private Singleton5(){
        }

        public static Singleton5 getInstance(){
            //性能优化，提升效率
            if(instance ==null){
                //上锁
                synchronized(Singleton5.class){
                    if(instance ==null){
                        instance = new Singleton5();
                    }
                }
            }
            return instance;
        }
    }

    //懒汉式③ 静态内部类形式 适用于多线程（线程安全）
    //静态内部类不会自动随着外部类的加载和初始化而初始化，他是要单独去加载和初始化的
    //因为是在内部类加载和初始化时创建的，所以线程是安全的
    static class Singleton6 {
        //构造器私有化
        private Singleton6(){
        }

        private static class Inner{
            private static final Singleton6 INSTANCE = new Singleton6();
        }

        public static Singleton6 getInstance(){
            return Inner.INSTANCE;
        }
    }

}
