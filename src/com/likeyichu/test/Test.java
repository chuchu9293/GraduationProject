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
		DocStatistics.getStatistics();
		FeatureSelection f=new FeatureSelection();
		f.chiSquaretest();
		f.getFeatureSortedTermSet(15);
		f.showTopNTerm(15);
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
		anal.close();
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
/*得到一篇文档，标题：     图论总述 - chuchus - 博客频道 - CSDN.NET
得到一篇文档，标题：     java中怎样比较String和字符串是否相等？-Jedliu-ChinaUnix博客
得到一篇文档，标题：     文本分类入门（番外篇）特征选择与特征权重计算的区别 - Jasper's Java Jacal - BlogJava
得到一篇文档，标题：     SVM入门（一）至（三）Refresh - Jasper's Java Jacal - BlogJava
得到一篇文档，标题：     习近平在上海团强调：经济发展“不能那么任性了”--中国人大新闻--人民网
得到一篇文档，标题：     院士:应取消研究生入学思想政治考试_中国频道_《参考消息》官方网站
得到一篇文档，标题：     黄宏：从特招小兵到少将厂长_娱乐频道_《参考消息》官方网站
得到一篇文档，标题：     赵薇：如拍电影《还珠格格》 自己想演容嬷嬷-中新网
得到一篇文档，标题：     跑男第二季首站成都 陈赫留队包贝尔换王宝强_百度传媒
加载扩展停止词典：stopword.dic
text=记者 ,A=0 B=12 C=4 D=0 ,chiSquarevalue:48.0
text=分类 ,A=4 B=0 C=0 D=12 ,chiSquarevalue:48.0
text=怎么 ,A=4 B=0 C=0 D=12 ,chiSquarevalue:48.0
text=函数 ,A=4 B=0 C=0 D=12 ,chiSquarevalue:48.0
text=来源 ,A=0 B=12 C=4 D=0 ,chiSquarevalue:48.0
text=所以 ,A=4 B=0 C=0 D=12 ,chiSquarevalue:48.0
text=篇 ,A=4 B=0 C=0 D=12 ,chiSquarevalue:48.0
text=搜 ,A=0 B=11 C=4 D=1 ,chiSquarevalue:35.0
text=存储 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=您 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=重复 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=模板 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=热门 ,A=1 B=12 C=3 D=0 ,chiSquarevalue:33.0
text=通过 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=支持 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=数学 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=计算 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=算法 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=得到 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=定义 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=区别 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=概念 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=完全 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=计算机 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=统计 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=请问 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=而言 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=其中 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=一种 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=私人 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=服务 ,A=1 B=12 C=3 D=0 ,chiSquarevalue:33.0
text=一篇 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=主 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=直观 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=常见 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=文档 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=进行 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=叫 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=技术 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
text=集 ,A=3 B=0 C=1 D=12 ,chiSquarevalue:33.0
[0.0, 2.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 5.0, 1.0]
[0.0, 2.0, 1.0, 3.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0]
[0.0, 89.0, 4.0, 5.0, 0.0, 11.0, 4.0, 0.0, 1.0, 11.0]
[0.0, 81.0, 8.0, 26.0, 0.0, 6.0, 1.0, 0.0, 3.0, 2.0]
[1.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 3.0, 0.0, 0.0]
[1.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 3.0, 0.0, 0.0]
[4.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 1.0, 0.0, 0.0]
[4.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 1.0, 0.0, 0.0]
[5.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0]
[2.0, 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 1.0, 0.0, 0.0]
[4.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 1.0, 0.0, 0.0]
[5.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0]
[2.0, 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 1.0, 0.0, 0.0]
[4.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 1.0, 0.0, 0.0]
[5.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0]
[5.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0]
*/