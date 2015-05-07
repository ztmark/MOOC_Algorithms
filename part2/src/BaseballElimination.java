import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Mark
 * Date  : 2015/5/7
 * Time  : 14:44
 */
public class BaseballElimination {

    private int n;
    private Map<String,Integer> teams;
    private int[] wins;
    private int[] loss;
    private int[] left;
    private int[][] game;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);

        n = in.readInt();
        in.readLine();
        teams = new HashMap<>(n + 1);
        wins = new int[n];
        loss = new int[n];
        left = new int[n];
        game = new int[n][n];

        for (int i = 0; i < n; i++) {
            String line = in.readLine();
            String[] parts = line.split("\\s+");
            teams.put(parts[0], i);
            wins[i] = Integer.parseInt(parts[1]);
            loss[i] = Integer.parseInt(parts[2]);
            left[i] = Integer.parseInt(parts[3]);
            for (int j = 0; j < n; j++) {
                game[i][j] = Integer.parseInt(parts[4 + j]);
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        checkTeamValidation(team);
        return wins[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        checkTeamValidation(team);
        return loss[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        checkTeamValidation(team);
        return left[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        checkTeamValidation(team1);
        checkTeamValidation(team2);
        return game[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        checkTeamValidation(team);
        if (isTrivialEliminated(team)) return true;

        int v = (n - 1) * (n - 2) / 2 + (n - 1) + 2;
        FlowNetwork network = new FlowNetwork(v);
        int g = n - 1;
        int S = teams.get(team);
        int T = v - 1;
        for (int i = 0; i < n; i++) {
            if (i == S) continue;
            network.addEdge(new FlowEdge(i, T, wins[S] + left[S] - wins[i]));
            for (int j = 0; j < i; j++) {
                if (j == S) continue;
                network.addEdge(new FlowEdge(S, ++g, game[i][j]));
                network.addEdge(new FlowEdge(g, i, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(g, j, Double.POSITIVE_INFINITY));
            }
        }
        FordFulkerson fulkerson = new FordFulkerson(network, S, T);
        for (FlowEdge edge : network.adj(S)) {
            if (edge.flow() < edge.capacity()) {
                return true;
            }
        }
        return false;
    }

    private boolean isTrivialEliminated(String team) {
        int teamIndex = teams.get(team);
        int totalWins = wins[teamIndex] + left[teamIndex];
        for (int index : teams.values()) {
            if (index == teamIndex) continue;
            if (totalWins < wins[index]) return true;
        }
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        checkTeamValidation(team);
        return null;
    }

    private void checkTeamValidation(String team) {
        Integer index = teams.get(team);
        if (index == null || index < 0 || n <= index) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        BaseballElimination bbe = new BaseballElimination(args[0]);
        for (String t : bbe.teams()) {
            System.out.println(t + " " + bbe.isEliminated(t));
        }
    }

}
