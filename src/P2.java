import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

//ma folosesc de clasa Drum1 pentru a modela graful primti
class Drum1 implements Comparable<Drum1> {
	int dest;
	int cost;
	Drum1(int d, int c) {
		this.dest = d;
		this.cost = c;
	}

	public int getCost() {
		return this.cost;
	}

	@Override
	public int compareTo(Drum1 other) {
		return Integer.compare(this.getCost(), other.cost);
	}

}

public class P2 {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		BufferedReader reader = new BufferedReader(new FileReader("p2.in"));
		//citesc n, m
		String rand = reader.readLine();
		StringTokenizer token = new StringTokenizer(rand, " ");

		final int n = Integer.parseInt(token.nextToken());
		final int m = Integer.parseInt(token.nextToken());
		rand = reader.readLine();
		token = new StringTokenizer(rand, " ");
		final int start = Integer.parseInt(token.nextToken());
		final int end = Integer.parseInt(token.nextToken());

		@SuppressWarnings("unchecked")
		ArrayList<Drum1>[] graf = new ArrayList[n + 1];
		for (int i = 0;i <= n;i++) {
			graf[i] = new ArrayList<Drum1>();
		}
		int[] dist = new int[n + 1];
		for (int i = 1;i <= n;i++) {
			dist[i] = 9999999;
		}
		dist[start] = 0;
		//citesc cele m muchii
		for (int i = 0;i < m;i++) {
			rand = reader.readLine();
			token = new StringTokenizer(rand, " ");
			int s = Integer.parseInt(token.nextToken());
			int d = Integer.parseInt(token.nextToken());
			Drum1 aux = new Drum1(d, Integer.parseInt(token.nextToken()));
			graf[s].add(aux);
		}

		//aplic algoritmul bellman ford imbunatatit(SPFA)

		PriorityQueue<Drum1> q = new PriorityQueue<Drum1>();
		q.add(new Drum1(start, 0));
		Drum1 u;
		while (!q.isEmpty()) {
			u = q.poll();
			for (int i = 0;i < graf[u.dest].size();i++) {
				if (dist[graf[u.dest].get(i).dest] > dist[u.dest] + graf[u.dest].get(i).cost) {
					dist[graf[u.dest].get(i).dest] = dist[u.dest] + graf[u.dest].get(i).cost;
					if (!q.contains(graf[u.dest].get(i))) {
						q.add(graf[u.dest].get(i));
					}
				}
			}
		}

		PrintWriter writer = new PrintWriter("p2.out");
		//afisez costul minim gasit
		writer.print(dist[end]);
		reader.close();
		writer.close();

	}
}
