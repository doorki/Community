package io.github.seccoding.web.pager;

public class OraclePager extends Pager {
	//오라클 이용 페이저 .
	public OraclePager(int printArticle, int printPage) {
		super(printArticle, printPage);
	}

	public OraclePager() {
		super();
	}

	@Override
	protected void computeArticleNumbers() {
		this.startArticleNumber = (this.pageNo * this.printArticle) + 1;
		this.endArticleNumber = this.startArticleNumber + this.printArticle - 1;
	}

	@Override
	public void setEndArticleNumber(int endArticleNumber) {
		this.endArticleNumber = endArticleNumber;
	}

	@Override
	public int getEndArticleNumber() {
		return this.endArticleNumber;
	}

	
	
}
