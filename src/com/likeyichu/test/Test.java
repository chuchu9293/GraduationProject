package com.likeyichu.test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.likeyichu.doc.Doc;
import com.likeyichu.doc.DocStatistics;
import com.likeyichu.math.FeatureSelection;
import com.likeyichu.train.Train;

public class Test {

	public static void main(String[] args) throws IOException {
		// iKAnalyzerTest();
		// System.out.println("********************");
		// jsoupTest();
		// System.out.println("********************");
		preTrainTest();
	}

	static void preTrainTest() throws IOException{
		List<String> relativeUrlList=Train.getRelativeUrlsToDownLoad("c:\\GP\\getRelativeUrlsToDownLoad.txt");
		Train.downLoadHtmlList(relativeUrlList,true);
		List<String> IrrelevantUrlList=Train.getRelativeUrlsToDownLoad("c:\\GP\\getIrrelevantUrlsToDownLoad.txt");
		Train.downLoadHtmlList(IrrelevantUrlList,false);
		//得到相关的docList
		DocStatistics.addDocListFromLocal("c:\\GP\\downloadFiles\\relative\\");
		//得到  不相关 的docList
		DocStatistics.addDocListFromLocal("c:\\GP\\downloadFiles\\irrelevant\\");
		//List<Doc> docList=DocStatistics.docList;
		DocStatistics.getStatistics();
		FeatureSelection f=new FeatureSelection();
		f.chiSquaretest();
		f.getFeatureSortedTermSet(4);
		f.showTopNTerm(400);
		for (Doc doc : DocStatistics.docList) {
			System.out.println(doc.getFeatureVectorList());
		}
	}

	static void iKAnalyzerTest() throws IOException {
		String str = "匹配：二部图中没有公共顶点的边集。边数最多的匹配叫最大匹配。若图中每个顶点都被匹配边邻接，叫完美匹配。二部图最小覆盖：选择尽量少的点，使得图中每条边至少有一个端点被选中。可以证明，最小覆盖数=最大匹配数。二部图的独立集：该集合中任意两点不相邻接。独立集中顶点个数=总顶点数-最大匹配中边的数目。";
		// 创建分词对象
		Analyzer anal = new IKAnalyzer(true);
		StringReader reader = new StringReader(str);
		// 分词
		TokenStream ts = anal.tokenStream("", reader);
		CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
		ts.reset();
		// 遍历分词数据
		while (ts.incrementToken()) {
			System.out.print(term.toString() + "|");
		}
		reader.close();
		System.out.println();
		/*
		 * 加载扩展停止词典：stopword.dic
		 * 搜索算法|是|利用|计算机|的|高性能|来|有|目的|的|穷举|一个|问|题解|空间|的|部分|或|所有|的|可能
		 * |情况|从而|求出|问题|的|解|的|一种
		 * |方法|搜索算法|实际上|是|根据|初始条件|和|扩展|规则|构造|一棵|解答|树|并|寻找|符合|目标|状态|的|节点|的|过程|
		 */
	}

	static void jsoupTest() {
		Document doc = null;
		try {
			doc = Jsoup
					.connect(
							"http://blog.csdn.net/chuchus/article/details/23205283")
					.userAgent("Mozilla").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(doc.title());
		String text = doc.text();
		String[] texts = text.split("。|\\.");// 以中文句号或英文句号作为分割
		// for (String string : texts) {
		// System.out.println(string);
		// }
		System.out.println(texts[0]);
	}

}
