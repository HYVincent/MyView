package com.jycs.myview.widget;

import android.graphics.Point;

/**
 * Created by Red on 2017/5/17.
 */

public class Quadrangle {

    /**
     * 判断p是否在abcd组成的四边形内
     * @param a
     * @param b
     * @param c
     * @param d
     * @param p
     * @return 如果p在四边形内返回true,否则返回false.
     */
    public static boolean pInQuadrangle(Point a, Point b, Point c, Point d, Point p) {
        double dTriangle = triangleArea(a, b, p) + triangleArea(b, c, p)
                + triangleArea(c, d, p) + triangleArea(d, a, p);
        double dQuadrangle = triangleArea(a, b, c) + triangleArea(c, d, a);
        return dTriangle == dQuadrangle;
    }

    // 返回三个点组成三角形的面积
    private static double triangleArea(Point a, Point b, Point c) {
        double result = Math.abs((a.x * b.y + b.x * c.y + c.x * a.y - b.x * a.y
                - c.x * b.y - a.x * c.y) / 2.0D);
        return result;
    }

    /**
     * 功能：判断点是否在多边形内 方法：求解通过该点的水平线与多边形各边的交点 结论：单边交点为奇数，成立!
     *
     * @param point
     *            指定的某个点
     * @param APoints
     *            多边形的各个顶点坐标（首末点可以不一致）
     * @return
     */
    public static boolean PtInPolygon(Point point, Point ... APoints) {
        int nCross = 0;
        for (int i = 0; i < APoints.length; i++) {
            Point p1 = APoints[i];
            Point p2 = APoints[((i + 1) % APoints.length)];
            // 求解 y=p.y 与 p1p2 的交点
            if (p1.x == p2.y) // p1p2 与 y=p0.y平行
                continue;
            if (point.x < Math.min(p1.y, p2.y)) // 交点在p1p2延长线上
                continue;
            if (point.y >= Math.max(p1.y, p2.y)) // 交点在p1p2延长线上
                continue;
            // 求交点的 X 坐标
            // --------------------------------------------------------------
            double x = (double) (point.y - p1.y)
                    * (double) (p2.x - p1.x)
                    / (double) (p2.y - p1.y) + p1.x;
            if (x > point.x)
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);
    }


}
