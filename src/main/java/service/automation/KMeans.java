package main.java.service.automation;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by ritobrotoseth on 22/07/18.
 */
public class KMeans {

  private int NUM_CLUSTERS = 5;   // Number of Clusters.
  private List<Point> points;
  private List<Cluster> clusters;

  public KMeans(List<Double> stockPoints) {
    this.points = Lists.newArrayList();
    this.clusters = Lists.newArrayList();
    init(stockPoints);
  }

  public static void main(String[] args) {
    List<Double> stockPoints = Lists.newArrayList(
        369.05, 370.15, 385.75, 422.4, 421.9,
        433.1, 428.5, 435.4, 424.25, 413.85,
        418.75, 404.6, 383.4, 385.2, 381.6,
        373.1, 372.15, 367.65, 370.1, 410.3,
        413.2, 426.85, 428.25, 415.95, 415.65);

    KMeans kmeans = new KMeans(stockPoints);
    kmeans.calculate();

//    System.out.println("#################");
//    System.out.println("Iteration: " + iteration);
    //    System.out.println("Centroid distances: " + distance);
//    plotClusters();

    kmeans.plotClusters();
  }

  /**
   * Initializes the process
   */
  public void init(List<Double> stockPoints) {
    points = Point.createDataPoints(stockPoints);
    setRandomCentroids(stockPoints);
//    plotClusters(); //Print Initial state
  }

  private void setRandomCentroids(List<Double> stockPoints) {
    double minPoint = stockPoints.get(0);
    double maxPoint = stockPoints.get(0);
    for(int i = 1; i < stockPoints.size(); i++) {
      if(stockPoints.get(i) < minPoint) {
        minPoint = stockPoints.get(i);
      }
      if(stockPoints.get(i) > maxPoint) {
        maxPoint = stockPoints.get(i);
      }
    }

    for (int i = 0; i < NUM_CLUSTERS; i++) {
      Cluster cluster = new Cluster(i);
      Point centroid = Point.createRandomPoint((int) Math.floor(minPoint), (int) Math.ceil(maxPoint));
      cluster.setCentroid(centroid);
      clusters.add(cluster);
    }
  }

  /**
   * The process to calculate the K Means, with iterating method.
   */
  public void calculate() {
    boolean finish = false;
    int iteration = 0;

    // Add in new data, one at a time, recalculating centroids with each new one.
    while(!finish) {
      //Clear cluster state
      clearClusters();

      List<Point> lastCentroids = getCentroids();

      //Assign points to the closer cluster
      assignCluster();

      //Calculate new centroids.
      calculateCentroids();

      iteration++;

      List<Point> currentCentroids = getCentroids();

      //Calculates total distance between new and old Centroids
      double distance = 0;
      for(int i = 0; i < lastCentroids.size(); i++) {
        distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
      }

      if(distance == 0) {
        finish = true;
      }
    }


  }

  private void clearClusters() {
    for(Cluster cluster : clusters) {
      cluster.clear();
    }
  }

  private List getCentroids() {
    List<Point> centroids = Lists.newArrayList();
    for(Cluster cluster : clusters) {
      Point aux = cluster.getCentroid();
      Point point = new Point(aux.getX(),aux.getY());
      centroids.add(point);
    }
    return centroids;
  }

  private void assignCluster() {
    double max = Double.MAX_VALUE;
    double min = max;
    int cluster = 0;
    double distance = 0.0;

    for(Point point : points) {
      min = max;
      for(int i = 0; i < NUM_CLUSTERS; i++) {
        Cluster c = clusters.get(i);
        distance = Point.distance(point, c.getCentroid());
        if(distance < min){
          min = distance;
          cluster = i;
        }
      }
      point.setCluster(cluster);
      clusters.get(cluster).addPoint(point);
    }
  }

  private void calculateCentroids() {
    for(Cluster cluster : clusters) {
      double sumX = 0;
      double sumY = 0;
      List<Point> list = cluster.getPoints();
      int n_points = list.size();

      for(Point point : list) {
        sumX += point.getX();
        sumY += point.getY();
      }

      Point centroid = cluster.getCentroid();
      if(n_points > 0) {
        double newX = sumX / n_points;
        double newY = sumY / n_points;
        centroid.setX(newX);
        centroid.setY(newY);
      }
    }
  }

  public void plotClusters() {
    for (int i = 0; i < NUM_CLUSTERS; i++) {
      Cluster c = clusters.get(i);
      c.plotCluster();
    }
  }

  public List<Cluster> getClusters() {
    return clusters;
  }
}
