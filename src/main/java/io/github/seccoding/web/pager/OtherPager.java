package io.github.seccoding.web.pager;

public class OtherPager extends Pager {
	//다른 디비 페이저 .
	public OtherPager(int printArticle, int printPage) {
		super(printArticle, printPage);
	}

	public OtherPager() {
		super();
	}

	@Override
	protected void computeArticleNumbers() {
		this.startArticleNumber = (this.pageNo * this.printArticle);
		this.endArticleNumber = this.printArticle;
	}

	@Override
	public void setEndArticleNumber(int endArticleNumber) {
		this.endArticleNumber = printArticle;
	}

	@Override
	public int getEndArticleNumber() {
		return this.printArticle;
	}

}
