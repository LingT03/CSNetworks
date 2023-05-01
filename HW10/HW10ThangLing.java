import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Router {
    private int id;
    private List<Neighbor> neighbors;

    public Router(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Neighbor> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Neighbor neighbor) {
        neighbors.add(neighbor);
    }
}

class Neighbor {
    private int routerId;
    private int cost;

    public Neighbor(int routerId, int cost) {
        this.routerId = routerId;
        this.cost = cost;
    }

    public int getRouterId() {
        return routerId;
    }

    public int getCost() {
        return cost;
    }
}

public class HW10ThangLing {
    private static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[][] costMatrix = readCostMatrix();
        int numRouters = costMatrix.length;
        Router[] routers = new Router[numRouters];

        // Create routers
        for (int i = 0; i < numRouters; i++) {
            routers[i] = new Router(i);
        }

        // Add neighbors to routers
        for (int i = 0; i < numRouters; i++) {
            for (int j = 0; j < numRouters; j++) {
                if (i != j && costMatrix[i][j] != INFINITY) {
                    Neighbor neighbor = new Neighbor(j, costMatrix[i][j]);
                    routers[i].addNeighbor(neighbor);
                }
            }
        }

        int[] distanceVector = new int[numRouters];
        int[] predecessorVector = new int[numRouters];
        boolean[] visited = new boolean[numRouters];

        // Initialization
        Arrays.fill(distanceVector, INFINITY);
        Arrays.fill(predecessorVector, -1);
        distanceVector[0] = 0;

        // Dijkstra's algorithm
        for (int i = 0; i < numRouters; i++) {
            int minDistance = INFINITY;
            int currentRouter = -1;

            // Find the router with the minimum distance
            for (int j = 0; j < numRouters; j++) {
                if (!visited[j] && distanceVector[j] < minDistance) {
                    minDistance = distanceVector[j];
                    currentRouter = j;
                }
            }

            if (currentRouter == -1) {
                break; // All remaining routers are unreachable
            }

            visited[currentRouter] = true;

            // Update distance and predecessor vectors
            for (Neighbor neighbor : routers[currentRouter].getNeighbors()) {
                int neighborId = neighbor.getRouterId();
                int neighborCost = neighbor.getCost();
                int newDistance = distanceVector[currentRouter] + neighborCost;

                if (!visited[neighborId] && newDistance < distanceVector[neighborId]) {
                    distanceVector[neighborId] = newDistance;
                    predecessorVector[neighborId] = currentRouter;
                }
            }

            // Display intermediate results
            System.out.println("Iteration " + (i + 1));
            displaySetN(distanceVector);
            displaySetY(visited);
            displayDistanceVector(distanceVector);
            displayPredecessorVector(predecessorVector);
            System.out.println();
        }

        // Build forwarding table for router V0
        System.out.println("Forwarding Table:");
        System.out.println("Destination\tLink");
        for (int i = 1; i < numRouters; i++) {
            System.out.printf("V%d\t\t(V0, ", i);
            int routerId = i;
            while (routerId != 0) {
                System.out.printf("V%d, ", routerId);
                routerId = predecessorVector[routerId];
            }
            System.out.println("...)");
        }
    }

    private static int[][] readCostMatrix() {
        int[][] costMatrix = null;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("topo.txt"));
            String line;
            List<String[]> lines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                lines.add(parts);
            }

            int numRouters = lines.size();
            costMatrix = new int[numRouters][numRouters];

            for (int i = 0; i < numRouters; i++) {
                Arrays.fill(costMatrix[i], INFINITY);
            }

            for (String[] parts : lines) {
                int router1 = Integer.parseInt(parts[0]);
                int router2 = Integer.parseInt(parts[1]);
                int cost = Integer.parseInt(parts[2]);

                costMatrix[router1][router2] = cost;
                costMatrix[router2][router1] = cost;
            }
        } catch (IOException e) {
            System.err.println("Error reading cost matrix from file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in cost matrix file.");
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return costMatrix;
    }

    private static void displaySetN(int[] distanceVector) {
        System.out.print("Set N': ");
        for (int i = 0; i < distanceVector.length; i++) {
            if (distanceVector[i] != INFINITY) {
                System.out.printf("V%d ", i);
            }
        }
        System.out.println();
    }

    private static void displaySetY(boolean[] visited) {
        System.out.print("Set Y': ");
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) {
                System.out.printf("V%d ", i);
            }
        }
        System.out.println();
    }

    private static void displayDistanceVector(int[] distanceVector) {
        System.out.print("Distance Vector D: ");
        for (int i = 0; i < distanceVector.length; i++) {
            System.out.printf("D(%d)=%d ", i, distanceVector[i]);
        }
        System.out.println();
    }

    private static void displayPredecessorVector(int[] predecessorVector) {
        System.out.print("Predecessor Vector P: ");
        for (int i = 0; i < predecessorVector.length; i++) {
            System.out.printf("P(%d)=%d ", i, predecessorVector[i]);
        }
        System.out.println();
    }
}
