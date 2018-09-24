package main.java.service.automation;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * Created by ritobrotoseth on 22/07/18.
 */
public class Point {

  private double x = 0;
  private double y = 0;
  private int cluster_number = 0;

  public Point(double x, double y)
  {
    this.setX(x);
    this.setY(y);
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getX()  {
    return this.x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getY() {
    return this.y;
  }

  public void setCluster(int n) {
    this.cluster_number = n;
  }

  public int getCluster() {
    return this.cluster_number;
  }

  //Calculates the distance between two points.
  protected static double distance(Point p, Point centroid) {
    return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
  }

  //Creates random point
  protected static Point createRandomPoint(int min, int max) {
    Random r = new Random();
    double x = min + (max - min) * r.nextDouble();
//    double y = min + (max - min) * r.nextDouble();
    double y = 1.0D;
    return new Point(x,y);
  }

  protected static List<Point> createDataPoints(List<Double> dataPoints) {
    List<Point> points = Lists.newArrayList();
    for(int i = 0; i < dataPoints.size(); i++) {
      points.add( new Point(dataPoints.get(i), 1.0D) );
    }
    return points;
  }

  public String toString() {
    return "("+x+","+y+")";
  }
}
