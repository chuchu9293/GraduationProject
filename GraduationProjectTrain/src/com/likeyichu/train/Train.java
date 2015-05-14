package com.likeyichu.train;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.likeyichu.doc.Doc;
import com.likeyichu.ikanalyzer.AboutIKAnalyzer;
import com.likeyichu.math.FeatureSelection;
import com.likeyichu.train.libsvm.AboutLibSvm;

public class Train {
	final static Logger logger = Logger.getLogger(Train.class);
	
	/**读token表，写non_relational_table,读token表，写vector表*/
	public static void main(String[] args) {
		//FeatureSelection.main(null);
		AboutLibSvm.transformVectorListFromTableToFile();
		try {
			AboutLibSvm.train();
		} catch (IOException e) {
			logger.error("AboutLibSvm.train()失败"+e.toString());
		}
	}
	
}
