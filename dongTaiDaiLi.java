//addAddAdd
//动态代理就是，在程序运行期，创建目标对象的代理对象，并对目标对象中的方法进行功能性增强的一种技术。
//可以理解为运行期间，对象中方法的动态拦截，在拦截方法的前后执行功能操作。
//代理类在程序运行期间，创建的代理对象称之为动态代理对象
//有了动态代理的技术，那么就可以在不修改方法源码的情况下，增强被代理对象的方法的功能，在方法执行前后做任何你想做的事情。
import java.lang.reflect.*;
/*jdk动态代理*/
interface Calculate {


}
class CalculatorImpl implements Calculate {

}
interface Test2 {
    public String pri();
    // public String toString();
}

public class dongTaiDaiLi implements Calculate {
    public static void main(String[] arg) throws ClassCastException, Throwable, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //一旦我们明确接口，完全可以通过接口的Class对象，
// 创建一个代理Class，通过代理Class即可创建代理对象。
        //jingTai,dongTaiDaiLi即接口实现类
        Calculate calculate = new dongTaiDaiLi();
        //create shili
        //反射——创建对象实例
        Class clazz = dongTaiDaiLi.class;
        //调用无参构造器
        //Class.newInstance: 弱类型。低效率。 只能调用无参public构造。
        //new: 强类型。相对高效。能调用任何public构造。
        //Constructor.newInstance 可以根据传入的参数，调用 任意构造构造函数。
        dongTaiDaiLi daiLi = (dongTaiDaiLi) clazz.newInstance();
        Constructor c = clazz.getDeclaredConstructor(String.class);
        // c.setAccessible(true);
        dongTaiDaiLi daiLi1 = (dongTaiDaiLi) c.newInstance("youcan");
        Class clazz1 = Calculate.class;
        System.out.println(clazz1);
        // Calculate calculate1=(Calculate) clazz1.newInstance();error!!!!!
        //dongTai,calculateProxyClazz即接口实现类
        //calculateProxyClazz==class $Proxy0;实现了接口
        //calculateProxyClazz;代理Class对象
        /*$Proxy0类构造器如下:InvocationHandler为接口,该接口有抽象方法invoke,实现时需重写方法
        *public $Proxy0(InvocationHandler invocationhandler) {
        *  super(invocationhandler);
        }*/
        //however, if Class calculateProxyClazz==Calculate.class,
        //calculateProxyClazz==interface Calculate;
        Class calculateProxyClazz
                = Proxy.getProxyClass(Calculate.class.getClassLoader(), Calculate.class);
        //  Calculate calculate1=(Calculate) calculateProxyClazz.newInstance();
        Constructor constructor
                = calculateProxyClazz.getConstructor(InvocationHandler.class);
        Calculate calculate1 = (Calculate) constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName() + "方法开始执行...");
                //      Object result = method.invoke(target, args);
                //  System.out.println(result);
                //   System.out.println(method.getName() + "方法执行结束...");
                //    return result;
                return null;
            }

            public String toString() {
                System.out.println("toString");
                return "456446546546545464654654";
            }
        });
        //    Calculate calculate2=(Calculate) calcdulateProxyClazz.newInstance();
        System.out.println(calculate1);
        System.out.println(calculate1.getClass());
        Class proxyClazz = Proxy.getProxyClass(new CalculatorImpl().getClass().getClassLoader(), new CalculatorImpl().getClass().getInterfaces());
        System.out.println(proxyClazz);
        //每次调用代理对象calculate1的方法，最终都会调用InvocationHandler的invoke()方法：
        Test2 test = new Test2() {
            @Override
            public String pri() {
                return "pri";
            }

            public String toString() {
                System.out.println("printToString");
                return "toString";
            }
        };
        System.out.println(test);//调用test.toString() : dongTaiDaiLi$2@9807454
        System.out.println(test.getClass());//class dongTaiDaiLi$2，已经不是接口，而是匿名内部类dongTaiDaiLi$2
        System.out.println(new dongTaiDaiLi().getClass());//class dongTaiDaiLi
        System.out.println(Test2.class);//interface Test
        //实际编程中，一般不用getProxyClass()，而是使用Proxy类的另一个静态方法：
        // Proxy.newProxyInstance()，
        // 直接返回代理实例，连中间得到代理Class对象的过程都帮你隐藏：
        CalculatorImpl target = new CalculatorImpl();
        Calculate calculate2 = (Calculate)getProxy(target);
        Calculate calculate3= (Calculate)Proxy.newProxyInstance(
                calculate2.getClass().getClassLoader(),
                calculate2.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)  {
                        return null;
                    }
                }
        );
        System.out.println("Calculate.class  "+Calculate.class.getClassLoader().equals(calculate2.getClass().getClassLoader()));
        System.out.println("calculate2.getClass()  "+calculate2.getClass().getClassLoader());
        System.out.println(calculate2.getClass().getInterfaces().equals(Calculate.class.getInterfaces()));
        System.out.println(calculate2.getClass().getInterfaces());
        System.out.println(Calculate.class.getInterfaces());
    }
    static Object getProxy(Object target){
        Object proxy =   Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)  {
                        return null;
                    }
                });
        return proxy;

    }
    public void print() {
        System.out.println("print");
    }

    dongTaiDaiLi() {
        System.out.println("diaoyong wuxan");
    }

    public dongTaiDaiLi(String s) {
        System.out.println(s);
    }
}

