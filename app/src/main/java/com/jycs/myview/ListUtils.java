package com.jycs.myview;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/2/7 20:17
 */

public class ListUtils {

    public static List<String> getStrList(int start, int end){
        if (end - start < 0 ){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i < 10 ? "0" + i : i + "");
        }
        return list;
    }

    public static <T, V> T[] toArray(List<T> list, Class<V> cla){
        T[] ts = (T[]) Array.newInstance(cla, list.size());

        for (int i = 0; i < list.size(); i++) {
            ts[i] = list.get(i);
        }
        return ts;
    }

    public static int[] toArray(List<Integer> list){
        int[] ints = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ints[i] = list.get(i);
        }
        return ints;
    }

    public static int[] substring(int[] data, int start, int len) {
        if (start > len){
            throw new IllegalStateException("len 不能小于 start");
        }
        int count = len - start > data.length ? data.length : len - start;
        int[] ints = new int[len - start];
        for (int i = start; i < count; i++) {
            ints[i] = data[i];
        }
        return ints;
    }

    public static void  fill(Object[] obj, Number val){
        fill(obj, val, false);
    }

    public static void fill(Object[] obj, Object val){
        fill(obj, val, true);
    }

    public static void fill(Object[] obj, Object val, boolean isNew){

        try {
            Method method = val.getClass().getDeclaredMethod("clone");
            method.setAccessible(true);
            Object invoke = method.invoke(val);
            for (int i = 0; i < obj.length; i++) {
                obj[i] = isNew ? invoke : val;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
