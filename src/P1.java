import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;


public class P1 {

	static int dfs(ArrayList<Integer>[] graf, int[] visit, int[] start,int k, int nod, int[] bk) {
		visit[nod] = 1;
		int i;
		for (i = 0;i < k;i++) {
			if (start[i] == nod) {
				return 1;
			} 
		}
		for (i = 0;i < graf[nod].size();i++) {
			if ((visit[graf[nod].get(i)] == 0) && (bk[graf[nod].get(i)] == 0)) {
				if (dfs(graf, visit, start, k, graf[nod].get(i), bk) == 1) {
					return 1;
				}
			}
		}
		return 0;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader("p1.in"));
		//citesc n, m, k, apoi citesc cele k orase din care incepe cautarea
		String rand = reader.readLine();
		StringTokenizer token = new StringTokenizer(rand, " ");

		final int n = Integer.parseInt(token.nextToken());
		final int m = Integer.parseInt(token.nextToken());
		final int k = Integer.parseInt(token.nextToken());
		int[] orase_start = new int[k];
		rand = reader.readLine();
		token = new StringTokenizer(rand, " ");
		for (int i = 0;i < k;i++) {
			orase_start[i] = Integer.parseInt(token.nextToken());
		}
		int[] perm_orase = new int[n - 1];
		rand = reader.readLine();
		token = new StringTokenizer(rand, " ");
		for (int i = 0;i < n - 1;i++) {
			perm_orase[i] = Integer.parseInt(token.nextToken());
		}
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] graf = new ArrayList[n + 1];
		for (int i = 1;i <= n; i++) {
			graf[i] = new ArrayList<Integer>();
		}
		//citesc cele m muchii
		for (int i = 0;i < m;i++) {
			rand = reader.readLine();
			token = new StringTokenizer(rand, " ");
			int fst = Integer.parseInt(token.nextToken());
			int snd = Integer.parseInt(token.nextToken());
			graf[fst].add(snd);
			graf[snd].add(fst);
		}

		// acum ca avem toate datele, incepem sa blocam pe rand orase, pana e
		//indeplinita conditia
		int nr_noduri = 1;
		int j;
		Integer x;
		for (j = 0;j < graf[perm_orase[0]].size();j++) {
			x = perm_orase[0];
			graf[graf[perm_orase[0]].get(j)].remove(x);
		}
		graf[perm_orase[0]].clear(); 
		int conditie = 1;
		int[] blocked = new int[n + 1];
		while (conditie == 1) {
			//parcurgem graful in adancime

			int[] visited = new int[n + 1];
			conditie = dfs(graf, visited, orase_start, k, 1, blocked);
			
			//daca am ajuns in nodurile pe care vrem sa le blocam, marim nr_noduri
			if (conditie == 1) {
				
				nr_noduri++;
				blocked[perm_orase[nr_noduri - 1]] = 1;
				//for (j = 0;j < graf[perm_orase[nr_noduri - 1]].size();j++) {
				//x = perm_orase[nr_noduri - 1];
				//graf[graf[perm_orase[nr_noduri - 1]].get(j)].remove(x);
				//}
				//graf[perm_orase[nr_noduri - 1]].clear();
			}
		}

		PrintWriter writer = new PrintWriter("p1.out");
		//afisez numarul minim gasit
		writer.print(nr_noduri);
		reader.close();
		writer.close();
	}
}