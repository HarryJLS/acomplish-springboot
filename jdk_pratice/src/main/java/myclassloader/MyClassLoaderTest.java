package myclassloader;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author JLS
 * @description:自定义类加载器实现
 * @since 2022-07-20 20:23
 */
public class MyClassLoaderTest {

    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("D:/test");
        // D盘自定义路径创建文件，
        Class clazz = classLoader.loadClass("myclassloader.file.User");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(obj, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }

    static class MyClassLoader extends ClassLoader {
        // 自定义类路径
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                // defineClass将一个字节数组转为class对象，这个字节数组是class文件读取后最终的字节数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }


        // 重写loadClass方法可以打破双亲委派
        @Override
        protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException
        {
            synchronized (getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    long t0 = System.nanoTime();
//                    try {
//                        if (parent != null) {
//                            c = parent.loadClass(name, false);
//                        } else {
//                            c = findBootstrapClassOrNull(name);
//                        }
                        if (!name.startsWith("myclassloader.file")) {  //针对Object类进行处理，可使其他类继续执行双亲委派
                            c = this.getParent().loadClass(name);
                        } else {
                            c = findClass(name);
                        }


//                    } catch (ClassNotFoundException e) {
//                        // ClassNotFoundException thrown if class not found
//                        // from the non-null parent class loader
//                    }

                    if (c == null) {
                        // If still not found, then invoke findClass in order
                        // to find the class.
                        long t1 = System.nanoTime();
                        c = findClass(name);

                        // this is the defining class loader; record the stats
                        sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                        sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                        sun.misc.PerfCounter.getFindClasses().increment();
                    }
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }
    }
}


