package com.north.utils;


/**
 * 经度、纬度与坐标的转换
 * @Description <一句话描述>
 * @author liuli
 * @version
 * @since
 * @Date 2015年1月21日 上午9:28:35
 */
public class LongitudeAndLatitudeUtil {

    // 1公里经度
    public static double jdkm = 0.0089;
    // 1公里维度
    public static double wdkm = 0.01;
    /**
     * 计算两点之间距离
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static Double getDistance(double lng1, double lat1, double lng2,
                                     double lat2) {
        double EARTH_RADIUS = 6378137;
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static void main(String[] args) {
        System.out.println(getDistance(118.786059, 32.090392, 118.786026, 32.090406));
    }

    public Double computeDistance(double[] lngs,double[] lats,double maxDis){
        double total = 0;

        double beginLng = lngs[0];
        double beginLat = lats[0];
        int flag = 1;
        for (int i = 1; i < lngs.length; i++){
            double lng = lngs[i];
            double lat = lats[i];

            double distance = getDistance(beginLng,beginLat,lng,lat);
            if (distance > maxDis*flag){
                flag = flag + 1;
                continue;
            }
            flag = 1;
            total += distance;
            beginLng = lng;
            beginLat = lat;
        }

        return total;
    }
}
