public class QuickFindUF {

  private int[] id;

  public QuickFindUF(int N) {
    id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
  }

	/* 2 array accesses. */
	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}

	/* at most 2N + 2 array accesses. */
	public void union(int p, int q) {
		int pid = id[p];
		int qid = id[q];

		for (int i = 0 ; i < id.length; i++) {
			if (id[i] == pid) id[i] = qid;
		}
	}
}
