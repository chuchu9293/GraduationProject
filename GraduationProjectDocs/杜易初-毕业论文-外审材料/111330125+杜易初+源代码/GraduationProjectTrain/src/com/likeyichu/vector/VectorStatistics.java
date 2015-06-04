package com.likeyichu.vector;

import java.util.List;

import com.likeyichu.dal.Dao;
import com.likeyichu.doc.Doc;

public class VectorStatistics {
	
	
	public static void insertVectorList(List<Doc> docList){
		Dao dao=Dao.getDao();
		for (Doc doc : docList) {
			doc.getFeatureVectorList();
			if(doc.isPositive)
				dao.insertVectorList(doc.id, doc.title, doc.featureVectorList.toString(),true);
			else
				dao.insertVectorList(doc.id, doc.title,  doc.featureVectorList.toString(),false);
		}
		
	}
}
