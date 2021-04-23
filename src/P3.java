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

//ma folosesc de clasa Drum pentru modelarea grafului dat
class Drum implements Comparable<Drum> {
	int nod;
	int dist;

	Drum() {}

	Drum(int n, int d) {
		this.nod = n;
		this.dist = d;
	}

	public int getDist() {
		return this.dist;
	}

	@Override
	public int compareTo(Drum other) {
		return Integer.compare(this.getDist(), other.dist);
	}

}


public class P3 {

	//implementarea algoritmului Dijkstra
	static void dijkstra(Integer source, int dest, ArrayList<Drum>[] graf, int n,
		ArrayList<Drum> drum, int[] dist) {

		PriorityQueue<Drum> q = new PriorityQueue<Drum>(); 
		q.add(new Drum(source, 0));
		
		Drum[] p = new Drum[n + 1];
		for (int i = 0;i <= n;i++) {

			dist[i] = 99999999;
			p[i] = new Drum(-1, 99999999);
		}
		dist[source] = 0;
		int[] sel = new int[n + 1];
		Drum u;
		while (!q.isEmpty()) {
			u = q.poll();
			if (sel[u.nod] == 0) {
				sel[u.nod] = 1;

				for (int i = 0;i < graf[u.nod].size();i++) {
					Drum aux = graf[u.nod].get(i);
					if ((sel[aux.nod] == 0) && (dist[aux.nod] > dist[u.nod] + aux.dist)) {
						dist[aux.nod] = dist[u.nod] + aux.dist;
						p[aux.nod] = u;
						q.add(new Drum(aux.nod, dist[aux.nod]));
					}
				}
			}
		}

		//construiesc drumul minim de la sursa la destinatie, retinand si 
		//costul fiecarei muchii
		drum.add(new Drum(dest, dist[dest]));
		Drum nod = p[dest];
		while (nod.nod != -1) {
			drum.add(0, nod);
			nod = p[nod.nod];
		}
		

	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		BufferedReader reader = new BufferedReader(new FileReader("p3.in"));
		//citesc n, m, e
		String rand = reader.readLine();
		StringTokenizer token = new StringTokenizer(rand, " ");

		final int n = Integer.parseInt(token.nextToken());
		final int m = Integer.parseInt(token.nextToken());
		final double e = Double.parseDouble(token.nextToken());

		@SuppressWarnings("unchecked")
		ArrayList<Drum>[] graf = new ArrayList[n + 1];
		for (int i = 0;i <= n;i++) {
			graf[i] = new ArrayList<Drum>();
		}
		//citesc cele m muchii
		for (int i = 0;i < m;i++) {
			rand = reader.readLine();
			token = new StringTokenizer(rand, " ");
			int s = Integer.parseInt(token.nextToken());
			int d = Integer.parseInt(token.nextToken());
			Drum aux = new Drum(d, Integer.parseInt(token.nextToken()));
			graf[s].add(aux);
		}

		int[] dist = new int[n + 1];
		ArrayList<Drum> drum = new ArrayList<Drum>();
		dijkstra(1, n, graf, n, drum, dist);
		double energie = e * (double)(1 - dist[drum.get(0).nod] / 100.0);
		//parcurg drumul minim gasit si calculez energia ramasa
		for (int i = 1;i < drum.size();i++) {
			energie = energie * (1 - (dist[drum.get(i).nod] - dist[drum.get(i - 1).nod]) / 100.0);
		}


		PrintWriter writer = new PrintWriter("p3.out");
		writer.println(energie);

		for (int i = 0 ;i < drum.size();i++) {
			writer.print(drum.get(i).nod + " ");
			System.out.print(drum.get(i).nod + " ");
		}

		System.out.println(energie);

		reader.close();
		writer.close();

	}
}